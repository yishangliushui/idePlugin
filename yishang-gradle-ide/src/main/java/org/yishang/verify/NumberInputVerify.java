package org.yishang.verify;

import javax.swing.*;

public class NumberInputVerify extends InputVerifier {
	@Override
	public boolean verify(JComponent input) {
		String text = ((JTextField) input).getText();
		return text.matches("\\d*"); // 只允许数字
	}
}
