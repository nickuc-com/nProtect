package nickultracraft.protect.hooks;

import org.bukkit.entity.Player;

/**
 * A class PermissionAbstract.java da package (nickultracraft.protect.hooks) pertence ao NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * É expressamente proibído alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link do download/source original.
*/

public abstract class PermissionAbstract {

	public String pluginName;
	
	public PermissionAbstract(String pluginName) {
		this.pluginName = pluginName;
	}
	public String getPluginName() {
		return pluginName;
	}
	public abstract boolean inGroup(Player player, String group);
}
