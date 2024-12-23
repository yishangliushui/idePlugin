package org.yishang.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static Map<String, String> parseConfigWithRegex(String config) {
		Map<String, String> configMap = new HashMap<>();
		if (config == null || config.isEmpty()) {
			return configMap;
		}

		// 定义正则表达式，匹配键值对
		Pattern pattern = Pattern.compile("^(\\w+):(.+)$", Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(config);

		while (matcher.find()) {
			String key = matcher.group(1).trim();
			String value = matcher.group(2).trim();
			configMap.put(key, value);
		}

		return configMap;
	}

}
