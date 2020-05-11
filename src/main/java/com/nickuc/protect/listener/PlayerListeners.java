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

import com.nickuc.ncore.api.plugin.bukkit.events.Listener;
import com.nickuc.ncore.api.plugin.bukkit.reflection.packets.*;
import com.nickuc.ncore.api.settings.*;
import com.nickuc.protect.hook.*;
import com.nickuc.protect.management.*;
import com.nickuc.protect.*;
import com.nickuc.protect.model.*;
import lombok.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.player.*;

import java.util.concurrent.*;

@RequiredArgsConstructor
public final class PlayerListeners extends Listener<nProtect> {

	@EventHandler
	public void onLoginComplete(LoginCompleteEvent e) {
		startTask(e.getPlayer());
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (nProtect.getLoginProvider().getLoginPlugin() == LoginPlugin.UNKNOWN) {
			startTask(e.getPlayer());
		}
	}

	private void startTask(Player player) {
		Account account = new Account(plugin, player);
		if (!account.isStaffer()) {
			PlayerCache.add(player.getName());
			return;
		}

		if (Settings.getBoolean(SettingsEnum.AUTO_LOGIN) && player.getAddress().getHostString().equals(account.getAddress())) {
			account.forceLogin(player, true);
			return;
		}

		plugin.runTaskLater(false, () -> {
			if (player.isOnline() && !PlayerCache.isAuthenticated(player)) player.kickPlayer(Messages.getMessage(MessagesEnum.DEMOROU_LOGAR));
		}, Settings.getInt(SettingsEnum.TEMPO_LOGAR), TimeUnit.SECONDS);

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
		this.plugin.getServer().getOnlinePlayers().stream().filter(player -> player != comandSender && player.hasPermission("nprotect.admin")).forEach(player -> {
			player.sendMessage("");
			player.sendMessage("  §7O jogador " + comandSender.getName() + " tentou desativar o " + ((plugin).equalsIgnoreCase("nprotect") ? "nProtect" : "plugin " + plugin));
			player.sendMessage("  §7O nProtect evitou que esta tarefa fosse realizada.");
			player.sendMessage("");
		});
	}
}
