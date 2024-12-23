package org.yishang.view;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import org.yishang.form.MybatisGenerator;
import org.yishang.listener.AutoGeneratorTableListener;
import org.yishang.persistent.UIGenerateState;

import javax.swing.*;

public class UISidebarWindow implements ToolWindowFactory {

	private MybatisGenerator mybatisGenerator = new MybatisGenerator();
	private UIGenerateState generateState = ApplicationManager.getApplication().getService(UIGenerateState.class);

	private JComponent mainPanel;

	public UISidebarWindow() {
		mainPanel = mybatisGenerator.getMainPanel();
	}

	@Override
	public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
		toolWindow.getContentManager().addContent(ContentFactory.getInstance().createContent(mainPanel, "", false));

//		mybatisGenerator.getMysqlAddress().setText(generateState.getMysqlAddress());
//		mybatisGenerator.getUserName().setText(generateState.getUserName());
//		mybatisGenerator.getPassword().setText(generateState.getPassword());
//		mybatisGenerator.getGenerateTable().setText(generateState.getGenerateTable());
//		mybatisGenerator.getGeneratePath().setText(generateState.getGeneratePath());
		mybatisGenerator.getGeneratorButton().addActionListener(new AutoGeneratorTableListener(mybatisGenerator, generateState));
		mybatisGenerator.getGeneratorTextArea().setText(generateState.getTextArea());
	}

}
