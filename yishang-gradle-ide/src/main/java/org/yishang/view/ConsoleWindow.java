package org.yishang.view;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import org.jetbrains.annotations.NotNull;
import org.yishang.util.WindowUtil;

public class ConsoleWindow implements ToolWindowFactory {

	@Override
	public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
		ConsoleView consoleView = WindowUtil.consoleViews.get(project);
		if (consoleView == null) {
			WindowUtil.createToolWindow(project, toolWindow);
		}
		WindowUtil.toolWindows.put(project, toolWindow);
	}

}
