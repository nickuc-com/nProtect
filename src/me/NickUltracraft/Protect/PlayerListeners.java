package me.NickUltracraft.Protect;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.NickUltracraft.Protect.Cache.Arrays;
import me.NickUltracraft.Protect.Cache.Conta;
import me.NickUltracraft.Protect.Cache.Messages;

/**
 * A class Listener.java do projeto (PLUGIN - nProtect Rebuilt) pertence ao NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * Rebuild, do not copy
*/

public class PlayerListeners implements Listener {
	
	private boolean commandMatches(String commandToCheck) {
		for(String stringCompare : Arrays.comandosPermitidos) {
			if(stringCompare.toLowerCase().equals(commandToCheck)) return true;
		}
		return false;
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(p.hasPermission("loginstaffer.staff")) {
			Conta account = new Conta(p.getName());
			if(account.getSenha() == null || (!account.isStaffer())) {
				account.setIP(p.getAddress().getHostString());
				account.setSenha("nprotectloginstaff");
				account.setStaffer(true);
				account.submitChanges();
			}
			new BukkitRunnable() {
				@Override
				public void run() {
					if(!Arrays.getInstance().estaLogado(p.getName())) p.kickPlayer(Messages.getInstance().getCachedMessage("demorou_logar"));
				}
			}.runTaskLater(Main.m, 20*25);
		} else {
			Conta account = new Conta(p.getName());
			if(account.getSenha() != null) account.delete();
		}
	}
	@EventHandler
	public void onMexer(PlayerMoveEvent e) {
		if(!Arrays.getInstance().estaLogado(e.getPlayer().getName())); e.getPlayer().teleport(e.getFrom());
	}
	@EventHandler
	public void onSair(PlayerQuitEvent e) {
		Arrays.getInstance().removerLogados(e.getPlayer().getName());
	}
	@EventHandler
	public void onFalar(AsyncPlayerChatEvent e) {
		if(!Arrays.getInstance().estaLogado(e.getPlayer().getName())); e.setCancelled(true);
	}
	@EventHandler
	public void onComando(PlayerCommandPreprocessEvent e) {
		if(!Arrays.getInstance().estaLogado(e.getPlayer().getName()) && (!commandMatches(e.getMessage().toLowerCase().split(" ")[0]))) e.setCancelled(true);
	}
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if(!Arrays.getInstance().estaLogado(e.getPlayer().getName())); e.setCancelled(true);
	}
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if(!Arrays.getInstance().estaLogado(e.getPlayer().getName())); e.setCancelled(true);
	}
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if(!Arrays.getInstance().estaLogado(e.getPlayer().getName())); e.setCancelled(true);
	}
}
