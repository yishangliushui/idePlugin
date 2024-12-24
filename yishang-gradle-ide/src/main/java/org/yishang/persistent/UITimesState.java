package org.yishang.persistent;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// 应用级别
@Service
//@Service(Service.Level.PROJECT) // 只修改该行
@State(name = "UITimesState", storages = {@Storage("ui-times-state.xml")})
public final class UITimesState implements PersistentStateComponent<UITimesState> {

	private String maxHistoryDay;
	private String alarmTime;
	private String alarmRule;

	//private Long activeInterval;
	//private Long updateInterval;

	private Long activeTime = Long.MAX_VALUE;

	// 运行项目路径
	private String runProjectPath = "";

	// 当日统计数据
	private String statisticsData = "{}";

	// 历史数据
	private String historyData = "[]";


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

	public String getHistoryData() {
		return historyData;
	}

	public void setHistoryData(String historyData) {
		this.historyData = historyData;
	}

	@Override
	public @Nullable UITimesState getState() {
		return this;
	}

	@Override
	public void loadState(@NotNull UITimesState uiDemoState) {
		XmlSerializerUtil.copyBean(uiDemoState, this);
	}

	public String getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}

	public String getAlarmRule() {
		return alarmRule;
	}

	public void setAlarmRule(String alarmRule) {
		this.alarmRule = alarmRule;
	}

	public String getMaxHistoryDay() {
		return maxHistoryDay;
	}

	public void setMaxHistoryDay(String maxHistoryDay) {
		this.maxHistoryDay = maxHistoryDay;
	}

	public Long getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Long activeTime) {
		this.activeTime = activeTime;
	}
//
//	public void setUpdateInterval(Long updateInterval) {
//		this.updateInterval = updateInterval;
//	}
//
//	public void setActiveInterval(Long activeInterval) {
//		this.activeInterval = activeInterval;
//	}
//
//	public Long getActiveInterval() {
//		return null == activeInterval ? 30 : activeInterval;
//	}
//
//	public Long getUpdateInterval() {
//		return null == updateInterval ? 30 : updateInterval;
//	}
}
