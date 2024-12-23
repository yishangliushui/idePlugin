package org.yishang.listener;

import com.intellij.openapi.ui.Messages;
import org.apache.commons.lang3.StringUtils;
import org.yishang.AutoGenerator;
import org.yishang.form.MybatisGenerator;
import org.yishang.persistent.UIGenerateState;
import org.yishang.util.StringUtil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class AutoGeneratorTableListener implements ActionListener {

	private MybatisGenerator mybatisGenerator;
	private UIGenerateState generateState;

	public AutoGeneratorTableListener(MybatisGenerator mybatisGenerator, UIGenerateState generateState) {
		this.mybatisGenerator = mybatisGenerator;
		this.generateState = generateState;
	}

	public static void main(String[] args) {
		String config = "mysqlAddress:127.0.0.1:3306\n" +
				"userName:root\n" +
				"password:123456\n" +
				"generatePath:/path/to/generate\n" +
				"author:JohnDoe\n" +
				"package:com.example.app";

		Map<String, String> configMap = StringUtil.parseConfigWithRegex(config);

		// 打印解析后的配置
		configMap.forEach((key, value) -> System.out.println(key + ": " + value));
	}


	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		String textArea = mybatisGenerator.getGeneratorTextArea().getText();
		if (StringUtils.isBlank(textArea)) {
			Messages.showInfoMessage("规则不能为空", "mybatisGenerator");
			return;
		}
		Map<String, String> parseMap = StringUtil.parseConfigWithRegex(textArea);
		String mysqlAddress = parseMap.getOrDefault("mysqlAddress", "").trim();
		String tables = parseMap.getOrDefault("tables", "").trim();
		String userName = parseMap.getOrDefault("userName", "").trim();
		String password = parseMap.getOrDefault("password", "").trim();
		String generatePath = parseMap.getOrDefault("generatePath", "").trim();
		String author = parseMap.getOrDefault("author", "").trim();
		String packageName = parseMap.getOrDefault("package", "").trim();
		String libraryPackageName = parseMap.getOrDefault("libraryPackageName", "").trim();
		generateState.setTextArea(textArea);
		if (StringUtils.isBlank(mysqlAddress)) {
			Messages.showInfoMessage("mysqlAddress不能为空", "mybatisGenerator");
			return;
		}
		if (StringUtils.isBlank(tables)) {
			Messages.showInfoMessage("tables不能为空", "mybatisGenerator");
			return;
		}
		if (StringUtils.isBlank(generatePath)) {
			Messages.showInfoMessage("generatePath不能为空", "mybatisGenerator");
			return;
		}
		if (StringUtils.isBlank(userName)) {
			Messages.showInfoMessage("userName不能为空", "mybatisGenerator");
			return;
		}
		if (StringUtils.isBlank(password)) {
			Messages.showInfoMessage("password不能为空", "mybatisGenerator");
			return;
		}
		if (StringUtils.isBlank(author)) {
			author = System.getProperty("user.name");
		}

		try {
			AutoGenerator.generator(mysqlAddress, userName, password, tables,
					generatePath, author, packageName, libraryPackageName);
			Messages.showInfoMessage("表生成成功", "mybatisGenerator");
		} catch (Exception e) {
			e.printStackTrace();
			Messages.showInfoMessage(e.getMessage(), "mybatisGenerator");
		}

	}
//	public void actionPerformed(ActionEvent actionEvent) {
//		try {
//			if (StringUtils.isBlank(mybatisGenerator.getMysqlAddress().getText())) {
//				Messages.showInfoMessage("请输入要数据库地址", "mybatisGenerator");
//				return;
//			}
//			if (StringUtils.isBlank(mybatisGenerator.getGenerateTable().getText())) {
//				Messages.showInfoMessage("请输入要生成的表名", "mybatisGenerator");
//				return;
//			}
//			if (StringUtils.isBlank(mybatisGenerator.getGeneratePath().getText())) {
//				Messages.showInfoMessage("请指定生成目录（绝对路径）", "mybatisGenerator");
//				return;
//			}
//			if (StringUtils.isBlank(mybatisGenerator.getUserName().getText())) {
//				Messages.showInfoMessage("请输入要数据库账号", "mybatisGenerator");
//				return;
//			}
//			if (StringUtils.isBlank(mybatisGenerator.getPassword().getText())) {
//				Messages.showInfoMessage("请输入要数据库密码", "mybatisGenerator");
//				return;
//			}
//
//			generateState.setMysqlAddress(mybatisGenerator.getMysqlAddress().getText());
//			generateState.setUserName(mybatisGenerator.getUserName().getText());
//			generateState.setPassword(mybatisGenerator.getPassword().getText());
//			generateState.setGenerateTable(mybatisGenerator.getGenerateTable().getText());
//			generateState.setGeneratePath(mybatisGenerator.getGeneratePath().getText());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		try {
//			AutoGenerator.generator(mybatisGenerator.getMysqlAddress().getText(),
//					mybatisGenerator.getUserName().getText(),
//					mybatisGenerator.getPassword().getText(),
//					mybatisGenerator.getGenerateTable().getText(),
//					mybatisGenerator.getGeneratePath().getText(),
//					System.getProperty("user.name"));
//			Messages.showInfoMessage("表生成成功", "mybatisGenerator");
//		} catch (Exception e) {
//			e.printStackTrace();
//			Messages.showInfoMessage(e.getMessage(), "mybatisGenerator");
//		}
//
//	}
}
