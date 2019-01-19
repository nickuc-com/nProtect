package me.NickUltracraft.Protect.Hooks;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import API.LoginEvent;

/**
 * A class nLogin.java do projeto (PLUGIN - nProtect REBUILT) pertence ao NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * Rebuild, do not copy
*/

public class AuthMe implements Listener {
	
	@EventHandler
	public void onLogar(LoginEvent e) {
		Bukkit.getPluginManager().callEvent(new LoginCaller(e.getPlayer()));
	}
}
