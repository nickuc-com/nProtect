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

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public final class PlayerCache {
	
	private static Set<String> jogadoresLogados = new HashSet<>();

	public static void setAuthenticated(String username) {
		jogadoresLogados.add(username.toLowerCase());
	}

	public static void removeAuthenticated(String username) {
		jogadoresLogados.remove(username.toLowerCase());
	}

	public static boolean isAuthenticated(Player player) {
		return jogadoresLogados.contains(player.getName().toLowerCase());
	}

}
