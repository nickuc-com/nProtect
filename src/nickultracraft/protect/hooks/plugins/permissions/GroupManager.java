package nickultracraft.protect.hooks.plugins.permissions;

import org.anjocaido.groupmanager.data.User;
import org.anjocaido.groupmanager.dataholder.WorldDataHolder;
import org.bukkit.entity.Player;

import nickultracraft.protect.hooks.PermissionAbstract;

/**
 * A class GroupManager.java da package (nickultracraft.protect.hooks.plugins.permissions) pertence ao NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * É expressamente proibído alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link do download/source original.
*/

public class GroupManager extends PermissionAbstract {

	public GroupManager() {
		super("GroupManager");
	}
	@Override
	public boolean inGroup(Player player, String group) {
		return new User(new WorldDataHolder(player.getWorld().getName()), player.getName()).getGroupName().equalsIgnoreCase(group);
	}
}
