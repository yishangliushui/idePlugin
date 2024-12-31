package org.yishang.util;


import org.yishang.bean.LineCode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CountLinesCodeUtil {
	public static List<LineCode> countJavaLines(String directoryPath) {
		List<LineCode> lineCodeList = new ArrayList<>();
		File directory = new File(directoryPath);
		if (!directory.isDirectory()) {
			lineCodeList.add(countLinesInFile(directoryPath));
			;
		} else {
			File[] files = directory.listFiles();
			if (files != null) {
				for (File file : files) {
					if (file.isDirectory()) {
						lineCodeList.addAll(countJavaLines(file.getAbsolutePath()));
						;
					} else if (file.getName().endsWith(".java")) {
						lineCodeList.add(countLinesInFile(file.getAbsolutePath()));
						;
					}
				}
			}
		}
		return lineCodeList;
	}

	public static LineCode countLinesInFile(String filePath) {
		LineCode lineCode = new LineCode();
		lineCode.setFileName(filePath);
		int lineCount = 0;
		int blankLines = 0;
		int commentLines = 0;
		boolean inBlockComment = false;
		try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
			for (String line : lines.toList()) {
				line = line.trim();
				if (line.isEmpty()) {
					blankLines++;
					continue;
				}
				if (line.startsWith("//") || inBlockComment) {
					if (line.startsWith("/*")) {
						inBlockComment = true;
					}
					if (line.endsWith("*/")) {
						inBlockComment = false;
					}
					commentLines++;
					continue;
				}
				if (line.contains("/*")) {
					inBlockComment = true;
					commentLines++;
					continue;
				}
				lineCount++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		lineCode.setJavaLines(lineCount);
		lineCode.setCommentLines(commentLines);
		lineCode.setBlankLines(blankLines);
		return lineCode;
	}
}
