/**
 * Copyright NickUC
 * -
 * Esta class pertence ao projeto de NickUC
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.com
 * -
 * É expressamente proibido alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link para acesso da source original.
 * -
 * Este aviso não pode ser removido ou alterado de qualquer distribuição de origem.
 */

package com.nickuc.protect.hook.plugins;

import com.nickuc.protect.hook.LoginCompleteEvent;
import com.nickuc.protect.hook.LoginPlugin;
import com.nickuc.protect.hook.LoginProvider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import rush.login.events.PlayerAuthLoginEvent;
import rush.login.events.PlayerAuthRegisterEvent;

public class MambaLogin implements Listener, LoginProvider {

	@EventHandler
	public void onLogar(PlayerAuthLoginEvent e) {
		new LoginCompleteEvent(e.getPlayer()).call();
	}

	@EventHandler
	public void onRegistrar(PlayerAuthRegisterEvent e) {
		new LoginCompleteEvent(e.getPlayer()).call();
	}

	@Override
	public LoginPlugin getLoginPlugin() {
		return LoginPlugin.MAMBALOGIN;
	}
}
