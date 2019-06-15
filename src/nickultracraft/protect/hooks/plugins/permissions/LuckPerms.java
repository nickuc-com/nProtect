package nickultracraft.protect.hooks.plugins.permissions;

/**
 * Copyright 2019 NickUltracraft
 *
 * A class LuckPerms.java pertence ao projeto (PLUGIN - nProtectV2) pertencente à NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * É expressamente proibído alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link para acesso da source original.
 *
 * Este aviso não pode ser removido ou alterado de qualquer distribuição de origem.
*/

import org.bukkit.entity.Player;

import me.lucko.luckperms.api.User;
import nickultracraft.protect.api.ConsoleLogger;
import nickultracraft.protect.hooks.PermissionAbstract;

public class LuckPerms extends PermissionAbstract {

	public LuckPerms() {
		super("LuckPerms");
	}
	@Override
	public boolean inGroup(Player player, String group) {
		User user = me.lucko.luckperms.LuckPerms.getApi().getUser(player.getName());
		if(user == null) {
			ConsoleLogger.error("LuckPerms: tried to check group for offline user " + player + " but it isn't loaded!");
			return false;
		}
		return user.getPrimaryGroup().toLowerCase().equals(group.toLowerCase());
	}
	

}
