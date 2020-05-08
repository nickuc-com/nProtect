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

import com.nickuc.ncore.api.plugin.bukkit.events.Listener;
import com.nickuc.protect.hook.LoginCompleteEvent;
import com.nickuc.protect.hook.LoginPlugin;
import com.nickuc.protect.hook.LoginProvider;
import com.nickuc.protect.nProtect;
import org.bukkit.event.EventHandler;

public class AuthMe extends Listener<nProtect> implements LoginProvider {

	@EventHandler
	public void onLogin(fr.xephi.authme.events.LoginEvent e) {
		new LoginCompleteEvent(e.getPlayer()).callEvt();
	}

	@Override
	public LoginPlugin getLoginPlugin() {
		return LoginPlugin.AUTHME;
	}
}
