package org.yishang.listener;

import cn.hutool.core.util.XmlUtil;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.project.ProjectManagerListener;
import com.intellij.openapi.startup.ProjectActivity;
import com.intellij.openapi.wm.WindowManager;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.yishang.StatisticsData;
import org.yishang.bean.Constant;
import org.yishang.bean.Statistics;
import org.yishang.persistent.UITimesState;
import org.yishang.util.DateUtil;
import org.yishang.util.SingletonUtil;
import org.yishang.util.StringUtil;
import org.yishang.util.WindowUtil;

import javax.swing.*;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;


public class ProjectStartListener implements ProjectActivity {
	private UITimesState uiTimesState = ApplicationManager.getApplication().getService(UITimesState.class);
	private Thread alarmMonitoringThread;
	private Thread statisticsMonitoringThread;
	private AtomicBoolean running = new AtomicBoolean(true);

	@Nullable
	@Override
	public Object execute(@NotNull Project project, @NotNull Continuation<? super Unit> continuation) {
		// 项目启动检测文件
		try {
			String fileName = PathManager.getConfigPath() + File.separator + Constant.FILE_NAME;
			File file = new File(fileName);
			if (!file.exists()) {
				boolean newFile = file.createNewFile();
				if (!newFile) {
					WindowUtil.consoleError(project, "插件创建文件失败");
				} else {
					RandomAccessFile randomAccessFile = null;
					try {
						randomAccessFile = new RandomAccessFile(fileName, "rw");
						FileChannel channel = randomAccessFile.getChannel();
						FileLock lock = channel.lock();
						try {
							// 读取文件内容并解析为 StatisticsData 对象
							randomAccessFile.seek(0); // 移动到文件开头
							Statistics statistics = new Statistics();
							statistics.setRunProjectPath(project.getBasePath());
							randomAccessFile.writeBytes(XmlUtil.format(XmlUtil.beanToXml(statistics)));
							randomAccessFile.setLength(randomAccessFile.getFilePointer()); // 截断文件以移除多余数据
						} finally {
							// 确保锁被释放
							lock.release();
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						try {
							if (randomAccessFile != null) {
								randomAccessFile.close();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			WindowUtil.consoleError(project, e.getMessage());
		}

		alarmMonitoringThread = new Thread(() -> {
			Integer accumulatedActiveTime = 0;
			Integer count = 0;
			Long lastUpdateTime = System.currentTimeMillis();
			String spacedNotifityMsg = "您已经工作了%s分钟，请休息一下！";
			Map<String, String> configMap = new HashMap<>();
			while (running.get()) {
				configMap.clear();
				String alarmRule = uiTimesState.getAlarmRule();
				LocalTime now = LocalTime.now();
				// 转换为从午夜开始的毫秒数
				Integer secondOfDay = now.toSecondOfDay();
				if (StringUtils.isNotBlank(alarmRule)) {
					Map<String, String> parseMap = StringUtil.parseConfigWithRegex(alarmRule);
					for (Map.Entry<String, String> entry : parseMap.entrySet()) {
						if ("spacedNotifityMsg".equals(entry.getKey().trim())) {
							spacedNotifityMsg = parseMap.getOrDefault("spacedNotifityMsg", "").trim();
							continue;
						}
						Integer toMillis = DateUtil.parseTimeToMillis(entry.getKey().trim());
						if (toMillis != null) {
							configMap.put(entry.getKey(), entry.getValue().trim());
							continue;
						}

					}

				}

				// 固定时间告警
				for (Map.Entry<String, String> entry : configMap.entrySet()) {
					// 计算当前是否在告警时间范围内
					try {
						Integer toMillis = DateUtil.parseTimeToMillis(entry.getKey().trim());

						if (secondOfDay.equals(toMillis) || ((secondOfDay) >= (toMillis - 1) && (secondOfDay) <= (toMillis + 1))) {
							JOptionPane.showMessageDialog(null, entry.getValue(), "休息提示", JOptionPane.INFORMATION_MESSAGE);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				// 常规通知告警
				try {
					Integer alarmTime = null;
					if (uiTimesState.getAlarmTime() != null && StringUtils.isNotBlank(uiTimesState.getAlarmTime())) {
						alarmTime = Integer.parseInt(uiTimesState.getAlarmTime());
					} else {
						alarmTime = 1800;
					}
					// 获取当前项目的主窗口
					// 判断当前ide是否处于活跃状态
					JFrame ideFrame = WindowManager.getInstance().getFrame(project);
					if (ideFrame != null && ideFrame.isActive()) {
						accumulatedActiveTime += 1;
					}
					if (accumulatedActiveTime != 0 && accumulatedActiveTime >= alarmTime) {
						// 弹出提示框，提示用户是否需要休息
						if (count < 3) {
							count += 1;
							if (spacedNotifityMsg.contains("%s")) {
								spacedNotifityMsg = String.format(spacedNotifityMsg, accumulatedActiveTime / 60);
							}
							JOptionPane.showMessageDialog(null, spacedNotifityMsg, "休息提示", JOptionPane.INFORMATION_MESSAGE);
						}
						long currentTimeMillis = System.currentTimeMillis();
						if (currentTimeMillis - lastUpdateTime >= 60000) {
							count = 0;
							lastUpdateTime = currentTimeMillis;
						}
						accumulatedActiveTime = 0;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}


				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt(); // 重新设置中断状态
				}
			}
		});
		alarmMonitoringThread.start();

		statisticsMonitoringThread = new Thread(() -> {
			while (running.get()) {
				// 判断编辑器是否处于活跃状态
				//Long activeInterval = (null == uiTimesState.getActiveInterval() ? 30 * 1000 : uiTimesState.getActiveInterval()) * 1000;
				//Long updateInterval = (null == uiTimesState.getUpdateInterval() ? 30 * 1000 : uiTimesState.getUpdateInterval()) * 1000;
				Long activeInterval = 2000L;
				Long updateInterval = 2000L;
				try {
					// 避免多个项目运行时间统计多次, 增加满足以下规则才进行时间统计:
					// 当前项目与配置信息一致, 或配置信息为空, 或配置信息内的项目不处于打开状态
					String projectPath = project.getLocationHash();
					//String projectPath = project.getProjectFilePath();
					String firstOrNull = null;
					@NotNull Project[] openProjects = ProjectManager.getInstance().getOpenProjects();
					for (Project openProject : openProjects) {
						String locationHash = openProject.getLocationHash();
						if (locationHash.equals(projectPath)) {
							firstOrNull = locationHash;
							break;
						}
					}
					// 获取当前
					JFrame frame = WindowManager.getInstance().getFrame(project);

					boolean active = frame.isActive();
					StatisticsData statisticsData = SingletonUtil.getInstance();
					if (Objects.equals(statisticsData.getRunProjectPath(), projectPath)
							|| statisticsData.getRunProjectPath().isBlank()
							|| firstOrNull == null) {
						if (statisticsData.getCreateDate().equals(DateUtil.getCurDate())) {
							statisticsData.getRunTime().addAndGet(updateInterval);
						} else {
							SingletonUtil.writeStatistics(uiTimesState.getMaxHistoryDay());
							statisticsData.getRunTime().addAndGet(updateInterval);
						}
					}
					// 修改判断条件
					if (active) {
						statisticsData.setRunProjectPath(projectPath);
//						String message = String.format("runProjectPath=%s \n,projectPath=%s \n,active=%s \n," +
//								"firstOrNull=%s \n, active=%s \n, " +
//								"runtime=%s \n, createDate=%s \n,", uiTimesState.getRunProjectPath(), projectPath, active, firstOrNull, statisticsData.getActiveTime(), statisticsData.getRunTime(), statisticsData.getCreateDate());
//						WindowUtil.consoleError(project, message);
						if (statisticsData.getCreateDate().equals(DateUtil.getCurDate())) {
							statisticsData.getActiveTime().addAndGet(activeInterval);
							//uiTimesState.setActiveTime(statisticsData.getActiveTime().longValue());
						} else {
							SingletonUtil.writeStatistics(uiTimesState.getMaxHistoryDay());
							statisticsData.getRunTime().addAndGet(updateInterval);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					Thread.sleep(updateInterval);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt(); // 重新设置中断状态
				}
			}
		});
		statisticsMonitoringThread.start();


		// 注册项目关闭监听器
		ApplicationManager.getApplication().getMessageBus().connect().subscribe(ProjectManager.TOPIC, new ProjectManagerListener() {
			@Override
			public void projectClosed(@NotNull Project projectToBeClosed) {
				if (projectToBeClosed.equals(project)) {
					running.set(false);
					if (alarmMonitoringThread != null) {
						alarmMonitoringThread.interrupt();
					}
					if (statisticsMonitoringThread != null) {
						statisticsMonitoringThread.interrupt();
					}
					if (SingletonUtil.thread != null) {
						// 关闭时执行一遍
						SingletonUtil.runStatisticsFile();
					}

				}
			}
		});

		return null;
	}
}
