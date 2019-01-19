package me.NickUltracraft.Protect.Cache;

import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;

import me.NickUltracraft.Protect.Main;

/**
 * A class Settings.java do projeto (PLUGIN - nProtect REBUILT) pertence ao NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * Rebuild, do not copy
*/

public class Settings {

	public static HashMap<String, Boolean> settingsMap = new HashMap<>(); 
 	
	public static Settings getInstance() {
		return new Settings();
	}
	public void loadSettings() {
		settingsMap.put("usar_title", true);
	}
	public boolean loadFromConfig(String path) {
		FileConfiguration config = Main.m.getConfig(); return config.getBoolean("Config." + path);
	}
	public Boolean getCachedSetting(String selectValue) {
		return settingsMap.get(selectValue);
	}
	
}

