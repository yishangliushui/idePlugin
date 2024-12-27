package org.yishang.bean;

public class LineCode {

	private String fileName;

	private int javaLines;
	// 空白
	private int blankLines;
	// 注释
	private int commentLines;

	public LineCode() {
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getJavaLines() {
		return javaLines;
	}

	public void setJavaLines(int javaLines) {
		this.javaLines = javaLines;
	}

	public int getBlankLines() {
		return blankLines;
	}

	public void setBlankLines(int blankLines) {
		this.blankLines = blankLines;
	}

	public int getCommentLines() {
		return commentLines;
	}

	public void setCommentLines(int commentLines) {
		this.commentLines = commentLines;
	}
}
