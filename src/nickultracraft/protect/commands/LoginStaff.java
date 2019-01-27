package nickultracraft.protect.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nickultracraft.protect.Console;
import nickultracraft.protect.Console.ConsoleLevel;
import nickultracraft.protect.api.PwManager;
import nickultracraft.protect.cache.Arrays;
import nickultracraft.protect.cache.Conta;
import nickultracraft.protect.cache.Messages;
import nickultracraft.protect.cache.Settings;
import nickultracraft.protect.events.PlayerLoginStaffEvent;
import nickultracraft.protect.events.PlayerWrongLoginStaffEvent;

/**
 * A class LoginStaff.java do projeto (PLUGIN - nProtect Rebuilt) pertence ao NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * Rebuild, do not copy
*/

public final class LoginStaff implements CommandExecutor {

	@SuppressWarnings("deprecation")
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
			String password = args[0];
			if(Arrays.getInstance().estaLogado(p)) {
				p.sendMessage(Messages.getInstance().getCachedMessage("ja_autenticado"));
				return true;
			}
			if(!new PwManager(password).comparatePassword(account.getSenha(), account.getSalt())) {
				Bukkit.getPluginManager().callEvent(new PlayerWrongLoginStaffEvent(p.getName(), password));
				new Console("O ip " + p.getAddress().getHostString() + " tentou entrar na conta de " + p.getName() + " e errou o login staff.", ConsoleLevel.INVASAO).sendMessage();
				p.kickPlayer(Messages.getInstance().getCachedMessage("senha_incorreta"));
				return true;
			}
			Arrays.getInstance().adicionarLogados(p.getName());
			p.sendMessage(Messages.getInstance().getCachedMessage("autenticou_sucesso"));
			if(Settings.getInstance().getCachedSetting("usar_title")) {
				p.sendTitle(Messages.getInstance().getCachedMessage("loginstaff_title"), Messages.getInstance().getCachedMessage("logou_subtitle"));
			}
			if(!account.getIP().equals(p.getAddress().getHostString())) {
				account.setIP(p.getAddress().getHostString());
				account.updateIP();
			}
			p.setWalkSpeed((float)0.2);
			p.setFlySpeed((float)0.2);
			Bukkit.getPluginManager().callEvent(new PlayerLoginStaffEvent(p, password));
		}
		return false;
	}

}
