package me.NickUltracraft.Protect.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLoginStaffEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
	private Player player;
	private String name;
	private String password;
	
	public PlayerLoginStaffEvent(Player player, String password) {
		this.player = player;
		this.name = player.getName();
		this.password = password;
	}
	public Player getPlayer() {
		return player;
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
