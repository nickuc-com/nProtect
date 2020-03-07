/**
 * Copyright NickUC
 * -
 * Esta class pertence ao projeto de NickUC
 * Mais informações: https://nickuc.com
 * -
 * É expressamente proibido alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link para acesso da source original.
 * -
 * Este aviso não pode ser removido ou alterado de qualquer distribuição de origem.
 */

package com.nickuc.protect.listener;

import com.nickuc.ncore.api.minecraft.spigot.reflection.packets.TitleAPI;
import com.nickuc.protect.hook.plugins.login.LoginCaller;
import com.nickuc.protect.hook.plugins.login.LoginEnum;
import org.bukkit.Bukkit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.nickuc.protect.nProtect;
import com.nickuc.protect.management.PlayerCache;
import com.nickuc.protect.objects.Account;
import com.nickuc.protect.management.Messages;
import com.nickuc.protect.management.Settings;

import java.util.concurrent.TimeUnit;

public final class PlayerListeners implements Listener {
	
	@EventHandler
	public void onLoginCaller(LoginCaller e) {
		Player p = e.getPlayer();
		Account account = new Account(p);
		if(account.isStaffer()) {
			if(Settings.getCachedSetting("auto_login") && (p.getAddress().getHostString().equals(account.getAddress()))) {
				account.forceLogin(p, true);
				return;
			}

			nProtect.nprotect.runTaskLater(false, () -> {
				if(!PlayerCache.isAuthenticated(p)) p.kickPlayer(Messages.getCachedMessage("demorou_logar"));
			}, Integer.parseInt(Settings.getCachedValue("tempo_logar")), TimeUnit.SECONDS);

			p.setWalkSpeed(0);
			p.setFlySpeed(0);
			p.sendMessage(account.getGrupo().getName().equalsIgnoreCase("PERMISSION_GROUP") ? Messages.getCachedMessage("logar_chat2") : Messages.getCachedMessage("logar_chat").replace("%grupo%", account.getGrupo().getName()));
			if(Settings.getCachedSetting("usar_title")) {
				TitleAPI.sendTitle(p, 0, 999, 999, Messages.getCachedMessage("loginstaff_title"), Messages.getCachedMessage("logar_subtitle"));
			}
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		Account account = new Account(p);
		if(account.isStaffer()) {
			if(nProtect.loginType == LoginEnum.UNKNOWN) {
				if(Settings.getCachedSetting("auto_login") && (p.getAddress().getHostString().equals(account.getAddress()))) {
					account.forceLogin(p, true);
					return;
				}
				Bukkit.getScheduler().runTaskLater(nProtect.nprotect, () -> {
					if(!PlayerCache.isAuthenticated(p)) p.kickPlayer(Messages.getCachedMessage("demorou_logar"));
				}, 20*Integer.parseInt(Settings.getCachedValue("tempo_logar")));
				p.setWalkSpeed(0);
				p.setFlySpeed(0);
				p.sendMessage(Messages.getCachedMessage("logar_chat").replace("%grupo%", account.getGrupo().getName()));
				if(Settings.getCachedSetting("usar_title")) {
					TitleAPI.sendTitle(p, 0, 999, 999, Messages.getCachedMessage("loginstaff_title"), Messages.getCachedMessage("logar_subtitle"));
				}
			}
		} else {
			PlayerCache.setAuthenticated(p.getName());
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
		if(!PlayerCache.isAuthenticated(e.getPlayer()) && (!commandMatches(e.getMessage().toLowerCase().split(" ")[0]))) {
			e.setCancelled(true); 
			return;
		}
		String message = e.getMessage().toLowerCase();
		if(message.contains("nprotect") && (message.contains("plugman") || message.contains("system"))) {
			e.setCancelled(true);
			e.getPlayer().sendMessage("§cVocê não pode mexer em um plugin de segurança pelo jogo.");
			sendWarning(e.getPlayer(), nProtect.loginProvider.getProviderClass().getSimpleName());
		} else if(nProtect.loginType != LoginEnum.UNKNOWN && nProtect.loginType != LoginEnum.NLOGIN) {
			if(!nProtect.loginProvider.getProviderClass().getSimpleName().toLowerCase().equalsIgnoreCase("Vault") && message.contains(nProtect.loginProvider.getProviderClass().getSimpleName().toLowerCase()) && message.contains("plugman") || !nProtect.loginProvider.getProviderClass().getSimpleName().toLowerCase().equalsIgnoreCase("Vault") && message.contains(nProtect.loginProvider.getProviderClass().getSimpleName().toLowerCase()) && message.contains("system")) {
				e.setCancelled(true);
				e.getPlayer().sendMessage("§cVocê não pode mexer em um autenticação pelo jogo.");
				sendWarning(e.getPlayer(), nProtect.loginProvider.getProviderClass().getSimpleName());
			}
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if(!PlayerCache.isAuthenticated(e.getPlayer())) e.getPlayer().teleport(e.getFrom());
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		PlayerCache.removeAuthenticated(e.getPlayer().getName());
	}

	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
		if(!PlayerCache.isAuthenticated(e.getPlayer())) e.setCancelled(true);
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if(!PlayerCache.isAuthenticated(e.getPlayer())) e.setCancelled(true);
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent e) {
		if(!PlayerCache.isAuthenticated(e.getPlayer())) e.setCancelled(true);
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if(!PlayerCache.isAuthenticated(e.getPlayer())) e.setCancelled(true);
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if(!PlayerCache.isAuthenticated(e.getPlayer())) e.setCancelled(true);
	}

	private boolean commandMatches(String commandToCheck) {
		return Settings.allowedCommands.stream().anyMatch(str -> str.equalsIgnoreCase(commandToCheck));
	}

	private void sendWarning(Player comandSender, String plugin) {
		Bukkit.getOnlinePlayers().stream().filter(player -> player != comandSender && player.hasPermission("nprotect.admin")).forEach(player -> {
			player.sendMessage("");
			player.sendMessage("  §7O jogador " + comandSender.getName() + " tentou desativar o " + ((plugin).equalsIgnoreCase("nprotect") ? "nProtect" : "plugin " + plugin));
			player.sendMessage("  §7O nProtect evitou que esta tarefa fosse realizada.");
			player.sendMessage("");
		});
	}
}
