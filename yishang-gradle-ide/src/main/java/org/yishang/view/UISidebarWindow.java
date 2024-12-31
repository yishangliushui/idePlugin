package org.yishang.view;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.ContentFactory;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.yishang.bean.Constant;
import org.yishang.form.CountLinesCodeUI;
import org.yishang.form.MybatisGenerator;
import org.yishang.listener.AutoGeneratorTableListener;
import org.yishang.listener.CountLinesCodeListener;
import org.yishang.persistent.UIGenerateState;
import org.yishang.util.WindowUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class UISidebarWindow implements ToolWindowFactory {

	private UIGenerateState generateState = ApplicationManager.getApplication().getService(UIGenerateState.class);

	@Override
	public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
		// 创建mybatis-generator-ui
		MybatisGenerator mybatisGenerator = new MybatisGenerator();
		toolWindow.getContentManager().addContent(ContentFactory.getInstance().createContent(mybatisGenerator.getMainPanel(), "MybatisGenerator", false));
		mybatisGenerator.getGeneratorButton().addActionListener(new AutoGeneratorTableListener(mybatisGenerator, generateState));
		mybatisGenerator.getGeneratorTextArea().setText(generateState.getTextArea());


		// 创建count-lines-code
		CountLinesCodeUI countLinesCodeUI = new CountLinesCodeUI();
		JTextField filePath = countLinesCodeUI.getFilePath();
		toolWindow.getContentManager().addContent(ContentFactory.getInstance().createContent(countLinesCodeUI.getMainPanel(), "LinesCode", false));
		WindowUtil.toolWindows.put(Constant.COUNT_LINES_CONTROL_TITLE + project.getLocationHash(), toolWindow);
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
		if (StringUtils.isBlank(countLinesCodeUI.getFilePath().getText())) {
			countLinesCodeUI.getFilePath().setText(project.getBasePath());
		}
	}

}
