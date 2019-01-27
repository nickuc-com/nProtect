package nickultracraft.protect.hooks;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LoginCaller extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
	private Player player;
	private String name;
	
	public LoginCaller(Player player) {
		this.player = player;
		this.name = player.getName();
	}
	public Player getPlayer() {
		return player;
	}
	public String getPlayerName() {
		return name;
	}
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
