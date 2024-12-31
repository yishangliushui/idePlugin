package org.yishang.view;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import org.jetbrains.annotations.NotNull;
import org.yishang.bean.Constant;
import org.yishang.util.WindowUtil;

public class ConsoleWindow implements ToolWindowFactory {

	@Override
	public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
		ConsoleView consoleView = WindowUtil.consoleViews.get(Constant.TIMER_MASTER_CONTROL_TITLE + project);
		if (consoleView == null) {
			WindowUtil.createToolConsoleWindow(project, toolWindow, Constant.TIMER_MASTER_CONTROL_TITLE);
		}
		WindowUtil.toolWindows.put(Constant.TIMER_MASTER_CONTROL_TITLE + project, toolWindow);
	}

}
