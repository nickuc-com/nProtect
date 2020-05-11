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
import com.nickuc.protect.hook.*;
import com.nickuc.protect.*;
import org.bukkit.event.*;
import rush.login.events.*;

public class MambaLogin extends Listener<nProtect> implements LoginProvider {

	@EventHandler
	public void onLogar(PlayerAuthLoginEvent e) {
		new LoginCompleteEvent(e.getPlayer()).callEvt();
	}

	@EventHandler
	public void onRegistrar(PlayerAuthRegisterEvent e) {
		new LoginCompleteEvent(e.getPlayer()).callEvt();
	}

	@Override
	public LoginPlugin getLoginPlugin() {
		return LoginPlugin.MAMBALOGIN;
	}
}
