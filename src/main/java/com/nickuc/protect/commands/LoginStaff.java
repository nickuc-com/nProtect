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

package com.nickuc.protect.commands;

import com.nickuc.ncore.api.logger.ConsoleLogger;
import com.nickuc.ncore.api.minecraft.spigot.command.AbstractCommand;
import com.nickuc.protect.events.PlayerWrongLoginStaffEvent;
import com.nickuc.protect.management.Messages;
import com.nickuc.protect.management.PlayerCache;
import com.nickuc.protect.nProtect;
import com.nickuc.protect.objects.Account;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class LoginStaff extends AbstractCommand<nProtect> {

	public LoginStaff(nProtect spigot) {
		super(spigot,"loginstaff");
		setDescription("Comando para se autenticar como staffer");
	}

	@Override
	public void execute(CommandSender sender, String lb, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§cDesculpe, mas este comando está indisponível para o console.");
			return;
		}
		Player p = (Player)sender;
		if(args.length != 1) {
			p.sendMessage(Messages.getCachedMessage("argumentos_invalidos"));
			return;
		}
		Account account = new Account(p);
		if(!account.isStaffer()) {
			p.sendMessage(Messages.getCachedMessage("nao_staffer"));
			return;
		}
		if(PlayerCache.isAuthenticated(p)) {
			p.sendMessage(Messages.getCachedMessage("ja_autenticado"));
			return;
		}
		String password = args[0];
		if(!account.getGrupo().getPassword().equals(password)) {
			PlayerWrongLoginStaffEvent event = new PlayerWrongLoginStaffEvent(p.getName(), password, false);
			event.callEvent(spigot);

			if(!event.isCancelled()) {
				ConsoleLogger.warning("O jogador " + p.getName() + " " + p.getAddress().getHostString() + " inseriu uma senha incorreta para o loginstaff.");
				p.kickPlayer(Messages.getCachedMessage("senha_incorreta"));
			}
			return;
		}
		ConsoleLogger.debug("Login efetuado para " + p.getName() + " para " + account.toString() + ".");
		account.forceLogin(p);
	}

}
