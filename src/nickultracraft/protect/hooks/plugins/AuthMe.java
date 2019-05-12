package nickultracraft.protect.hooks.plugins;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import fr.xephi.authme.api.v3.AuthMeApi;
import fr.xephi.authme.events.LoginEvent;
import nickultracraft.protect.hooks.LoginAbstract;

/**
 * A class AuthMe.java da package (nickultracraft.protect.hooks.plugins) pertence ao NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * É expressamente proibído alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link do download/source original.
*/

public class AuthMe extends LoginAbstract implements Listener {
	
	public AuthMe() {
		super("AuthMe", AuthMeApi.getInstance().getPluginVersion());
	}
	@EventHandler
	public void onLogar(LoginEvent e) {
		callLogin(e.getPlayer());
	}
}
