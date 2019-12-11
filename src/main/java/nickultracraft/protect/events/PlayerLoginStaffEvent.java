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

package nickultracraft.protect.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLoginStaffEvent extends Event implements Cancellable {
	
	private static final HandlerList handlers = new HandlerList();
	
	private Player player;
	private String password;
	private boolean cancelled;

	public PlayerLoginStaffEvent(Player player, String password) {
		this.player = player;
		this.password = password;
	}
	public Player getPlayer() {
		return player;
	}

	public String getPassword() {
		return password;
	}

	public PlayerLoginStaffEvent call() {
		Bukkit.getServer().getPluginManager().callEvent(this);
		return this;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean b) {
		cancelled = b;
	}
}
