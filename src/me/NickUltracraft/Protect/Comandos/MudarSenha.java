package me.NickUltracraft.Protect.Comandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.NickUltracraft.Protect.Cache.Conta;
import me.NickUltracraft.Protect.Cache.Messages;
import me.NickUltracraft.Protect.Cache.Settings;

/**
 * A class MudarSenha.java do projeto (PLUGIN - nProtect Rebuilt) pertence ao NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * Rebuild, do not copy
*/

public final class MudarSenha implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player)sender;
			int lenght = args.length;
			if(lenght != 1) {
				p.sendMessage(Messages.getInstance().getCachedMessage("argumentos_invalidos2"));
				return true;
			} 
			Conta account = new Conta(p.getName());
			if(!account.isStaffer()) {
				p.sendMessage(Messages.getInstance().getCachedMessage("nao_staffer"));
				return true;
			}
			account.setIP(p.getAddress().getHostString());
			account.setSenha(args[0]);
			account.submitChanges();
			p.sendMessage(Messages.getInstance().getCachedMessage("mudousenha_sucesso"));
			if(Settings.getInstance().getCachedSetting("usar_title")) {
				p.sendTitle(Messages.getInstance().getCachedMessage("loginstaff_title"), Messages.getInstance().getCachedMessage("mudousenha_subtitle"));
			}
		}
		return false;
	}

}
