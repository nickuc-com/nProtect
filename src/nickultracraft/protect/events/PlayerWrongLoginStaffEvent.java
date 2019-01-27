package nickultracraft.protect.events;

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
