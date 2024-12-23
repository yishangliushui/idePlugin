package org.yishang.persistent;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// 应用级别
@Service
//@Service(Service.Level.PROJECT) // 只修改该行
@State(name = "UIGenerateState", storages = {@Storage("ui-generator-state.xml")})
public final class UIGenerateState implements PersistentStateComponent<UIGenerateState> {

	private String textArea;
	private String mysqlAddress;
	private String userName;
	private String password;
	private String generatePath;
	private String generateTable;

	public String getTextArea() {
		return textArea;
	}

	public void setTextArea(String textArea) {
		this.textArea = textArea;
	}

	public String getMysqlAddress() {
		return mysqlAddress;
	}

	public void setMysqlAddress(String mysqlAddress) {
		this.mysqlAddress = mysqlAddress;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGeneratePath() {
		return generatePath;
	}

	public void setGeneratePath(String generatePath) {
		this.generatePath = generatePath;
	}

	public String getGenerateTable() {
		return generateTable;
	}

	public void setGenerateTable(String generateTable) {
		this.generateTable = generateTable;
	}

	@Override
	public @Nullable UIGenerateState getState() {
		return this;
	}

	@Override
	public void loadState(@NotNull UIGenerateState uiGenerateState) {
		XmlSerializerUtil.copyBean(uiGenerateState, this);
	}
}
