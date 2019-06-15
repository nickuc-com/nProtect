package nickultracraft.protect.hooks;

/**
 * Copyright 2019 NickUltracraft
 *
 * A class PermissionAbstract.java pertence ao projeto (PLUGIN - nProtectV2) pertencente à NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * É expressamente proibído alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link para acesso da source original.
 *
 * Este aviso não pode ser removido ou alterado de qualquer distribuição de origem.
*/

import org.bukkit.entity.Player;

public abstract class PermissionAbstract {

	public String pluginName;
	
	public PermissionAbstract(String pluginName) {
		this.pluginName = pluginName;
	}
	public String getPluginName() {
		return pluginName;
	}
	public abstract boolean inGroup(Player player, String group);
}
