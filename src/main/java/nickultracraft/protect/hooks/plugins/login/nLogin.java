package nickultracraft.protect.hooks.plugins.login;

/**
 * Copyright 2019 NickUltracraft
 *
 * A class nLogin.java pertence ao projeto (PLUGIN - nProtectV2) pertencente à NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * É expressamente proibído alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link para acesso da source original.
 *
 * Este aviso não pode ser removido ou alterado de qualquer distribuição de origem.
*/

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import nickultracraft.login.events.LoginEvent;
import nickultracraft.login.events.RegisterEvent;
import nickultracraft.protect.hooks.LoginAbstract;

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
