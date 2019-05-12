package nickultracraft.protect.hooks;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * A class LoginCaller.java da package (nickultracraft.protect.hooks) pertence ao NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * É expressamente proibído alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link do download/source original.
*/

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
