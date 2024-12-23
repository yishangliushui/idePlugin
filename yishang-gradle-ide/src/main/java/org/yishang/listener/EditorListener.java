package org.yishang.listener;


import com.alibaba.fastjson.JSON;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.event.*;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.yishang.StatisticsData;
import org.yishang.persistent.UITimesState;
import org.yishang.util.SingletonUtil;

import java.util.HashSet;
import java.util.Set;

public class EditorListener implements EditorFactoryListener, BulkAwareDocumentListener, CaretListener {

	private UITimesState uiTimesState = ApplicationManager.getApplication().getService(UITimesState.class);

	private Set<String> fileSet = new HashSet<String>();

	@Override
	public void editorCreated(@NotNull EditorFactoryEvent event) {
		// 避免重复增加监听器
		VirtualFile file = FileDocumentManager.getInstance().getFile(event.getEditor().getDocument());
		if (file == null) {
			return;
		}

		if (fileSet.contains(file.getPath())) {
			return;
		}
		fileSet.add(file.getPath());
		// 监听编辑操作
		event.getEditor().getDocument().addDocumentListener(this);
		// 监听光标移动事件
		event.getEditor().getCaretModel().addCaretListener(this);
	}

	@Override
	public void documentChangedNonBulk(@NotNull DocumentEvent event) {
		StatisticsData statisticsData = SingletonUtil.getInstance();
		if (!event.getOldFragment().isEmpty() || !event.getNewFragment().isEmpty() || !event.isWholeTextReplaced()) {
			String newFragment = event.getNewFragment().toString();
			String oldFragment = event.getOldFragment().toString();
			// 只对字符长度为 1 和非空空白符的情况进行统计
			if ((newFragment.length() == 1 || newFragment.trim().isEmpty())) {
				statisticsData.getKeyCount().incrementAndGet();
			}
			// 根据文档代码段变更信息判断是新增还是删除行
			if (oldFragment.contains("\n")) {
				statisticsData.getRemoveLineCount().addAndGet(countOccurrences(oldFragment, '\n'));
			}
			if (newFragment.contains("\n")) {
				statisticsData.getAddLineCount().addAndGet(countOccurrences(newFragment, '\n'));
			}
		}

	}

	@Override
	public void caretPositionChanged(@NotNull CaretEvent event) {
		// 鼠标移动就赋值.
		//state.statisticsData = Utils.stringify(Utils.initData())
		uiTimesState.setStatisticsData(JSON.toJSONString(SingletonUtil.getInstance()));
	}

	// 辅助方法：计算字符串中某个字符出现的次数
	private int countOccurrences(String str, char ch) {
		return (int) str.chars().filter(c -> c == ch).count();
	}

}
