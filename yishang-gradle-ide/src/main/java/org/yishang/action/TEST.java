package org.yishang.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;


public class TEST extends AnAction {

	@Override
	public void actionPerformed(AnActionEvent e) {
		Messages.showInfoMessage("1111", "Hello World");
	}
}
