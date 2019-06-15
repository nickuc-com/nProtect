package nickultracraft.protect.hooks.plugins.permissions;

/**
 * Copyright 2019 NickUltracraft
 *
 * A class PermissionsEx.java pertence ao projeto (PLUGIN - nProtectV2) pertencente à NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * É expressamente proibído alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link para acesso da source original.
 *
 * Este aviso não pode ser removido ou alterado de qualquer distribuição de origem.
*/

import org.bukkit.entity.Player;

import nickultracraft.protect.hooks.PermissionAbstract;

public class PermissionsEx extends PermissionAbstract {

	public PermissionsEx() {
		super("PermissionsEx");
	}
	@Override
	public boolean inGroup(Player player, String group) {
		return ru.tehkode.permissions.bukkit.PermissionsEx.getPermissionManager().getUser(player).inGroup(group);
	}
}
