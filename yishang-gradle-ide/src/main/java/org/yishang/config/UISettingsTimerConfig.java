package org.yishang.config;


import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.util.NlsContexts;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.yishang.form.TimerUI;
import org.yishang.persistent.UITimesState;

import javax.swing.*;
import java.util.Objects;

public class UISettingsTimerConfig implements Configurable {
	private TimerUI timerUI = new TimerUI();
	private UITimesState uiTimesState = ApplicationManager.getApplication().getService(UITimesState.class);

	public UISettingsTimerConfig() {
		reset();
	}

	@Override
	public @NlsContexts.ConfigurableName String getDisplayName() {
		return "UISettingsTimerConfig";
	}

	@Override
	public @Nullable JComponent createComponent() {
		return timerUI.getMianJPanel();
	}

	@Override
	public boolean isModified() {
//		if (Objects.equals(timerUI.getAlarmTime().getText(), uiTimesState.getAlarmTime())
//				&& Objects.equals(timerUI.getAlarmRuleArea().getText(), uiTimesState.getAlarmRule())
//				&& Objects.equals(timerUI.getActiveInterval().getText(), uiTimesState.getActiveInterval().toString())
//				&& Objects.equals(timerUI.getUpdateInterval().getText(), uiTimesState.getUpdateInterval().toString())) {
//			return false;
//		}
		if (Objects.equals(timerUI.getAlarmTime().getText(), uiTimesState.getAlarmTime())
				&& Objects.equals(timerUI.getAlarmRuleArea().getText(), uiTimesState.getAlarmRule())
				&& Objects.equals(timerUI.getMaxHistoryDay().getText(), uiTimesState.getMaxHistoryDay())) {
			return false;
		}
		return true;

	}

	@Override
	public void apply() {
		uiTimesState.setAlarmTime(timerUI.getAlarmTime().getText());
		uiTimesState.setAlarmRule(timerUI.getAlarmRuleArea().getText());
		uiTimesState.setMaxHistoryDay(timerUI.getMaxHistoryDay().getText());
//		String active = timerUI.getActiveInterval().getText();
//		uiTimesState.setActiveInterval(StringUtils.isBlank(active) ? 30 : Long.parseLong(active));
//
//		String update = timerUI.getUpdateInterval().getText();
//		uiTimesState.setUpdateInterval(StringUtils.isBlank(update) ? 30 : Long.parseLong(update));
	}

	@Override
	public void reset() {
		timerUI.getAlarmTime().setText(StringUtils.isNotBlank(uiTimesState.getAlarmTime()) ? uiTimesState.getAlarmTime() : "1800");
		timerUI.getAlarmRuleArea().setText(uiTimesState.getAlarmRule());
		timerUI.getMaxHistoryDay().setText(StringUtils.isNotBlank(uiTimesState.getMaxHistoryDay()) ? uiTimesState.getMaxHistoryDay() : "365");
//		timerUI.getActiveInterval().setText(uiTimesState.getActiveInterval() != null ? uiTimesState.getActiveInterval() + "" : "30");
//		timerUI.getUpdateInterval().setText(uiTimesState.getUpdateInterval() != null ? uiTimesState.getUpdateInterval() + "" : "30");
	}

}
