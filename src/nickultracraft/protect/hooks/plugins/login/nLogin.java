package nickultracraft.protect.hooks.plugins.login;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import nickultracraft.login.events.LoginEvent;
import nickultracraft.login.events.RegisterEvent;
import nickultracraft.protect.hooks.LoginAbstract;

/**
 * A class nLogin.java da package (nickultracraft.protect.hooks.plugins) pertence ao NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * É expressamente proibído alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link do download/source original.
*/

public class nLogin extends LoginAbstract implements Listener {
	
	public nLogin() {
		super("nLogin", Bukkit.getPluginManager().getPlugin("nLogin").getDescription().getVersion());
	}
	@EventHandler
	public void onLogar(LoginEvent e) {
		callLogin(e.getPlayer());
	}
	@EventHandler
	public void onRegister(RegisterEvent e) {
		callLogin(e.getPlayer());
	}

}
