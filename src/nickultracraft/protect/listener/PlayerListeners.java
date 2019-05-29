package nickultracraft.protect.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import nickultracraft.protect.nProtect;
import nickultracraft.protect.api.TitleAPI;
import nickultracraft.protect.hooks.LoginCaller;
import nickultracraft.protect.hooks.LoginPluginType;
import nickultracraft.protect.objects.Arrays;
import nickultracraft.protect.objects.Conta;
import nickultracraft.protect.objects.Messages;
import nickultracraft.protect.objects.Settings;

/**
 * A class PlayerListeners.java da package (nickultracraft.protect) pertence ao NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * É expressamente proibído alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link do download/source original.
*/

public class PlayerListeners implements Listener {
	
	@EventHandler
	public void onLogin(LoginCaller e) {
		Player p = e.getPlayer();
		Conta account = new Conta(p.getName());
		if(account.isStaffer()) {
			if(Settings.getInstance().getCachedSetting("auto_login") && (p.getAddress().getHostString().equals(account.getIP()))) {
				account.forceLogin(p, true);
				return;
			}
			new BukkitRunnable() {
				@Override
				public void run() {
					if(p != null && (!Arrays.getInstance().estaLogado(p))) p.kickPlayer(Messages.getInstance().getCachedMessage("demorou_logar"));
				}
			}.runTaskLater(nProtect.m, 20*Integer.valueOf(Settings.getInstance().getCachedValue("tempo_logar")));
			p.setWalkSpeed(0);
			p.setFlySpeed(0);
			p.sendMessage(Messages.getInstance().getCachedMessage("logar_chat").replace("%grupo%", account.getGrupo().getGrupo()));
			if(Settings.getInstance().getCachedSetting("usar_title")) {
				TitleAPI.sendTitle(p, 0, 30, 30, Messages.getInstance().getCachedMessage("loginstaff_title"), Messages.getInstance().getCachedMessage("logar_subtitle"));
			}
		}
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		Conta account = new Conta(p.getName());
		if(account.isStaffer()) {
			if(nProtect.loginPluginType == LoginPluginType.UNKNOW) {
				if(Settings.getInstance().getCachedSetting("auto_login") && (p.getAddress().getHostString().equals(account.getIP()))) {
					account.forceLogin(p, true);
					return;
				}
				new BukkitRunnable() {
					@Override
					public void run() {
						if(p != null && (!Arrays.getInstance().estaLogado(p))) p.kickPlayer(Messages.getInstance().getCachedMessage("demorou_logar"));
					}
				}.runTaskLater(nProtect.m, 20*Integer.valueOf(Settings.getInstance().getCachedValue("tempo_logar")));
				p.setWalkSpeed(0);
				p.setFlySpeed(0);
				p.sendMessage(Messages.getInstance().getCachedMessage("logar_chat").replace("%grupo%", account.getGrupo().getGrupo()));
				if(Settings.getInstance().getCachedSetting("usar_title")) {
					TitleAPI.sendTitle(p, 0, 30, 30, Messages.getInstance().getCachedMessage("loginstaff_title"), Messages.getInstance().getCachedMessage("logar_subtitle"));
				}
			}
		} else {
			Arrays.getInstance().adicionarLogados(p.getName());
		}
	}
	@EventHandler
	public void onMexer(PlayerMoveEvent e) {
		if(!Arrays.getInstance().estaLogado(e.getPlayer())) e.getPlayer().teleport(e.getFrom());
	}
	@EventHandler
	public void onSair(PlayerQuitEvent e) {
		Arrays.getInstance().removerLogados(e.getPlayer().getName());
	}
	@EventHandler
	public void onFalar(AsyncPlayerChatEvent e) {
		if(!Arrays.getInstance().estaLogado(e.getPlayer())) e.setCancelled(true);
	}
	@EventHandler
	public void onComando(PlayerCommandPreprocessEvent e) {
		if(!Arrays.getInstance().estaLogado(e.getPlayer()) && (!commandMatches(e.getMessage().toLowerCase().split(" ")[0]))) e.setCancelled(true);
	}
	@EventHandler
	public void onInteragir(PlayerInteractEvent e) {
		if(!Arrays.getInstance().estaLogado(e.getPlayer())) e.setCancelled(true);
	}
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if(!Arrays.getInstance().estaLogado(e.getPlayer())) e.setCancelled(true);
	}
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if(!Arrays.getInstance().estaLogado(e.getPlayer())) e.setCancelled(true);
	}
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if(!Arrays.getInstance().estaLogado(e.getPlayer())) e.setCancelled(true);
	}
	private boolean commandMatches(String commandToCheck) {
		for(String stringCompare : Arrays.comandosPermitidos) { if(stringCompare.toLowerCase().equals(commandToCheck)) return true; } return false;
	}
}
