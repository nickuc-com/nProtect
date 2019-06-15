package nickultracraft.protect.objects;

/**
 * Copyright 2019 NickUltracraft
 *
 * A class Settings.java pertence ao projeto (PLUGIN - nProtectV2) pertencente à NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * É expressamente proibído alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link para acesso da source original.
 *
 * Este aviso não pode ser removido ou alterado de qualquer distribuição de origem.
*/

import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;

import nickultracraft.protect.nProtect;
import nickultracraft.protect.api.ConsoleLogger;

public class Settings {

	public static HashMap<String, String> settingsMap = new HashMap<>(); 
 	
	public static Settings getInstance() {
		return new Settings();
	}
	public void loadSettings() {
		settingsMap.clear();
		try {
			if(nProtect.m.getConfig().isSet("Config.UsarTitle")) add("usar_title", loadFromConfig("UsarTitle"));
			if(nProtect.m.getConfig().isSet("Config.AutoLogin")) add("auto_login", loadFromConfig("AutoLogin"));
			if(nProtect.m.getConfig().isSet("Config.TempoLogar")) add("tempo_logar", loadFromConfig("TempoLogar"));
			if(nProtect.m.getConfig().isSet("Config.SenhaDefault")) add("senha_default_sem_cargo", loadFromConfig("SenhaDefault"));
			addMissingSettings();
		} catch (Exception e) {
			ConsoleLogger.error("Falha ao carregar as configuracoes. Usando valores default.");
			addMissingSettings();
		}
	}
	private void addMissingSettings() {
		add("usar_title", "true");
		add("auto_login", "true");
		add("tempo_logar", "25");
		add("senha_default_sem_cargo", "nprotect_default");
	}
	private void add(String id, String valor) {
		if(!settingsMap.containsKey(id)) settingsMap.put(id, valor);
	}
	public String loadFromConfig(String path) {
		FileConfiguration config = nProtect.m.getConfig(); return config.getString("Config." + path);
	}
	public Boolean getCachedSetting(String selectValue) {
		return Boolean.valueOf(settingsMap.get(selectValue));
	}
	public String getCachedValue(String selectValue) {
		return settingsMap.get(selectValue);
	}
	
}

