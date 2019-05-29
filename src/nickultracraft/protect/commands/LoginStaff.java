package nickultracraft.protect.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nickultracraft.protect.api.ConsoleLogger;
import nickultracraft.protect.events.PlayerWrongLoginStaffEvent;
import nickultracraft.protect.objects.Arrays;
import nickultracraft.protect.objects.Conta;
import nickultracraft.protect.objects.Messages;

/**
 * A class LoginStaff.java da package (nickultracraft.protect.commands) pertence ao NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * É expressamente proibído alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link do download/source original.
*/

public final class LoginStaff implements CommandExecutor {

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
			if(!account.isStaffer()) {
				p.sendMessage(Messages.getInstance().getCachedMessage("nao_staffer"));
				return true;
			}
			if(Arrays.getInstance().estaLogado(p)) {
				p.sendMessage(Messages.getInstance().getCachedMessage("ja_autenticado"));
				return true;
			}
			String password = args[0];
			if(!account.getSenha().equals(password)) {
				Bukkit.getPluginManager().callEvent(new PlayerWrongLoginStaffEvent(p.getName(), password));
				ConsoleLogger.invasion("O ip " + p.getAddress().getHostString() + " tentou entrar na conta de " + p.getName() + " e errou o login staff.");
				p.kickPlayer(Messages.getInstance().getCachedMessage("senha_incorreta"));
				return true;
			}
			account.forceLogin(p);
			return true;
		}
		sender.sendMessage("§cComando indisponível para console.");
		return false;
	}

}
