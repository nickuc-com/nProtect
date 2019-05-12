package nickultracraft.protect.objetos;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import nickultracraft.protect.nProtect;
import nickultracraft.protect.api.Console;
import nickultracraft.protect.api.Console.ConsoleLevel;

/**
 * A class Messages.java da package (nickultracraft.protect.cache) pertence ao NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * É expressamente proibído alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link do download/source original.
*/

public class Messages {
	
	public static HashMap<String, String> messagesMap = new HashMap<>(); 
 	
	public static Messages getInstance() {
		return new Messages();
	}
	public void loadMessages() {
		messagesMap.clear();
		try {
			for(String messageToAdd : nProtect.m.getConfig().getConfigurationSection("Mensagens").getKeys(false)) {
				if(nProtect.m.getConfig().isSet("Mensagens." + messageToAdd)) add(messageToAdd, loadFromConfig(messageToAdd));
			}
			addMissingMessages();
		} catch (Exception e) {
			new Console("Falha ao carregar as mensagens. Usando valores default.", ConsoleLevel.ERRO).sendMessage();
			addMissingMessages();
		}
	}
	private void addMissingMessages() {
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
	}
	private void add(String id, String valor) {
		if(!messagesMap.containsKey(id)) messagesMap.put(id, valor);
	}
	public String loadFromConfig(String path) {
		FileConfiguration config = nProtect.m.getConfig(); return config.getString("Mensagens." + path);
	}
	public String getCachedMessage(String selectValue) {
		return ChatColor.translateAlternateColorCodes('&', messagesMap.get(selectValue));
	}

}
