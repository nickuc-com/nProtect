/**
 * Copyright NickUC
 * -
 * Esta class pertence ao projeto de NickUC
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.com
 * -
 * É expressamente proibido alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link para acesso da source original.
 * -
 * Este aviso não pode ser removido ou alterado de qualquer distribuição de origem.
 */

package nickultracraft.protect.objects;

import nickultracraft.ncore.minecraft.spigot.logging.ConsoleLogger;
import nickultracraft.protect.nProtect;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public class Settings {

	private static HashMap<String, String> settingsMap = new HashMap<>();

	public static void loadSettings() {
		settingsMap.clear();
		try {
			if(nProtect.instance.getConfig().isSet("Config.UsarTitle")) add("usar_title", loadFromConfig("UsarTitle"));
			if(nProtect.instance.getConfig().isSet("Config.AutoLogin")) add("auto_login", loadFromConfig("AutoLogin"));
			if(nProtect.instance.getConfig().isSet("Config.TempoLogar")) add("tempo_logar", loadFromConfig("TempoLogar"));
			if(nProtect.instance.getConfig().isSet("Config.SenhaDefault")) add("senha_default_sem_cargo", loadFromConfig("SenhaDefault"));
			addMissingSettings();
			
			for(String setting : settingsMap.keySet()) {
				ConsoleLogger.debug("Loaded '" + setting + "' -> '" + settingsMap.get(setting) + "'");
			}
			
		} catch (Exception e) {
			ConsoleLogger.warning("Falha ao carregar as configuracoes. Usando valores default.");
			addMissingSettings();
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
		FileConfiguration config = nProtect.instance.getConfig(); return config.getString("Config." + path);
	}

	public static Boolean getCachedSetting(String selectValue) {
		return Boolean.valueOf(settingsMap.get(selectValue));
	}

	public static String getCachedValue(String selectValue) {
		return settingsMap.get(selectValue);
	}
	
}

