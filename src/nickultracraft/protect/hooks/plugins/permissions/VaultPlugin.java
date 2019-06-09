package nickultracraft.protect.hooks.plugins.permissions;

import org.bukkit.entity.Player;

import nickultracraft.protect.nProtect;
import nickultracraft.protect.hooks.PermissionAbstract;

/**
 * A class VaultPlugin.java da package (nickultracraft.protect.hooks.plugins.permissions) pertence ao NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * É expressamente proibído alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link do download/source original.
*/

public class VaultPlugin extends PermissionAbstract {

	public VaultPlugin() {
		super(nProtect.permissionPluginVault.getName());
	}
	@Override
	public boolean inGroup(Player player, String group) {
		return nProtect.permissionPluginVault.playerInGroup(player, group);
	}
}
