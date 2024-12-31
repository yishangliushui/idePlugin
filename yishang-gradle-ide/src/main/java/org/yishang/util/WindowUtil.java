package org.yishang.util;

import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;

import java.util.HashMap;
import java.util.Map;

public class WindowUtil {

	public static Map<String, ConsoleView> consoleViews = new HashMap<>();

	public static Map<String, ToolWindow> toolWindows = new HashMap<>();

	// Utils 中的方法
	public static void createToolConsoleWindow(Project project, ToolWindow toolWindow, String title) {
		ConsoleView consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(project).getConsole();
		WindowUtil.consoleViews.put(title + project.getLocationHash(), consoleView);
		Content content = toolWindow.getContentManager().getFactory().createContent(consoleView.getComponent(), title, false);
		content.getComponent().setVisible(true);
		content.setCloseable(true);
		toolWindow.getContentManager().addContent(content);
	}

	public static void consoleInfo(Project project, String msg, String title) {
		// 检查并创建工具窗口
		if (consoleViews.get(title + project.getLocationHash()) == null) {
			ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow(title);
			if (toolWindow != null) {
				createToolConsoleWindow(project, toolWindow, title);
			}
		}

		// 获取控制台视图并打印消息
		ConsoleView consoleView = consoleViews.get(title + project.getLocationHash());
		if (consoleView != null) {
			consoleView.clear();
			consoleView.print(msg, ConsoleViewContentType.NORMAL_OUTPUT);
		}

		// 激活工具窗口
		ToolWindow toolWindow = toolWindows.get(title + project.getLocationHash());
		if (toolWindow != null) {
			toolWindow.activate(null, false);
		}
	}

	public static void consoleError(Project project, String message) {
		//					// 输出到Notiyfy
		Notification notification = new Notification("Attach to Process action", "输出信息", message, NotificationType.WARNING);
		// 在提示消息中，增加一个 Action，可以通过 Action 一步打开配置界面
		//notification.addAction(new CustomSettingAction());
		// 发送通知
		Notifications.Bus.notify(notification, project);
	}
}

