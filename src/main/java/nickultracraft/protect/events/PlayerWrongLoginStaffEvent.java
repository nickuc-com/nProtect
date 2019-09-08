package nickultracraft.protect.events;

/**
 * Copyright 2019 NickUltracraft
 *
 * A class PlayerWrongLoginStaffEvent.java pertence ao projeto (PLUGIN - nProtectV2) pertencente à NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * É expressamente proibído alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link para acesso da source original.
 *
 * Este aviso não pode ser removido ou alterado de qualquer distribuição de origem.
*/

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerWrongLoginStaffEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();

	private String name;
	private String password;
	
	public PlayerWrongLoginStaffEvent(String name, String password) {
		this.name = name;
		this.password = password;
	}
	public String getPlayerName() {
		return name;
	}
	public String getSenha() {
		return password;
	}
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
