package org.yishang.action;


import com.alibaba.fastjson.JSON;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.yishang.StatisticsData;
import org.yishang.bean.Statistics;
import org.yishang.util.SingletonUtil;
import org.yishang.util.WindowUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class TimerMasterOutputAction extends AnAction {

	//private UITimesState uiTimesState = ApplicationManager.getApplication().getService(UITimesState.class);

	@Override
	public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
		//StatisticsData statisticsData = SingletonUtil.getInstance();
		Statistics statistics = SingletonUtil.readStatisticsFile();
		StatisticsData statisticsData = JSON.parseObject(statistics.getStatisticsData(), StatisticsData.class);
//		// 获取今日数据
		StringBuilder stringBuilder = new StringBuilder();
		String today = String.format("🐻 (%s)%s%s", statisticsData.getCreateDate(), System.lineSeparator(), getBodyContent(statisticsData));
		stringBuilder.append(today).append(System.lineSeparator());
		// 对历史数据进行倒序排序
		//WindowUtil.consoleInfo(anActionEvent.getProject(), today);
		List<StatisticsData> historyDataList = new ArrayList<>();
		if (StringUtils.isNotBlank(statistics.getHistoryDataList())) {
			List<String> historyData = JSON.parseObject(statistics.getHistoryDataList(), List.class);
			historyDataList = historyData.stream().map(s -> JSON.parseObject(s, StatisticsData.class)).sorted((o1, o2) -> o2.getCreateDate().compareTo(o1.getCreateDate())).collect(Collectors.toList());
			for (StatisticsData data : historyDataList) {
				String format = String.format("🐻 (%s)%s%s", data.getCreateDate(), System.lineSeparator(), getBodyContent(data));
				//WindowUtil.consoleInfo(anActionEvent.getProject(), format);
				stringBuilder.append(format).append(System.lineSeparator());
			}

			if (!historyDataList.isEmpty()) {
				StatisticsData data = historyDataList.get(0);
				if (data.getCreateDate().equals(statisticsData.getCreateDate())) {
					data.getActiveTime().addAndGet(statisticsData.getActiveTime().longValue());
					data.getKeyCount().addAndGet(statisticsData.getKeyCount().longValue());
					data.getAddLineCount().addAndGet(statisticsData.getAddLineCount().longValue());
					data.getRemoveLineCount().addAndGet(statisticsData.getRemoveLineCount().longValue());
					data.getPasteCount().addAndGet(statisticsData.getPasteCount().longValue());
					data.getRunTime().addAndGet(statisticsData.getRunTime().longValue());
					data.getPushCount().addAndGet(statisticsData.getPushCount().longValue());
				} else {
					historyDataList.add(statisticsData);
				}
			} else {
				historyDataList.add(statisticsData);
			}
		} else {
			historyDataList.add(statisticsData);
		}

		StatisticsData statisticsList = statistics(historyDataList, null);
		String last = String.format("🐲 Average of Day%s%s", System.lineSeparator(), getBodyContent(statisticsList));
		stringBuilder.append(last).append(System.lineSeparator());
		WindowUtil.consoleInfo(anActionEvent.getProject(), stringBuilder.toString());

	}

	private String getBodyContent(StatisticsData statisticsData) {
		StringBuilder stringBuilder = new StringBuilder();
		double runTime = (double) statisticsData.getRunTime().longValue() / (3600 * 1000);
		String runTimeStr = "小时";
		if (runTime < 1) {
			runTime = (double) statisticsData.getRunTime().longValue() / (60 * 1000);
			runTimeStr = "分钟";
		}

		double activeTime = (double) statisticsData.getActiveTime().longValue() / (3600 * 1000);
		String activeTimeStr = "小时";
		if (activeTime < 1) {
			activeTime =  (double) statisticsData.getActiveTime().longValue() / (60 * 1000);
			activeTimeStr = "分钟";
		}
		stringBuilder.append("编辑器使用时间: ").append(String.format("%.2f", runTime)).append(runTimeStr).append("\n");
		stringBuilder.append("编辑器活跃时间: ").append(String.format("%.2f", activeTime)).append(activeTimeStr).append("\n");
		stringBuilder.append("添加的代码行数: ").append(statisticsData.getAddLineCount()).append("\n");
		stringBuilder.append("删除的代码行数: ").append(statisticsData.getRemoveLineCount()).append("\n");
		stringBuilder.append("总的键入数: ").append(statisticsData.getKeyCount()).append("\n");
		stringBuilder.append("CTRL+C 次数: ").append(statisticsData.getCopyCount()).append("\n");
		stringBuilder.append("CTRL+V 次数: ").append(statisticsData.getPasteCount()).append("\n");
		stringBuilder.append("代码提交次数: ").append(statisticsData.getPushCount()).append("\n");
		return stringBuilder.toString();
	}

	private StatisticsData statistics(List<StatisticsData> list, String date) {
		StatisticsData data = new StatisticsData();
		if (list.isEmpty()) {
			return data;
		}
		for (StatisticsData statisticsData : list) {
//			if (date != null && LocalDate.parse(statisticsData.getCreateDate()).isBefore(LocalDate.parse(date))) {
//				break;
//			}
			data.getRunTime().addAndGet(statisticsData.getRunTime().longValue());
			data.getActiveTime().addAndGet(statisticsData.getActiveTime().longValue());
			data.getAddLineCount().addAndGet(statisticsData.getAddLineCount().longValue());
			data.getRemoveLineCount().addAndGet(statisticsData.getRemoveLineCount().longValue());
			data.getKeyCount().addAndGet(statisticsData.getKeyCount().longValue());
			data.getCopyCount().addAndGet(statisticsData.getCopyCount().longValue());
			data.getPasteCount().addAndGet(statisticsData.getPasteCount().longValue());
			data.getPushCount().addAndGet(statisticsData.getPushCount().longValue());
		}

		int size = list.size();
		data.setRunTime(new AtomicLong(data.getRunTime().longValue() / size));
		data.setActiveTime(new AtomicLong(data.getActiveTime().longValue() / size));
		data.setKeyCount(new AtomicLong(data.getKeyCount().longValue() / size));
		data.setAddLineCount(new AtomicLong(data.getAddLineCount().longValue() / size));
		data.setRemoveLineCount(new AtomicLong(data.getRemoveLineCount().longValue() / size));
		data.setCopyCount(new AtomicLong(data.getCopyCount().longValue() / size));
		data.setPasteCount(new AtomicLong(data.getPasteCount().longValue() / size));
		data.setPushCount(new AtomicLong(data.getPushCount().longValue() / size));
		return data;
	}
}
