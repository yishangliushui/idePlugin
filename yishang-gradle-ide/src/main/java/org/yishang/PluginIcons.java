package org.yishang;

import com.intellij.openapi.util.IconLoader;
import com.intellij.util.IconUtil;

import javax.swing.*;


public interface PluginIcons {
	Icon ICON_ORIGIN = IconLoader.getIcon("META-INF/pluginIcon.svg", PluginIcons.class);
	Icon ICON_13 = IconUtil.scale(PluginIcons.ICON_ORIGIN, null, 13f / PluginIcons.ICON_ORIGIN.getIconWidth());
	Icon ICON_40 = IconUtil.scale(PluginIcons.ICON_ORIGIN, null, 40f / PluginIcons.ICON_ORIGIN.getIconWidth());
}
