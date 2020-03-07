/**
 * Copyright NickUC
 * -
 * Esta class pertence ao projeto de NickUC
 * Mais informações: https://nickuc.com
 * -
 * É expressamente proibido alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link para acesso da source original.
 * -
 * Este aviso não pode ser removido ou alterado de qualquer distribuição de origem.
 */

package com.nickuc.protect.management;

import com.nickuc.ncore.api.logger.ConsoleLogger;
import com.nickuc.protect.nProtect;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class Settings {

	private static HashMap<String, String> settingsMap = new HashMap<>();
	public static Set<String> allowedCommands = new HashSet<>();

	public static void load() {
		settingsMap.clear();
		try {
			if(nProtect.nprotect.getConfig().isSet("Config.UsarTitle")) {
				add("usar_title", loadFromConfig("UsarTitle"));
			}
			if(nProtect.nprotect.getConfig().isSet("Config.AutoLogin")) {
				add("auto_login", loadFromConfig("AutoLogin"));
			}
			if(nProtect.nprotect.getConfig().isSet("Config.TempoLogar")) {
				add("tempo_logar", loadFromConfig("TempoLogar"));
			}
			if(nProtect.nprotect.getConfig().isSet("Config.SenhaDefault")) {
				add("senha_default_sem_cargo", loadFromConfig("SenhaDefault"));
			}
			addMissingSettings();
			loadCommands();
			
			for(Map.Entry<String, String> entry : settingsMap.entrySet()) {
				ConsoleLogger.debug("Loaded '" + entry.getKey() + "' -> '" + entry.getValue() + "'");
			}
		} catch (Exception e) {
			ConsoleLogger.warning("Falha ao carregar as configuracoes. Usando valores default.");
			addMissingSettings();
		}
	}

	private static void loadCommands() {
		for (String command : nProtect.nprotect.getConfig().getStringList("Config.ComandosPermitidos")) {
			allowedCommands.add(command.toLowerCase());
		}
	}

	private static void addMissingSettings() {
		add("usar_title", "true");
		add("auto_login", "true");
		add("tempo_logar", "25");
		add("senha_default_sem_cargo", "nprotect_default");
	}

	private static void add(String id, String valor) {
		if(!settingsMap.containsKey(id)) settingsMap.put(id, valor);
	}

	protected static String loadFromConfig(String path) {
		FileConfiguration config = nProtect.nprotect.getConfig(); return config.getString("Config." + path);
	}

	public static Boolean getCachedSetting(String selectValue) {
		return Boolean.valueOf(settingsMap.get(selectValue));
	}

	public static String getCachedValue(String selectValue) {
		return settingsMap.get(selectValue);
	}
	
}

