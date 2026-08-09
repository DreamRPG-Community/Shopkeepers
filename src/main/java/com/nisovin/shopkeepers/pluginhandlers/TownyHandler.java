package com.nisovin.shopkeepers.pluginhandlers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Method;

public class TownyHandler {

	public static final String PLUGIN_NAME = "Towny";

	public static Plugin getPlugin() {
		return Bukkit.getPluginManager().getPlugin(PLUGIN_NAME);
	}

	public static boolean isPluginEnabled() {
		return Bukkit.getPluginManager().isPluginEnabled(PLUGIN_NAME);
	}

	public static boolean isCommercialArea(Location location) {
		if (!isPluginEnabled()) return false;
		try {
			Class<?> townyUniverse = Class.forName("com.palmergames.bukkit.towny.object.TownyUniverse");
			Method getTownBlock = townyUniverse.getMethod("getTownBlock", Location.class);
			Object townBlock = getTownBlock.invoke(null, location);
			if (townBlock == null) return false;
			Object townBlockType = townBlock.getClass().getMethod("getType").invoke(townBlock);
			return townBlockType != null && "COMMERCIAL".equals(townBlockType.toString());
		} catch (ReflectiveOperationException | LinkageError ignored) {
			return false;
		}
	}
}
