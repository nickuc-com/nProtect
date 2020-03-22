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

package com.nickuc.protect.objects;

import com.nickuc.ncore.api.config.nConfig;
import com.nickuc.ncore.api.logger.ConsoleLogger;
import com.nickuc.ncore.api.plugin.spigot.reflection.packets.TitleAPI;
import com.nickuc.ncore.api.settings.Messages;
import com.nickuc.ncore.api.settings.Settings;
import com.nickuc.protect.events.PlayerLoginStaffEvent;
import com.nickuc.protect.management.MessagesEnum;
import com.nickuc.protect.management.PlayerCache;
import com.nickuc.protect.management.SettingsEnum;
import com.nickuc.protect.nProtect;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.entity.Player;

import java.io.File;

@Getter @ToString
public final class Account {

	private nProtect nprotect;
	private Player player;
	private Group grupo;
	private String address = "127.0.0.1";
	private boolean staffer = false;

	public Account(nProtect nprotect, Player player) {
		this.nprotect = nprotect;
		this.player = player;
		reload();
	}

	public void reload() {
		try {
			if (nProtect.permission != null) {
				nProtect.getGrupos().stream().filter(grupo -> nProtect.permission.playerInGroup(player, grupo.getName())).findFirst().ifPresent(group -> this.grupo = group);
			} else {
				if (player != null && player.isOnline() && player.hasPermission("loginstaff.staffer")) this.grupo = new Group("PERMISSION_GROUP", Settings.getString(SettingsEnum.SENHA_DEFAULT_SEM_CARGO));
			}
			this.staffer = (grupo != null);
		} catch (Exception e) {
			ConsoleLogger.err("Não foi possível carregar o usuário " + (player != null ? player.getName() : ""), e);
		}
	}

	public void saveAddress(String address) {
		if (player == null) return;

		File file = new File(nprotect.getDataFolder(), "playerdata");
		file.mkdirs();

		File pfile = new File(nprotect.getDataFolder() + "/playerdata", player.getName().toLowerCase() + ".yml");
		nConfig pconfig = new nConfig(pfile);

		if (!pconfig.existsConfig()) {
			pconfig.saveConfig();
		}
		pconfig.set("address", address);
		pconfig.saveConfig();
	}

	public void forceLogin(Player p) {
		forceLogin(p, false);
	}

	public void forceLogin(Player p, boolean session) {
		PlayerLoginStaffEvent loginEvent = new PlayerLoginStaffEvent(p, grupo.getPassword(), false);
		loginEvent.callEvent(nprotect);

		if (!loginEvent.isCancelled()) {
			PlayerCache.add(p.getName());
			if (session) {
				p.sendMessage(Messages.getMessage(MessagesEnum.LOGOU_CHAT));
				if (Settings.getBoolean(SettingsEnum.USAR_TITLE)) {
					TitleAPI.sendTitle(p, 0, 8, 5, Messages.getMessage(MessagesEnum.LOGINSTAFF_TITLE), Messages.getMessage(MessagesEnum.LOGOU_SUBTITLE_SESSION));
				}
			} else {
				if (Settings.getBoolean(SettingsEnum.USAR_TITLE)) {
					TitleAPI.sendTitle(p, 0, 8, 5, Messages.getMessage(MessagesEnum.LOGINSTAFF_TITLE), Messages.getMessage(MessagesEnum.LOGOU_SUBTITLE));
				}
			}
			if (Settings.getBoolean(SettingsEnum.AUTO_LOGIN) && !p.getAddress().getHostString().equals(address)) {
				saveAddress(p.getAddress().getHostString());
			}
			p.setWalkSpeed(0.2F);
			p.setFlySpeed(0.2F);
		}
	}
}
