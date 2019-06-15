package nickultracraft.protect.hooks;

/**
 * Copyright 2019 NickUltracraft
 *
 * A class LoginAbstract.java pertence ao projeto (PLUGIN - nProtectV2) pertencente à NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * É expressamente proibído alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link para acesso da source original.
 *
 * Este aviso não pode ser removido ou alterado de qualquer distribuição de origem.
*/

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class LoginAbstract {

	public String pluginName;
	public String pluginVersion;
	
	public LoginAbstract(String pluginName, String pluginVersion) {
		this.pluginName = pluginName;
		this.pluginVersion = pluginVersion;
	}
	public String getPluginName() {
		return pluginName;
	}
	public String getPluginVersion() {
		return pluginVersion;
	}
	public void callLogin(Player player) {
		Bukkit.getPluginManager().callEvent(new LoginCaller(player));
	}
}
