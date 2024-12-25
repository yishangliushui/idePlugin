package org.yishang.test;


import cn.hutool.core.util.XmlUtil;
import com.alibaba.fastjson.JSON;
import org.w3c.dom.Document;
import org.yishang.StatisticsData;
import org.yishang.bean.Statistics;

public class Test {
	public static void main(String[] args) {
		// 获取配置目录
		String xmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
				"<Statistics>\n" +
				"  <activeTime/>\n" +
				"  <runProjectPath/>\n" +
				"  <statisticsData/>\n" +
				"  <historyDataList/>\n" +
				"</Statistics>";
		StatisticsData statisticsData = parseXmlToStatisticsData(xmlContent);
		Document document = XmlUtil.beanToXml(statisticsData);
		System.out.println(JSON.toJSONString(statisticsData));
		System.out.println(XmlUtil.format(document));

		Statistics statistics = new Statistics();
		System.out.println(XmlUtil.format(XmlUtil.beanToXml(statistics)));
	}

	public static StatisticsData parseXmlToStatisticsData(String xmlContent) {
		Document document = XmlUtil.readXML(xmlContent);
		StatisticsData statisticsData = XmlUtil.xmlToBean(document, StatisticsData.class);

		return statisticsData; // 假设的返回值
	}
}
