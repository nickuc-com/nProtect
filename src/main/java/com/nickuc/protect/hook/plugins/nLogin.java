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
import nickultracraft.login.events.AuthenticateEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class nLogin implements Listener, LoginProvider {

	@EventHandler
	public void onLogar(AuthenticateEvent e) {
		new LoginCompleteEvent(e.getPlayer()).call();
	}

	@Override
	public LoginPlugin getLoginPlugin() {
		return LoginPlugin.NLOGIN;
	}
}
