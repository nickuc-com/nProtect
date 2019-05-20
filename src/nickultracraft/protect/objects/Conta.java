package nickultracraft.protect.objects;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import nickultracraft.protect.nProtect;
import nickultracraft.protect.api.Console;
import nickultracraft.protect.api.TitleAPI;
import nickultracraft.protect.api.Console.ConsoleLevel;
import nickultracraft.protect.events.PlayerLoginStaffEvent;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

/**
 * A class Conta.java da package (nickultracraft.protect.cache) pertence ao NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * É expressamente proibído alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link do download/source original.
*/

public class Conta {
	
	private String name;
	private Grupo grupo;
	private String ip = "127.0.0.1";
	private boolean staffer = false;
	
	public Conta(String name) {
		this.name = name;
		loadAccount();
	}
	public void loadAccount() {
		try {
			PermissionUser permissionUser = PermissionsEx.getPermissionManager().getUser(name);
			for(Grupo grupo : nProtect.grupos) {
				if(permissionUser.inGroup(grupo.getGrupo())) this.grupo = grupo;
			}
			setStaffer(grupo != null);
		} catch (Exception e) {
			new Console("Falha ao carregar usuario " + name, ConsoleLevel.ERRO).sendMessage();
			e.printStackTrace();
		}
	}
	public void updateIP(String ip) throws Exception {
		File file = new File(nProtect.m.getDataFolder(), "playerdata");
		file.mkdirs();
		File pfile = new File(nProtect.m.getDataFolder() + "/playerdata", name.toLowerCase() + ".yml");
		FileConfiguration pcfile = YamlConfiguration.loadConfiguration(pfile);
		pcfile.options().header("Arquivo de database para " + name + ".\nNão é recomendado para ser utilizado em grandes amostras de dados.");
		pcfile.set("address", ip);
		pcfile.save(pfile);
	}
	public void forceLogin(Player p) {
		forceLogin(p, false);
	}
	public void forceLogin(Player p, boolean session) {
		Arrays.getInstance().adicionarLogados(p.getName());
		if(session) {
			p.sendMessage(Messages.getInstance().getCachedMessage("logou_chat"));
			if(Settings.getInstance().getCachedSetting("usar_title")) TitleAPI.sendTitle(p, 0, 3, 2, Messages.getInstance().getCachedMessage("loginstaff_title"), Messages.getInstance().getCachedMessage("logou_subtitle"));
		} else {
			if(Settings.getInstance().getCachedSetting("usar_title")) TitleAPI.sendTitle(p, 0, 3, 2, Messages.getInstance().getCachedMessage("loginstaff_title"), Messages.getInstance().getCachedMessage("logou_subtitle_session"));
		}
		if(Settings.getInstance().getCachedSetting("auto_login")) {
			try { if(!getIP().equals(p.getAddress().getHostString())) updateIP(p.getAddress().getHostString());	} catch (Exception e) {}
		}
		p.setWalkSpeed((float)0.2);
		p.setFlySpeed((float)0.2);
		Bukkit.getPluginManager().callEvent(new PlayerLoginStaffEvent(p, getSenha()));
	}
	public Grupo getGrupo() {
		return grupo;
	}
	public String getName() {
		return name;
	}
	public String getSenha() {
		return grupo.getPassword();
	}
	public String getIP() {
		return ip;
	}
	public boolean isStaffer() {
		return staffer;
	}
	public void setStaffer(boolean staffer) {
		this.staffer = staffer;
	}
}
