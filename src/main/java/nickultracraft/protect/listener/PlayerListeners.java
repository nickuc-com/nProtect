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

package nickultracraft.protect.listener;

import nickultracraft.ncore.minecraft.spigot.packets.TitleAPI;
import nickultracraft.protect.hook.plugins.login.LoginEnum;
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

import nickultracraft.protect.nProtect;
import nickultracraft.protect.hook.plugins.login.LoginCaller;
import nickultracraft.protect.objects.Arrays;
import nickultracraft.protect.objects.Account;
import nickultracraft.protect.objects.Messages;
import nickultracraft.protect.objects.Settings;

public class PlayerListeners implements Listener {
	
	@EventHandler
	public void onLogin(LoginCaller e) {
		Player p = e.getPlayer();
		Account account = new Account(p);
		if(account.isStaffer()) {
			if(Settings.getCachedSetting("auto_login") && (p.getAddress().getHostString().equals(account.getAddress()))) {
				account.getContaOperations().forceLogin(p, true);
				return;
			}
			Bukkit.getScheduler().runTaskLater(nProtect.instance, () -> {
				if(!Arrays.estaLogado(p)) p.kickPlayer(Messages.getCachedMessage("demorou_logar"));
			}, 20*Integer.parseInt(Settings.getCachedValue("tempo_logar")));
			p.setWalkSpeed(0);
			p.setFlySpeed(0);
			p.sendMessage(account.getGrupo().getGrupo().equalsIgnoreCase("PERMISSION_GROUP") ? Messages.getCachedMessage("logar_chat2") : Messages.getCachedMessage("logar_chat").replace("%grupo%", account.getGrupo().getGrupo()));
			if(Settings.getCachedSetting("usar_title")) {
				TitleAPI.sendTitle(p, 0, 999, 999, Messages.getCachedMessage("loginstaff_title"), Messages.getCachedMessage("logar_subtitle"));
			}
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		Account account = new Account(p);
		if(account.isStaffer()) {
			if(nProtect.loginType == LoginEnum.UNKNOWN) {
				if(Settings.getCachedSetting("auto_login") && (p.getAddress().getHostString().equals(account.getAddress()))) {
					account.getContaOperations().forceLogin(p, true);
					return;
				}
				Bukkit.getScheduler().runTaskLater(nProtect.instance, () -> {
					if(!Arrays.estaLogado(p)) p.kickPlayer(Messages.getCachedMessage("demorou_logar"));
				}, 20*Integer.parseInt(Settings.getCachedValue("tempo_logar")));
				p.setWalkSpeed(0);
				p.setFlySpeed(0);
				p.sendMessage(Messages.getCachedMessage("logar_chat").replace("%grupo%", account.getGrupo().getGrupo()));
				if(Settings.getCachedSetting("usar_title")) {
					TitleAPI.sendTitle(p, 0, 999, 999, Messages.getCachedMessage("loginstaff_title"), Messages.getCachedMessage("logar_subtitle"));
				}
			}
		} else {
			Arrays.adicionarLogados(p.getName());
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onComando(PlayerCommandPreprocessEvent e) {
		if(!Arrays.estaLogado(e.getPlayer()) && (!commandMatches(e.getMessage().toLowerCase().split(" ")[0]))) { 
			e.setCancelled(true); 
			return;
		}
		String message = e.getMessage().toLowerCase();
		if(message.contains("nprotect") && message.contains("plugman") || (message.contains("nprotect") && message.contains("system"))) { 
			e.setCancelled(true);
			e.getPlayer().sendMessage("§cVocê não pode mexer em um plugin de segurança pelo jogo.");
			sendWarning(e.getPlayer(), nProtect.loginProvider.getInjectorClass().getSimpleName());
		} else if(nProtect.loginType != LoginEnum.UNKNOWN && nProtect.loginType != LoginEnum.NLOGIN) {
			if(!nProtect.loginProvider.getInjectorClass().getSimpleName().toLowerCase().equalsIgnoreCase("Vault") && message.contains(nProtect.loginProvider.getInjectorClass().getSimpleName().toLowerCase()) && message.contains("plugman") || !nProtect.loginProvider.getInjectorClass().getSimpleName().toLowerCase().equalsIgnoreCase("Vault") && message.contains(nProtect.loginProvider.getInjectorClass().getSimpleName().toLowerCase()) && message.contains("system")) {
				e.setCancelled(true);
				e.getPlayer().sendMessage("§cVocê não pode mexer em um autenticação pelo jogo.");
				sendWarning(e.getPlayer(), nProtect.loginProvider.getInjectorClass().getSimpleName());
			}
		}
	}

	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent e) {
		if(!Arrays.estaLogado(e.getPlayer())) e.getPlayer().teleport(e.getFrom());
	}

	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent e) {
		Arrays.removerLogados(e.getPlayer().getName());
	}

	@EventHandler
	public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent e) {
		if(!Arrays.estaLogado(e.getPlayer())) e.setCancelled(true);
	}

	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent e) {
		if(!Arrays.estaLogado(e.getPlayer())) e.setCancelled(true);
	}

	@EventHandler
	public void onPlayerDropItemEvent(PlayerDropItemEvent e) {
		if(!Arrays.estaLogado(e.getPlayer())) e.setCancelled(true);
	}

	@EventHandler
	public void onBlockPlaceEvent(BlockPlaceEvent e) {
		if(!Arrays.estaLogado(e.getPlayer())) e.setCancelled(true);
	}

	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent e) {
		if(!Arrays.estaLogado(e.getPlayer())) e.setCancelled(true);
	}

	private boolean commandMatches(String commandToCheck) {
		for(String str : Arrays.allowedCommands) { if(str.toLowerCase().equals(commandToCheck)) return true; } return false;
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
