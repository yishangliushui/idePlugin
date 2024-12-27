package org.yishang.listener;

import com.intellij.openapi.ui.Messages;
import org.apache.commons.lang3.StringUtils;
import org.yishang.bean.Constant;
import org.yishang.bean.LineCode;
import org.yishang.form.CountLinesCodeUI;
import org.yishang.util.CountLinesCodeUtil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class CountLinesCodeListener implements ActionListener {

	private CountLinesCodeUI countLinesCodeUI;

	private String basePath;

	public CountLinesCodeListener(CountLinesCodeUI countLinesCodeUI, String basePath) {
		this.countLinesCodeUI = countLinesCodeUI;
		this.basePath = basePath;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 获取文件地址
		String filePath = countLinesCodeUI.getFilePath().getText().trim();
		if (StringUtils.isBlank(filePath) || Constant.PLACEHOLDER_MSG.equals(filePath)) {
			//修改为获取当前项目根路径
			filePath = basePath;
		}
		// 判断是文件，还是目录
		File file = new File(filePath);
		if (!file.exists()) {
			// 提示文件不存在
			Messages.showInfoMessage("文件目录不存在", "LinesCode");
		}
		// 统计目录下所有文件(java)的行数
		List<LineCode> lineCodes = CountLinesCodeUtil.countJavaLines(filePath);
		LineCode total = new LineCode();
		if (!lineCodes.isEmpty()) {
			for (LineCode lineCode : lineCodes) {
				total.setFileName("total");
				total.setBlankLines(lineCode.getBlankLines() + total.getBlankLines());
				total.setCommentLines(lineCode.getCommentLines() + total.getCommentLines());
				total.setJavaLines(lineCode.getJavaLines() + total.getJavaLines());
			}
		}
		StringBuilder result = new StringBuilder();
		result.append("<html><body><table border='1'>");

		// 添加表头
		result.append("<tr>")
				.append("<th style='font-weight: bold;'>文件名</th>")
				.append("<th style='font-weight: bold;'>代码行数</th>")
				.append("<th style='font-weight: bold;'>注释行数</th>")
				.append("<th style='font-weight: bold;'>空白行数</th>")
				.append("</tr>");

		// 添加总计行
		result.append("<tr>")
				.append("<td>").append(total.getFileName()).append("</td>")
				.append("<td>").append(total.getJavaLines()).append("</td>")
				.append("<td>").append(total.getCommentLines()).append("</td>")
				.append("<td>").append(total.getBlankLines()).append("</td>")
				.append("</tr>");

		// 处理详情结果
		for (LineCode lineCode : lineCodes) {
			result.append("<tr>")
					.append("<td>").append(lineCode.getFileName()).append("</td>")
					.append("<td>").append(lineCode.getJavaLines()).append("</td>")
					.append("<td>").append(lineCode.getCommentLines()).append("</td>")
					.append("<td>").append(lineCode.getBlankLines()).append("</td>")
					.append("</tr>");
		}

		result.append("</table></body></html>");

		countLinesCodeUI.getShowTextPane().setContentType("text/html");
		countLinesCodeUI.getShowTextPane().setText(result.toString());

	}

}
