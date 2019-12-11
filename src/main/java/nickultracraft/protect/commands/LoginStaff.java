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


package nickultracraft.protect.commands;

import nickultracraft.ncore.minecraft.spigot.logging.ConsoleLogger;
import nickultracraft.protect.events.PlayerWrongLoginStaffEvent;
import nickultracraft.protect.objects.Account;
import nickultracraft.protect.objects.Arrays;
import nickultracraft.protect.objects.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public final class LoginStaff implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player)sender;
			if(args.length != 1) {
				p.sendMessage(Messages.getCachedMessage("argumentos_invalidos"));
				return true;
			} 
			Account account = new Account(p);
			if(!account.isStaffer()) {
				p.sendMessage(Messages.getCachedMessage("nao_staffer"));
				return true;
			}
			if(Arrays.estaLogado(p)) {
				p.sendMessage(Messages.getCachedMessage("ja_autenticado"));
				return true;
			}
			String password = args[0];
			if(!account.getPassword().equals(password)) {
				PlayerWrongLoginStaffEvent wrongEvent = new PlayerWrongLoginStaffEvent(p.getName(), password).call();
				ConsoleLogger.warning("O jogador " + p.getName() + " " + p.getAddress().getHostString() + " inseriu uma senha incorreta para o loginstaff.");
				if(wrongEvent.isCancelled()) {
					return true;
				}
				p.kickPlayer(Messages.getCachedMessage("senha_incorreta"));
				return true;
			}
			ConsoleLogger.debug("Login efetuado para " + p.getName() + " para " + account.toString() + ".");
			account.getContaOperations().forceLogin(p);
			return true;
		}
		sender.sendMessage("§cDesculpe, mas este comando está indisponível para o console.");
		return false;
	}

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
