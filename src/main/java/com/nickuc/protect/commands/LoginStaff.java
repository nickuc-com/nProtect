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

import com.nickuc.ncore.api.logger.*;
import com.nickuc.ncore.api.plugin.shared.command.*;
import com.nickuc.ncore.api.plugin.shared.sender.*;
import com.nickuc.ncore.api.settings.*;
import com.nickuc.protect.events.*;
import com.nickuc.protect.management.*;
import com.nickuc.protect.model.*;
import com.nickuc.protect.*;

public final class LoginStaff extends SharedCommand<nProtect> {

	public LoginStaff() {
		super("loginstaff");
		setDescription("Comando para se autenticar como staffer");
	}

	@Override
	public void execute(SharedSender sender, String lb, String[] args) throws Exception {
		if (!(sender instanceof SharedPlayer)) {
			sender.sendMessage("§cDesculpe, mas este comando está indisponível para o console.");
			return;
		}

		SharedPlayer sharedPlayer = (SharedPlayer) sender;
		if (args.length != 1) {
			sharedPlayer.sendMessage(Messages.getMessage(MessagesEnum.INVALID_ARGS));
			return;
		}
		
		Account account = new Account(plugin, sharedPlayer);
		if (!account.isStaffer()) {
			sharedPlayer.sendMessage(Messages.getMessage(MessagesEnum.NAO_STAFFER));
			return;
		}

		if (sharedPlayer.temp().exists("logado")) {
			sharedPlayer.sendMessage(Messages.getMessage(MessagesEnum.JA_AUTENTICADO));
			return;
		}

		String password = args[0];
		if (!account.getGrupo().getPassword().equals(password)) {
			if (new PlayerWrongLoginStaffEvent(sharedPlayer.getName(), password).callEvt()) {
				ConsoleLogger.warning("O jogador " + sharedPlayer.getName() + " " + sharedPlayer.getAddress() + " inseriu uma senha incorreta para o loginstaff.");
				sharedPlayer.kickPlayer(Messages.getMessage(MessagesEnum.INCORRECT_PASS));
			}
			return;
		}
		ConsoleLogger.debug("Login efetuado para " + sharedPlayer.getName() + " para " + account.toString() + ".");
		account.forceLogin(sharedPlayer);
	}

}
