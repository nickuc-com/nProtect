package nickultracraft.protect.objects;

/**
 * Copyright 2019 NickUltracraft
 *
 * A class Conta.java pertence ao projeto (PLUGIN - nProtectV2) pertencente à NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * É expressamente proibído alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link para acesso da source original.
 *
 * Este aviso não pode ser removido ou alterado de qualquer distribuição de origem.
*/

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import nickultracraft.protect.nProtect;
import nickultracraft.protect.api.ConsoleLogger;
import nickultracraft.protect.api.TitleAPI;
import nickultracraft.protect.events.PlayerLoginStaffEvent;
import nickultracraft.protect.hooks.PermissionPluginType;

public class Conta {
	
	private Player player;
	private String name;
	private Grupo grupo;
	private String address = "127.0.0.1";
	private boolean staffer = false;

	public Conta(Player player) {
		this.player = player;
		this.name = player.getName();
		loadAccount();
	}
	public void loadAccount() {
		try {
			if(nProtect.permissionPluginType != PermissionPluginType.UNKNOW) {
				for(Grupo grupo : nProtect.grupos) {
					if(nProtect.getPermissionAbstract().inGroup(player, grupo.getGrupo())) this.grupo = grupo;
				}
			} else {
				Player target = Bukkit.getPlayer(name);
				if(target != null && target.hasPermission("loginstaff.staffer")) {
					this.grupo = new Grupo("PERMISSION_GROUP", Settings.getInstance().getCachedValue("senha_default_sem_cargo")); 
				}
			}
			setStaffer(grupo != null);
		} catch (Exception e) {
			ConsoleLogger.error("Falha ao carregar usuario " + name);
			e.printStackTrace();
		}
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Grupo getGrupo() {
		return grupo;
	}
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
	public String getPassword() {
		return getGrupo().getPassword();
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public boolean isStaffer() {
		return staffer;
	}
	public void setStaffer(boolean staffer) {
		this.staffer = staffer;
	}
	public String toString() {
		return "Conta [player=" + player + ", name=" + name + ", grupo=" + grupo + ", address=" + address + ", staffer="
				+ staffer + "]";
	}
	public ContaOperations getContaOperations() {
		return new ContaOperations();
	}
	public class ContaOperations {
		
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
				if(Settings.getInstance().getCachedSetting("usar_title")) {
					TitleAPI.sendTitle(p, 0, 8, 5, Messages.getInstance().getCachedMessage("loginstaff_title"), Messages.getInstance().getCachedMessage("logou_subtitle_session"));
				}
			} else {
				if(Settings.getInstance().getCachedSetting("usar_title")) {
					TitleAPI.sendTitle(p, 0, 8, 5, Messages.getInstance().getCachedMessage("loginstaff_title"), Messages.getInstance().getCachedMessage("logou_subtitle"));
				}
			}
			if(Settings.getInstance().getCachedSetting("auto_login")) {
				try { if(!getAddress().equals(p.getAddress().getHostString())) updateIP(p.getAddress().getHostString());	} catch (Exception e) {}
			}
			p.setWalkSpeed((float)0.2);
			p.setFlySpeed((float)0.2);
			Bukkit.getPluginManager().callEvent(new PlayerLoginStaffEvent(p, getPassword()));
		}
		
	}

}
