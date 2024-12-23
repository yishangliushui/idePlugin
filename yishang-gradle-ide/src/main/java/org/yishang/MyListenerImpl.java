package org.yishang;

import com.intellij.openapi.ui.Messages;

public class MyListenerImpl implements MyListener {
	@Override
	public void onClick() {
		Messages.showInfoMessage("1234567", "Title");
	}
}
