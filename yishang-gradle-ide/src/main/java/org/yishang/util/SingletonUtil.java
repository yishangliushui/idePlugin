package org.yishang.util;

import cn.hutool.core.util.XmlUtil;
import com.alibaba.fastjson.JSON;
import com.intellij.openapi.application.PathManager;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.yishang.StatisticsData;
import org.yishang.bean.Constant;
import org.yishang.bean.Statistics;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SingletonUtil {

	// 读写锁
	private final static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private final static Lock readLock = readWriteLock.readLock();
	private final static Lock writeLock = readWriteLock.writeLock();
	private static final String FILE_NAME = PathManager.getConfigPath() + File.separator + Constant.FILE_NAME;
	public static Thread thread;
	// 私有静态变量保存唯一实例
	private static volatile StatisticsData statisticsData;

	// 提供全局访问点
	public static StatisticsData getInstance() {
		readLock.lock();
		try {
			if (statisticsData == null) { // 第一次检查
				synchronized (StatisticsData.class) {
					if (statisticsData == null) { // 第二次检查
						//statisticsData = JSON.parseObject(uiTimesState.getStatisticsData(), StatisticsData.class);
						//if (statisticsData == null) {
						//	statisticsData = new StatisticsData();
						//}
						// 改为同步数据做加法
						Statistics statistics = readStatisticsFile();
						statisticsData = new StatisticsData();
						statistics.setRunProjectPath(statistics.getRunProjectPath());
						if (StringUtils.isNotBlank(statistics.getStatisticsData())){
							statisticsData.setCreateDate(JSON.parseObject(statistics.getStatisticsData(), StatisticsData.class).getCreateDate());
						}
						// 启动线程，定时保存数据
						//UITimesState uiTimesState = ApplicationManager.getApplication().getService(UITimesState.class);
						if (thread == null) {
							thread = new Thread(() -> {
								while (true) {
									try {
										Thread.sleep(10000);
									} catch (Exception e) {
										e.printStackTrace();
									}
									//System.out.println("缓存的数据详情："+JSON.toJSONString(statisticsData));
									//uiTimesState.setStatisticsData(JSON.toJSONString(statisticsData));
									// 每隔十秒同步到文件。
									// 开启文件锁
									runStatisticsFile();
								}
							});
							thread.start();
						}

					}
					// 问题，启动多个项目时，多进程会导致数据统计不准确。
				}
			}
			return statisticsData;
		} finally {
			readLock.unlock();
		}
	}

	public static void runStatisticsFile() {
		try {

			RandomAccessFile randomAccessFile = null;
			try {
				randomAccessFile = new RandomAccessFile(FILE_NAME, "rw");
				FileChannel channel = randomAccessFile.getChannel();
				FileLock lock = channel.lock();
				try {
					// 读取文件内容并解析为 StatisticsData 对象
					String xmlContent = readXmlContent(randomAccessFile);
					Document document = XmlUtil.readXML(xmlContent);
					Statistics statisticsFile = XmlUtil.xmlToBean(document, Statistics.class);
					statisticsFile.setRunProjectPath(statisticsData.getRunProjectPath());
					StatisticsData statisticsDataFile;
					if (StringUtils.isNotBlank(statisticsFile.getStatisticsData())) {
						statisticsDataFile = JSON.parseObject(statisticsFile.getStatisticsData(), StatisticsData.class);
						if (statisticsDataFile == null) {
							statisticsDataFile = new StatisticsData();
						}
					} else {
						statisticsDataFile = new StatisticsData();
					}
					if (statisticsData.getCreateDate().equals(statisticsDataFile.getCreateDate())) {
						statisticsDataFile.getRunTime().addAndGet(statisticsData.getRunTime().get());
						statisticsDataFile.getActiveTime().addAndGet(statisticsData.getActiveTime().get());
						statisticsDataFile.getKeyCount().addAndGet(statisticsData.getKeyCount().longValue());
						statisticsDataFile.getAddLineCount().addAndGet(statisticsData.getAddLineCount().longValue());
						statisticsDataFile.getRemoveLineCount().addAndGet(statisticsData.getRemoveLineCount().longValue());
						statisticsDataFile.getCopyCount().addAndGet(statisticsData.getCopyCount().longValue());
						statisticsDataFile.getPasteCount().addAndGet(statisticsData.getPasteCount().longValue());
						statisticsDataFile.getPushCount().addAndGet(statisticsData.getPushCount().longValue());
						statisticsFile.setRunProjectPath(statisticsData.getRunProjectPath());
						statisticsFile.setStatisticsData(JSON.toJSONString(statisticsDataFile));
					}


					// 将更新后的 StatisticsData 转换为 XML 字符串
					// 重新写入内容
					// 实现将更新后的内容写入文件的方法
					randomAccessFile.seek(0); // 移动到文件开头
					randomAccessFile.writeBytes(XmlUtil.format(XmlUtil.beanToXml(statisticsFile)));
					randomAccessFile.setLength(randomAccessFile.getFilePointer()); // 截断文件以移除多余数据

					// 重置统计数据
					clearStatisticsData();
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String readXmlContent(RandomAccessFile randomAccessFile) throws IOException {
		// 实现读取文件内容的方法
		byte[] buffer = new byte[(int) randomAccessFile.length()];
		randomAccessFile.readFully(buffer);
		return new String(buffer, StandardCharsets.UTF_8);
	}


	public static void clearStatisticsData() {
		writeLock.tryLock();
		try {
			StatisticsData instance = SingletonUtil.getInstance();
			instance.setRunTime(new AtomicLong(0));
			instance.setActiveTime(new AtomicLong(0));
			instance.setKeyCount(new AtomicLong(0));
			instance.setAddLineCount(new AtomicLong(0));
			instance.setRemoveLineCount(new AtomicLong(0));
			instance.setCopyCount(new AtomicLong(0));
			instance.setPasteCount(new AtomicLong(0));
			instance.setPushCount(new AtomicLong(0));
			instance.setCreateDate(DateUtil.getCurDate());
		} finally {
			writeLock.unlock();
		}
	}


	public static Statistics readStatisticsFile() {
		RandomAccessFile randomAccessFile = null;
		try {
			randomAccessFile = new RandomAccessFile(FILE_NAME, "rw");
			FileChannel channel = randomAccessFile.getChannel();
			FileLock lock = channel.lock();
			try {
				// 读取文件内容并解析为 StatisticsData 对象
				String xmlContent = readXmlContent(randomAccessFile);
				Document document = XmlUtil.readXML(xmlContent);
				return XmlUtil.xmlToBean(document, Statistics.class);
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
		return new Statistics();
	}


	public static void writeStatistics(String maxHistoryDay) {
		RandomAccessFile randomAccessFile = null;
		try {
			randomAccessFile = new RandomAccessFile(FILE_NAME, "rw");
			FileChannel channel = randomAccessFile.getChannel();
			FileLock lock = channel.lock();
			try {
				String xmlContent = readXmlContent(randomAccessFile);
				Document document = XmlUtil.readXML(xmlContent);
				Statistics statistics = XmlUtil.xmlToBean(document, Statistics.class);

				Integer historySize = StringUtils.isBlank(maxHistoryDay) ? 365 : Integer.parseInt(maxHistoryDay);
				List<String> historyData;
				if (StringUtils.isNotBlank(statistics.getHistoryDataList())){
					historyData = JSON.parseObject(statistics.getHistoryDataList(), List.class);
				}else {
					historyData = new ArrayList<>();
				}
				if (!historyData.isEmpty() && historyData.size() >= historySize) {
					// 大于保持的最大天数删除第一个
					historyData.remove(0);
				}

				StatisticsData statisticsData = SingletonUtil.getInstance();
				historyData.add(JSON.toJSONString(statisticsData));
				statistics.setHistoryDataList(JSON.toJSONString(historyData));
				statisticsData.setCreateDate(DateUtil.getCurDate());
				statistics.setStatisticsData(JSON.toJSONString(statisticsData));
				statistics.setRunProjectPath(statisticsData.getRunProjectPath());

				// 将更新后的 StatisticsData 转换为 XML 字符串
				// 重新写入内容
				// 实现将更新后的内容写入文件的方法
				randomAccessFile.seek(0); // 移动到文件开头
				randomAccessFile.writeBytes(XmlUtil.format(XmlUtil.beanToXml(statistics)));
				randomAccessFile.setLength(randomAccessFile.getFilePointer()); // 截断文件以移除多余数据

				// 重置统计数据
				clearStatisticsData();
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
