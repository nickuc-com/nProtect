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

import nickultracraft.protect.nProtect;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Arrays {
	
	private static List<String> jogadoresLogados = new ArrayList<>();
	public static List<String> allowedCommands = new ArrayList<>();

	public static void loadComandos() {
		for(int i = 0; i < nProtect.instance.getConfig().getStringList("Config.ComandosPermitidos").size(); i++) { if(!allowedCommands.contains(nProtect.instance.getConfig().getStringList("Config.ComandosPermitidos").get(i).toLowerCase())) allowedCommands.add(nProtect.instance.getConfig().getStringList("Config.ComandosPermitidos").get(i).toLowerCase()); }
	}

	public static void adicionarLogados(String username) {
		if(!jogadoresLogados.contains(username.toLowerCase())) jogadoresLogados.add(username.toLowerCase());
	}

	public static void removerLogados(String username) {
		if(jogadoresLogados.contains(username.toLowerCase())) jogadoresLogados.remove(username.toLowerCase());
	}

	public static boolean estaLogado(Player player) {
		return jogadoresLogados.contains(player.getName().toLowerCase());
	}

}
