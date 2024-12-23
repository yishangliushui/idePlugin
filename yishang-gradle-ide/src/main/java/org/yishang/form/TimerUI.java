package org.yishang.form;

import lombok.Data;
import org.yishang.custom.NumericTextField;

import javax.swing.*;

@Data
public class TimerUI {
	private JPanel mianJPanel;
	private JTextField alarmTime;
	private JTextArea alarmRuleArea;
	private JTextArea spacedNotifityMsg;
	private JTextField maxHistoryDay;

	public JPanel getMianJPanel() {
		return mianJPanel;
	}

	public void setMianJPanel(JPanel mianJPanel) {
		this.mianJPanel = mianJPanel;
	}

	public JTextField getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(JTextField alarmTime) {
		this.alarmTime = alarmTime;
	}

	public JTextArea getAlarmRuleArea() {
		return alarmRuleArea;
	}

	public void setAlarmRuleArea(JTextArea alarmRuleArea) {
		this.alarmRuleArea = alarmRuleArea;
	}

	public JTextArea getSpacedNotifityMsg() {
		return spacedNotifityMsg;
	}

	public void setSpacedNotifityMsg(JTextArea spacedNotifityMsg) {
		this.spacedNotifityMsg = spacedNotifityMsg;
	}

	public JTextField getMaxHistoryDay() {
		return maxHistoryDay;
	}

	public void setMaxHistoryDay(JTextField maxHistoryDay) {
		this.maxHistoryDay = maxHistoryDay;
	}

	private void createUIComponents() {
		maxHistoryDay = new NumericTextField();
		alarmTime = new NumericTextField();
	}
}
