package nickultracraft.protect.objects;

/**
 * Copyright 2019 NickUltracraft
 *
 * A class Arrays.java pertence ao projeto (PLUGIN - nProtectV2) pertencente à NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * É expressamente proibído alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link para acesso da source original.
 *
 * Este aviso não pode ser removido ou alterado de qualquer distribuição de origem.
*/

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import nickultracraft.protect.nProtect;

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
		return jogadoresLogados.contains(player.getName().toLowerCase());
	}

}
