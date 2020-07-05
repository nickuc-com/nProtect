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

package com.nickuc.protect.model;

import com.nickuc.ncore.api.config.*;
import com.nickuc.ncore.api.logger.*;
import com.nickuc.ncore.api.plugin.shared.sender.*;
import com.nickuc.ncore.api.settings.*;
import com.nickuc.ncore.plugin.bukkit.reflection.packets.*;
import com.nickuc.protect.events.*;
import com.nickuc.protect.management.*;
import com.nickuc.protect.*;
import lombok.*;
import org.bukkit.entity.*;

import java.io.*;

@Getter @ToString
public final class Account {

	private final nProtect nprotect;
	private final SharedPlayer player;
	private Group grupo;
	private String address = "127.0.0.1";
	private boolean staffer = false;

	public Account(nProtect nprotect, SharedPlayer player) {
		this.nprotect = nprotect;
		this.player = player;
		reload();
	}

	public void reload() {
		try {
			if (nProtect.permission != null) {
				nProtect.getGrupos().stream().filter(grupo -> nProtect.permission.playerInGroup(player.getSender(), grupo.getName())).findFirst().ifPresent(group -> this.grupo = group);
			} else {
				if (player != null && player.isOnline() && player.hasPermission("loginstaff.staffer")) this.grupo = Group.wrap("PERMISSION_GROUP", Settings.getString(SettingsEnum.SENHA_DEFAULT_SEM_CARGO));
			}
			this.staffer = (grupo != null);

			File playerData = new File(nprotect.getDataFolder(), "playerdata");
			if (player != null && playerData.exists()) {
				File pfile = new File(playerData, player.getName().toLowerCase() + ".yml");
				nConfig pconfig = new nConfig(pfile);
				this.address = pconfig.getString("address", address);
			}
		} catch (Exception e) {
			ConsoleLogger.err("Não foi possível carregar o usuário " + (player != null ? player.getName() : ""), e);
		}
	}

	public void saveAddress(String address) {
		if (player == null) return;

		File playerData = new File(nprotect.getDataFolder(), "playerdata");
		File pfile = new File(playerData, player.getName().toLowerCase() + ".yml");
		nConfig pconfig = new nConfig(pfile);
		pconfig.set("address", address);
		pconfig.saveConfig();
	}

	public void forceLogin(SharedPlayer sharedPlayer) {
		forceLogin(sharedPlayer, false);
	}

	public void forceLogin(SharedPlayer sharedPlayer, boolean session) {
		Player player = sharedPlayer.getSender();
		if (new PlayerLoginStaffEvent(player, grupo.getPassword()).callEvt()) {
			sharedPlayer.temp().define("logado", player.getAddress().getAddress().getHostAddress());
			player.sendMessage(Messages.getMessage(MessagesEnum.LOGOU_CHAT));
			if (session) {
				if (Settings.getBoolean(SettingsEnum.USAR_TITLE)) {
					TitleAPI.sendTitle(player, 0, 8, 5, Messages.getMessage(MessagesEnum.LOGINSTAFF_TITLE), Messages.getMessage(MessagesEnum.LOGOU_SUBTITLE_SESSION));
				}
			} else {
				if (Settings.getBoolean(SettingsEnum.USAR_TITLE)) {
					TitleAPI.sendTitle(player, 0, 8, 5, Messages.getMessage(MessagesEnum.LOGINSTAFF_TITLE), Messages.getMessage(MessagesEnum.LOGOU_SUBTITLE));
				}
			}
			if (Settings.getBoolean(SettingsEnum.AUTO_LOGIN) && !player.getAddress().getAddress().getHostAddress().equals(address)) {
				saveAddress(player.getAddress().getAddress().getHostAddress());
			}
			player.setWalkSpeed(0.2F);
			player.setFlySpeed(0.1F);
		}
	}
}
