package nickultracraft.protect.hooks.plugins.login;

/**
 * Copyright 2019 NickUltracraft
 *
 * A class MambaLogin.java pertence ao projeto (PLUGIN - nProtectV2) pertencente à NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * É expressamente proibído alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link para acesso da source original.
 *
 * Este aviso não pode ser removido ou alterado de qualquer distribuição de origem.
*/

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import nickultracraft.protect.hooks.LoginAbstract;
import rush.login.events.PlayerAuthLoginEvent;
import rush.login.events.PlayerAuthRegisterEvent;

public class MambaLogin extends LoginAbstract implements Listener {
	
	public MambaLogin() {
		super("MambaLogin", "Unknow");
	}
	@EventHandler
	public void onLogar(PlayerAuthLoginEvent e) {
		callLogin(e.getPlayer());
	}
	@EventHandler
	public void onRegistrar(PlayerAuthRegisterEvent e) {
		callLogin(e.getPlayer());
	}
}
