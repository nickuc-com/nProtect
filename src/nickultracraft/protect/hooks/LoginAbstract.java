package nickultracraft.protect.hooks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * A class LoginAbstract.java da package (nickultracraft.protect.hooks) pertence ao NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * É expressamente proibído alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link do download/source original.
*/

public abstract class LoginAbstract {

	public String pluginName;
	public String pluginVersion;
	
	public LoginAbstract(String pluginName, String pluginVersion) {
		this.pluginName = pluginName;
		this.pluginVersion = pluginVersion;
	}
	public String getPluginName() {
		return pluginName;
	}
	public String getPluginVersion() {
		return pluginVersion;
	}
	public void callLogin(Player player) {
		Bukkit.getPluginManager().callEvent(new LoginCaller(player));
	}
}
