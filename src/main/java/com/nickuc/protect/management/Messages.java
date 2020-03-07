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
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public final class Messages {
	
	private static HashMap<String, String> messagesMap = new HashMap<>();

	public static void load() {
		messagesMap.clear();
		try {
			for(String messageToAdd : nProtect.nprotect.getConfig().getConfigurationSection("Mensagens").getKeys(false)) {
				if(nProtect.nprotect.getConfig().isSet("Mensagens." + messageToAdd)) add(messageToAdd, loadFromConfig(messageToAdd));
			}
			addMissingMessages();
			
			for(Map.Entry<String, String> entry : messagesMap.entrySet()) {
				ConsoleLogger.debug("Loaded '" + entry.getKey() + "' -> '" + entry.getValue() + "'");
			}
		} catch (Exception e) {
			ConsoleLogger.warning("Falha ao carregar as mensagens. Usando valores default.");
			addMissingMessages();
		}
	}

	private static void addMissingMessages() {
		add("argumentos_invalidos", "&cVocê deve usar: /loginstaff <sua senha>");
		add("nao_staffer", "&cVocê deve ser um staffer para executar este comando.");
		add("ja_autenticado", "&cVocê já está autenticado como staffer.");
		add("senha_incorreta", "&cA senha inserida está incorreta.");
		add("demorou_logar", "&cVocê demorou muito para se autenticar como staffer");
		add("mudousenha_sucesso", "&aVocê alterou a sua senha com sucesso.");
		add("loginstaff_title", "&e&lLOGIN STAFF");
		add("logou_subtitle", "&eVocê logou com sucesso!");
		add("logou_subtitle_session", "&eSessão continuada com sucesso");
		add("logou_chat", "&aVocê logou como staffer com sucesso.");
		add("logar_subtitle", "Se autentique usando /loginstaff <senha>");
		add("logar_chat", "&ePara se autenticar como staffer, utilize /loginstaff <sua senha>\n&e&lDICA: &eVocê deve inserir a senha do grupo %grupo%");
		add("logar_chat2", "&ePara se autenticar como staffer, utilize /loginstaff <sua senha>\n&e&lDICA: &eVocê deve inserir a senha para os grupos default.");
	}

	private static void add(String id, String valor) {
		if(!messagesMap.containsKey(id)) messagesMap.put(id, valor);
	}

	protected static String loadFromConfig(String path) {
		FileConfiguration config = nProtect.nprotect.getConfig(); return config.getString("Mensagens." + path);
	}

	public static String getCachedMessage(String selectValue) {
		if(!messagesMap.containsKey(selectValue)) return "§cUnknown message";
		return ChatColor.translateAlternateColorCodes('&', messagesMap.get(selectValue));
	}

}
