package me.NickUltracraft.Protect.Cache;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import me.NickUltracraft.Protect.Main;

/**
 * A class Messages.java do projeto (PLUGIN - nProtect Rebuilt) pertence ao NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * Rebuild, do not copy
*/

public class Messages {
	
	public static HashMap<String, String> messagesMap = new HashMap<>(); 
 	
	public static Messages getInstance() {
		return new Messages();
	}
	public void loadMessages() {
		messagesMap.put("argumentos_invalidos", "&cVocê deve usar: /loginstaff <sua senha>");
		messagesMap.put("nao_staffer", "&cVocê deve ser um staffer para executar este comando.");
		messagesMap.put("ja_autenticado", "&cVocê já está autenticado como staffer.");
		messagesMap.put("senha_incorreta", "&cA senha inserida está incorreta.");
		messagesMap.put("autenticou_sucesso", "&aVocê se autenticou com sucesso como staffer.");
		messagesMap.put("demorou_logar", "&cVocê demorou muito para se autenticar como staffer");
		messagesMap.put("mudousenha_sucesso", "&aVocê alterou a sua senha com sucesso.");
	}
	public String loadFromConfig(String path) {
		FileConfiguration config = Main.m.getConfig(); return config.getString("Mensagens." + path);
	}
	public String getCachedMessage(String selectValue) {
		return ChatColor.translateAlternateColorCodes('&', messagesMap.get(selectValue));
	}

}
