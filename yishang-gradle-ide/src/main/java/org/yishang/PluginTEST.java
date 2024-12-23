package org.yishang;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;
import java.awt.*;

/**
 * @Description: TODO
 * @ClassName: PluginTEST
 * @author: Administrator
 * @date: 2024年11月28日 11:20
 * @Version 1.0
 */
public class PluginTEST {
	public static void main(String[] args) {
		ImageIcon originalIcon = (ImageIcon) IconLoader.getIcon("META-INF/pluginIcon.svg", PluginIcons.class);
		Image image = originalIcon.getImage(); // 转换为 Image
		Image scaledImage = image.getScaledInstance(13, 13, Image.SCALE_SMOOTH); // 缩放图像
		Icon scaledIcon = new ImageIcon(scaledImage); // 转换回 Icon
	}
}
