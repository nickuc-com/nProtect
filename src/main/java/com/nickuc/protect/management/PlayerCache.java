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
	
	private static final Set<String> logados = new HashSet<>();

	public static void add(String username) {
		logados.add(username.toLowerCase());
	}

	public static void remove(String username) {
		logados.remove(username.toLowerCase());
	}

	public static boolean isAuthenticated(Player player) {
		return logados.contains(player.getName().toLowerCase());
	}

}
