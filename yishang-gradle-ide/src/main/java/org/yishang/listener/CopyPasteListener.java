package org.yishang.listener;

import com.intellij.codeInsight.editorActions.CopyPastePreProcessor;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.RawText;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.yishang.StatisticsData;
import org.yishang.util.SingletonUtil;


public class CopyPasteListener implements CopyPastePreProcessor {

	@Override
	public @Nullable String preprocessOnCopy(PsiFile psiFile, int[] ints, int[] ints1, String s) {
		StatisticsData statisticsData = SingletonUtil.getInstance();
		statisticsData.getCopyCount().incrementAndGet();
		return null;
	}

	@Override
	public @NotNull String preprocessOnPaste(Project project, PsiFile psiFile, Editor editor, String text, RawText rawText) {
		StatisticsData statisticsData = SingletonUtil.getInstance();
		statisticsData.getPasteCount().incrementAndGet();
		return text;
	}
}
