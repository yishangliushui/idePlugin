package org.yishang.util;

import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;

import java.util.HashMap;
import java.util.Map;

public class WindowUtil {

	public static Map<Project, ConsoleView> consoleViews = new HashMap<>();

	public static Map<Project, ToolWindow> toolWindows = new HashMap<>();

	// Utils 中的方法
	public static void createToolWindow(Project project, ToolWindow toolWindow) {
		ConsoleView consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(project).getConsole();
		WindowUtil.consoleViews.put(project, consoleView);
		Content content = toolWindow.getContentManager().getFactory().createContent(consoleView.getComponent(), "TimerMaster Output", false);
		content.getComponent().setVisible(true);
		content.setCloseable(true);
		toolWindow.getContentManager().addContent(content);
	}

	public static void consoleInfo(Project project, String msg) {
		// 检查并创建工具窗口
		if (consoleViews.get(project) == null) {
			ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow("TimerMaster Console");
			if (toolWindow != null) {
				createToolWindow(project, toolWindow);
			}
		}

		// 获取控制台视图并打印消息
		ConsoleView consoleView = consoleViews.get(project);
		if (consoleView != null) {
			consoleView.clear();
			consoleView.print(msg, ConsoleViewContentType.NORMAL_OUTPUT);
		}

		// 激活工具窗口
		ToolWindow toolWindow = toolWindows.get(project);
		if (toolWindow != null) {
			toolWindow.activate(null, false);
		}
	}
}

