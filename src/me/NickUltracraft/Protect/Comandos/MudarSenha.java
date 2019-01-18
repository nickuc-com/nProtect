package me.NickUltracraft.Protect.Comandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.NickUltracraft.Protect.Cache.Conta;
import me.NickUltracraft.Protect.Cache.Messages;

/**
 * A class MudarSenha.java do projeto (PLUGIN - nProtect Rebuilt) pertence ao NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * Rebuild, do not copy
*/

public final class MudarSenha implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player)sender;
			int lenght = args.length;
			if(lenght != 1) {
				p.sendMessage(Messages.getInstance().getCachedMessage("argumentos_invalidos"));
				return true;
			} 
			Conta account = new Conta(p.getName());
			if(account.getSenha() == null) {
				p.sendMessage(Messages.getInstance().getCachedMessage("nao_staffer"));
				return true;
			}
			account.setIP(p.getAddress().getHostString());
			account.setSenha(args[0]);
			account.setStaffer(true);
			account.submitChanges();
			p.sendMessage(Messages.getInstance().getCachedMessage("mudousenha_sucesso"));
		}
		return false;
	}

}
