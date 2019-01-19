package me.NickUltracraft.Protect.Cache;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import me.NickUltracraft.Protect.Main;
/**
 * A class Arrays.java do projeto (PLUGIN - nProtect Rebuilt) pertence ao NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * Rebuild, do not copy
*/

public class Arrays {
	
	public static List<String> jogadoresLogados = new ArrayList<>();
	public static List<String> comandosPermitidos = new ArrayList<>();
	
	public static Arrays getInstance() {
		return new Arrays();
	}
	public void loadComandos() {
		for(int i = 0; i < Main.m.getConfig().getStringList("Config.ComandosPermitidos").size(); i++) {
			if(!comandosPermitidos.contains(Main.m.getConfig().getStringList("Config.ComandosPermitidos").get(i).toLowerCase())) comandosPermitidos.add(Main.m.getConfig().getStringList("Config.ComandosPermitidos").get(i).toLowerCase());
		}
	}
	public void adicionarLogados(String username) {
		if(!jogadoresLogados.contains(username.toLowerCase())) jogadoresLogados.add(username.toLowerCase());
	}
	public void removerLogados(String username) {
		if(jogadoresLogados.contains(username.toLowerCase())) jogadoresLogados.remove(username.toLowerCase());
	}
	public boolean estaLogado(Player player) {
		if(!player.hasPermission("loginstaff.staffer")) {
			return true;
		}
		return jogadoresLogados.contains(player.getName().toLowerCase());
	}

}
