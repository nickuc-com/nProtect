package nickultracraft.protect.hooks.plugins.permissions;

/**
 * Copyright 2019 NickUltracraft
 *
 * A class GroupManager.java pertence ao projeto (PLUGIN - nProtectV2) pertencente à NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * É expressamente proibído alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link para acesso da source original.
 *
 * Este aviso não pode ser removido ou alterado de qualquer distribuição de origem.
*/

import org.anjocaido.groupmanager.data.User;
import org.anjocaido.groupmanager.dataholder.WorldDataHolder;
import org.bukkit.entity.Player;

import nickultracraft.protect.hooks.PermissionAbstract;

public class GroupManager extends PermissionAbstract {

	public GroupManager() {
		super("GroupManager");
	}
	@Override
	public boolean inGroup(Player player, String group) {
		return new User(new WorldDataHolder(player.getWorld().getName()), player.getName()).getGroupName().equalsIgnoreCase(group);
	}
}
