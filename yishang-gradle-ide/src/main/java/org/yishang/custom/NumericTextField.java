package org.yishang.custom;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class NumericTextField extends JTextField {

	public NumericTextField() {
		super();
		((AbstractDocument) getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				if (string == null || !string.matches("[0-9]*")) {
					return; // 阻止非数字字符
				}
				super.insertString(fb, offset, string, attr);
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
				if (text == null || !text.matches("[0-9]*")) {
					return; // 阻止非数字字符
				}
				super.replace(fb, offset, length, text, attrs);
			}
		});
	}
}
