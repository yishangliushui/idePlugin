package org.yishang.form;

import javax.swing.*;

public class CountLinesCodeUI {

	private JTextField filePath;
	private JButton countLineButton;
	private JTextPane showTextPane;
	private JPanel mainPanel;


	public JPanel getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	public JTextField getFilePath() {
		return filePath;
	}

	public void setFilePath(JTextField filePath) {
		this.filePath = filePath;
	}

	public JButton getCountLineButton() {
		return countLineButton;
	}

	public void setCountLineButton(JButton countLineButton) {
		this.countLineButton = countLineButton;
	}

	public JTextPane getShowTextPane() {
		return showTextPane;
	}

	public void setShowTextPane(JTextPane showTextPane) {
		this.showTextPane = showTextPane;
	}
}
