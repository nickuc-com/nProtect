package nickultracraft.protect.hooks.plugins.permissions;

import nickultracraft.protect.hooks.PermissionAbstract;

/**
 * A class PermissionsEx.java da package (nickultracraft.protect.hooks.plugins.permissions) pertence ao NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * É expressamente proibído alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link do download/source original.
*/

public class PermissionsEx extends PermissionAbstract {

	public PermissionsEx() {
		super("PermissionsEx");
	}
	@Override
	public boolean inGroup(String player, String group) {
		return ru.tehkode.permissions.bukkit.PermissionsEx.getPermissionManager().getUser(player).inGroup(group);
	}
}
