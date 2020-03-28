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

import com.nickuc.ncore.api.plugin.spigot.reflection.packets.TitleAPI;
import com.nickuc.ncore.api.settings.Messages;
import com.nickuc.ncore.api.settings.Settings;
import com.nickuc.protect.hook.LoginCompleteEvent;
import com.nickuc.protect.hook.LoginPlugin;
import com.nickuc.protect.management.MessagesEnum;
import com.nickuc.protect.management.PlayerCache;
import com.nickuc.protect.management.SettingsEnum;
import com.nickuc.protect.nProtect;
import com.nickuc.protect.objects.Account;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.*;

import java.util.concurrent.TimeUnit;

public final class PlayerListeners implements Listener {

	private nProtect nprotect;

	public PlayerListeners(nProtect nprotect) {
		this.nprotect = nprotect;
	}

	@EventHandler
	public void onLoginComplete(LoginCompleteEvent e) {
		Player player = e.getPlayer();
		Account account = new Account(nprotect, player);
		if (!account.isStaffer()) {
			PlayerCache.add(player.getName());
			return;
		}

		startTask(player, account);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		Account account = new Account(nprotect, player);
		if (!account.isStaffer()) {
			PlayerCache.add(player.getName());
			return;
		}

		startTask(player, account);
	}

	private void startTask(Player player, Account account) {
		if (nProtect.getLoginProvider().getLoginPlugin() != LoginPlugin.UNKNOWN) {
			PlayerCache.add(player.getName());
			return;
		}

		if (Settings.getBoolean(SettingsEnum.AUTO_LOGIN) && player.getAddress().getHostString().equals(account.getAddress())) {
			account.forceLogin(player, true);
			return;
		}

		nprotect.runTaskLater(false, () -> {
			if (!PlayerCache.isAuthenticated(player)) player.kickPlayer(Messages.getMessage(MessagesEnum.DEMOROU_LOGAR));
		}, 1000*Settings.getInt(SettingsEnum.TEMPO_LOGAR), TimeUnit.MILLISECONDS);

		player.setWalkSpeed(0);
		player.setFlySpeed(0);

		player.sendMessage(account.getGrupo().getName().equalsIgnoreCase("PERMISSION_GROUP") ? Messages.getMessage(MessagesEnum.LOGAR_CHAT2) : Messages.getMessage(MessagesEnum.LOGAR_CHAT).replace("%grupo%", account.getGrupo().getName()));
		if (Settings.getBoolean(SettingsEnum.USAR_TITLE)) {
			TitleAPI.sendTitle(player, 0, 999, 999, Messages.getMessage(MessagesEnum.LOGINSTAFF_TITLE), Messages.getMessage(MessagesEnum.LOGAR_SUBTITLE));
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
		if (!PlayerCache.isAuthenticated(e.getPlayer()) && !commandMatches(e.getMessage().toLowerCase().split(" ")[0])) {
			e.setCancelled(true); 
			return;
		}
		String message = e.getMessage().toLowerCase();
		if (message.contains("nprotect") && (message.contains("plugman") || message.contains("system"))) {
			e.setCancelled(true);
			e.getPlayer().sendMessage("§cVocê não pode mexer em um plugin de segurança pelo jogo.");
			sendWarning(e.getPlayer(), nProtect.getLoginProvider().getLoginPlugin().getName());
		} else if (nProtect.getLoginProvider().getLoginPlugin() != LoginPlugin.UNKNOWN && nProtect.getLoginProvider().getLoginPlugin() != LoginPlugin.NLOGIN && message.contains(nProtect.getLoginProvider().getLoginPlugin().getName().toLowerCase()) && (message.contains("plugman") || message.contains("system"))) {
			e.setCancelled(true);
			e.getPlayer().sendMessage("§cVocê não pode mexer em um autenticação pelo jogo.");
			sendWarning(e.getPlayer(), nProtect.getLoginProvider().getLoginPlugin().getName());
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if (!PlayerCache.isAuthenticated(e.getPlayer())) e.getPlayer().teleport(e.getFrom());
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		PlayerCache.remove(e.getPlayer().getName());
	}

	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
		if (!PlayerCache.isAuthenticated(e.getPlayer())) e.setCancelled(true);
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (!PlayerCache.isAuthenticated(e.getPlayer())) e.setCancelled(true);
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent e) {
		if (!PlayerCache.isAuthenticated(e.getPlayer())) e.setCancelled(true);
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if (!PlayerCache.isAuthenticated(e.getPlayer())) e.setCancelled(true);
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if (!PlayerCache.isAuthenticated(e.getPlayer())) e.setCancelled(true);
	}

	private boolean commandMatches(String commandToCheck) {
		return Settings.getStringList(SettingsEnum.COMANDOS_PERMITIDOS).stream().anyMatch(str -> str.equalsIgnoreCase(commandToCheck));
	}

	private void sendWarning(Player comandSender, String plugin) {
		nprotect.getServer().getOnlinePlayers().stream().filter(player -> player != comandSender && player.hasPermission("nprotect.admin")).forEach(player -> {
			player.sendMessage("");
			player.sendMessage("  §7O jogador " + comandSender.getName() + " tentou desativar o " + ((plugin).equalsIgnoreCase("nprotect") ? "nProtect" : "plugin " + plugin));
			player.sendMessage("  §7O nProtect evitou que esta tarefa fosse realizada.");
			player.sendMessage("");
		});
	}
}
