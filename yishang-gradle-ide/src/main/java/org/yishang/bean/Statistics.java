package org.yishang.bean;

public class Statistics {

	// 运行项目路径
	private String runProjectPath;

	// 当日统计数据
	private String statisticsData;

	// 历史数据
	private String historyDataList;


	public String getRunProjectPath() {
		return runProjectPath;
	}

	public void setRunProjectPath(String runProjectPath) {
		this.runProjectPath = runProjectPath;
	}

	public String getStatisticsData() {
		return statisticsData;
	}

	public void setStatisticsData(String statisticsData) {
		this.statisticsData = statisticsData;
	}

	public String getHistoryDataList() {
		return historyDataList;
	}

	public void setHistoryDataList(String historyDataList) {
		this.historyDataList = historyDataList;
	}
}
