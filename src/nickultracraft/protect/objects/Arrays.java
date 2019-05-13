package nickultracraft.protect.objects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import nickultracraft.protect.nProtect;

/**
 * A class Arrays.java da package (nickultracraft.protect.cache) pertence ao NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * É expressamente proibído alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link do download/source original.
*/

public class Arrays {
	
	public static List<String> jogadoresLogados = new ArrayList<>();
	public static List<String> comandosPermitidos = new ArrayList<>();
	
	public static Arrays getInstance() {
		return new Arrays();
	}
	public void loadComandos() {
		for(int i = 0; i < nProtect.m.getConfig().getStringList("Config.ComandosPermitidos").size(); i++) { if(!comandosPermitidos.contains(nProtect.m.getConfig().getStringList("Config.ComandosPermitidos").get(i).toLowerCase())) comandosPermitidos.add(nProtect.m.getConfig().getStringList("Config.ComandosPermitidos").get(i).toLowerCase()); }
	}
	public void adicionarLogados(String username) {
		if(!jogadoresLogados.contains(username.toLowerCase())) jogadoresLogados.add(username.toLowerCase());
	}
	public void removerLogados(String username) {
		if(jogadoresLogados.contains(username.toLowerCase())) jogadoresLogados.remove(username.toLowerCase());
	}
	public boolean estaLogado(Player player) {
		if(!player.hasPermission("loginstaff.staffer")) { return true; } return jogadoresLogados.contains(player.getName().toLowerCase());
	}

}
