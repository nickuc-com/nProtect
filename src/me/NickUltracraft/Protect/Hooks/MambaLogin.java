package me.NickUltracraft.Protect.Hooks;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import rush.login.events.PlayerAuthLoginEvent;
import rush.login.events.PlayerAuthRegisterEvent;

/**
 * A class nLogin.java do projeto (PLUGIN - nProtect REBUILT) pertence ao NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * Rebuild, do not copy
*/

public class MambaLogin implements Listener {
	
	@EventHandler
	public void onLogar(PlayerAuthLoginEvent e) {
		Bukkit.getPluginManager().callEvent(new LoginCaller(e.getPlayer()));
	}
	@EventHandler
	public void onRegistrar(PlayerAuthRegisterEvent e) {
		Bukkit.getPluginManager().callEvent(new LoginCaller(e.getPlayer()));
	}
}
