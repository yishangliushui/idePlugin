package org.yishang.view;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.ContentFactory;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.yishang.bean.Constant;
import org.yishang.form.CountLinesCodeUI;
import org.yishang.listener.CountLinesCodeListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class CountLinesCodeWindow implements ToolWindowFactory {

	private final CountLinesCodeUI countLinesCodeUI = new CountLinesCodeUI();

	private final JComponent mainPanel;

	private final JTextField filePath = countLinesCodeUI.getFilePath();

	public CountLinesCodeWindow() {
		mainPanel = countLinesCodeUI.getMainPanel();
	}
	@Override
	public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
		toolWindow.getContentManager().addContent(ContentFactory.getInstance().createContent(mainPanel, "", false));
		countLinesCodeUI.getCountLineButton().addActionListener(new CountLinesCodeListener(countLinesCodeUI, project.getBasePath()));
		countLinesCodeUI.getFilePath().addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (filePath.getText().equals(Constant.PLACEHOLDER_MSG)) {
					filePath.setText("");
					//filePath.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (filePath.getText().isEmpty()) {
					filePath.setForeground(Color.GRAY);
					filePath.setText(Constant.PLACEHOLDER_MSG);
				}
			}
		});
		if (StringUtils.isBlank(countLinesCodeUI.getFilePath().getText())){
			countLinesCodeUI.getFilePath().setText(project.getBasePath());
		}
	}
}
