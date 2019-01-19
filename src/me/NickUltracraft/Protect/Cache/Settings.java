package me.NickUltracraft.Protect.Cache;

import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;

import me.NickUltracraft.Protect.Console;
import me.NickUltracraft.Protect.Main;
import me.NickUltracraft.Protect.Console.ConsoleLevel;

/**
 * A class Settings.java do projeto (PLUGIN - nProtect REBUILT) pertence ao NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * Rebuild, do not copy
*/

public class Settings {

	public static HashMap<String, String> settingsMap = new HashMap<>(); 
 	
	public static Settings getInstance() {
		return new Settings();
	}
	public void loadSettings() {
		settingsMap.clear();
		try {
			if(Main.m.getConfig().isSet("Config.UsarTitle")) {
				add("usar_title", loadFromConfig("UsarTitle"));
			}
			if(Main.m.getConfig().isSet("Config.SenhaDefault")) {
				add("senha_default", loadFromConfig("SenhaDefault"));
			}
			addMissingSettings();
		} catch (Exception e) {
			new Console("Falha ao carregar as mensagens. Usando valores default.", ConsoleLevel.ERRO).sendMessage();
			addMissingSettings();
		}
	}
	private void addMissingSettings() {
		add("usar_title", "true");
		add("senha_default", "nprotect");
	}
	private void add(String id, String valor) {
		if(!settingsMap.containsKey(id)) settingsMap.put(id, valor);
	}
	public String loadFromConfig(String path) {
		FileConfiguration config = Main.m.getConfig(); return config.getString("Config." + path);
	}
	public Boolean getCachedSetting(String selectValue) {
		return Boolean.valueOf(settingsMap.get(selectValue));
	}
	public String getCachedValue(String selectValue) {
		return settingsMap.get(selectValue);
	}
	
}

