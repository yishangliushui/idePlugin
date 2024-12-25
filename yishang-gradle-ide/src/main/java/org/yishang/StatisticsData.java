package org.yishang;

import org.yishang.util.DateUtil;

import java.util.concurrent.atomic.AtomicLong;

public class StatisticsData {

	// 运行项目路径
	private String runProjectPath = "";
	private volatile AtomicLong runTime = new AtomicLong(0L);
	private volatile AtomicLong activeTime = new AtomicLong(0L);
	private volatile AtomicLong keyCount = new AtomicLong(0L);
	private volatile AtomicLong addLineCount = new AtomicLong(0L);
	private volatile AtomicLong removeLineCount = new AtomicLong(0L);
	private volatile AtomicLong copyCount = new AtomicLong(0L);
	private volatile AtomicLong pasteCount = new AtomicLong(0L);
	private volatile AtomicLong pushCount = new AtomicLong(0L);
	private String createDate = DateUtil.getCurDate();


	public AtomicLong getRunTime() {
		return runTime;
	}

	public void setRunTime(AtomicLong runTime) {
		this.runTime = runTime;
	}

	public AtomicLong getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(AtomicLong activeTime) {
		this.activeTime = activeTime;
	}

	public AtomicLong getKeyCount() {
		return keyCount;
	}

	public void setKeyCount(AtomicLong keyCount) {
		this.keyCount = keyCount;
	}

	public AtomicLong getAddLineCount() {
		return addLineCount;
	}

	public void setAddLineCount(AtomicLong addLineCount) {
		this.addLineCount = addLineCount;
	}

	public AtomicLong getRemoveLineCount() {
		return removeLineCount;
	}

	public void setRemoveLineCount(AtomicLong removeLineCount) {
		this.removeLineCount = removeLineCount;
	}

	public AtomicLong getCopyCount() {
		return copyCount;
	}

	public void setCopyCount(AtomicLong copyCount) {
		this.copyCount = copyCount;
	}

	public AtomicLong getPasteCount() {
		return pasteCount;
	}

	public void setPasteCount(AtomicLong pasteCount) {
		this.pasteCount = pasteCount;
	}

	public AtomicLong getPushCount() {
		return pushCount;
	}

	public void setPushCount(AtomicLong pushCount) {
		this.pushCount = pushCount;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getRunProjectPath() {
		return runProjectPath;
	}

	public void setRunProjectPath(String runProjectPath) {
		this.runProjectPath = runProjectPath;
	}
}
