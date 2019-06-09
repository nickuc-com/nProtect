package nickultracraft.protect;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.permission.Permission;
import nickultracraft.protect.api.Metrics;
import nickultracraft.protect.api.UpdaterAPI;
import nickultracraft.protect.api.ConsoleLogger;
import nickultracraft.protect.commands.LoginStaff;
import nickultracraft.protect.hooks.LoginAbstract;
import nickultracraft.protect.hooks.LoginPluginType;
import nickultracraft.protect.hooks.PermissionAbstract;
import nickultracraft.protect.hooks.PermissionPluginType;
import nickultracraft.protect.hooks.plugins.login.AuthMe;
import nickultracraft.protect.hooks.plugins.login.MambaLogin;
import nickultracraft.protect.hooks.plugins.login.nLogin;
import nickultracraft.protect.hooks.plugins.permissions.GroupManager;
import nickultracraft.protect.hooks.plugins.permissions.LuckPerms;
import nickultracraft.protect.hooks.plugins.permissions.PermissionsEx;
import nickultracraft.protect.hooks.plugins.permissions.VaultPlugin;
import nickultracraft.protect.listener.PlayerListeners;
import nickultracraft.protect.objects.Arrays;
import nickultracraft.protect.objects.Grupo;
import nickultracraft.protect.objects.Messages;
import nickultracraft.protect.objects.Settings;

/**
 * A class Main.java da package (nickultracraft.protect) pertence ao NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * É expressamente proibído alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link do download/source original.
*/

public class nProtect extends JavaPlugin {
	
	public static nProtect m;
	public static UpdaterAPI updaterAPI;
	public static LoginPluginType loginPluginType;
	public static PermissionPluginType permissionPluginType;
	public static LoginAbstract loginAbstract;
	public static PermissionAbstract permissionPlugin;
	public static List<Grupo> grupos = new ArrayList<>();
	public static Permission permissionPluginVault = null;

	public void onEnable() {
		m = this;
		manageConfig();
		new Metrics(this);
		updaterAPI = new UpdaterAPI(this, "nProtect");
		updaterAPI.defaultEnableExecute();
		if(updaterAPI.isUpdateAvailable()) {
			ConsoleLogger.warning(" Uma nova versao do nProtect esta disponivel " + getDescription().getVersion() + " -> " + updaterAPI.getLastVersion());
			ConsoleLogger.info("");
		}
		Arrays.getInstance().loadComandos();
		Messages.getInstance().loadMessages();
		Settings.getInstance().loadSettings();
		registerListeners();
		registerCommands();
		setupPermissionPlugin();
		setupLoginPlugin();
		ConsoleLogger.info("Inicializacao completa com sucesso");
	}
	public void onDisable() {
		if(updaterAPI != null) updaterAPI.defaultDisableExecute();
	}
	public static void setLoginAbstract(LoginAbstract loginAbstract, Listener listener, Plugin plugin, LoginPluginType loginPluginType) {
		nProtect.loginAbstract = loginAbstract;
		nProtect.loginPluginType = loginPluginType;
		Bukkit.getPluginManager().registerEvents(listener, plugin);
		ConsoleLogger.info("Vinculando sistema de registro do " + loginAbstract.getPluginName());
	}
	public static void setPermissionAbstract(PermissionAbstract permissionPlugin, Plugin plugin, PermissionPluginType permissionPluginType) {
		nProtect.permissionPluginType = permissionPluginType;
		nProtect.permissionPlugin = permissionPlugin;
		ConsoleLogger.info("Vinculando sistema de permissões do " + permissionPlugin.getPluginName());
	}
	public static LoginAbstract getLoginAbstract() {
		return loginAbstract;
	}
	public static PermissionAbstract getPermissionAbstract() {
		return permissionPlugin;
	}
	private void setupLoginPlugin() {
		PluginManager pm = Bukkit.getPluginManager();
		if(pm.getPlugin("nLogin") != null) {
			setLoginAbstract(new nLogin(), new nLogin(), this, LoginPluginType.NLOGIN);
		} else if(pm.getPlugin("AuthMe") != null) {
			setLoginAbstract(new AuthMe(), new AuthMe(), this, LoginPluginType.AUTHME);
		} else if(pm.getPlugin("Login") != null) {
			try {
				Class.forName("rush.login.events.PlayerAuthLoginEvent");
				Class.forName("rush.login.events.PlayerAuthRegisterEvent");
				setLoginAbstract(new MambaLogin(), new MambaLogin(), this, LoginPluginType.MAMBALOGIN);
			} catch (Exception e) {}
		} else {
			loginPluginType = LoginPluginType.UNKNOW;
			ConsoleLogger.warning("Nenhum plugin de login detectado. Podem existir outros plugins que se conectem com o nProtect");
		}
	}
	private void setupPermissionPlugin() {
		if(setupVault()) {
			ConsoleLogger.warning("Plugin de permissoes foi detectado automaticamente pelo hook do Vault (" + permissionPluginVault.getName() + ")");
			setPermissionAbstract(new VaultPlugin(), this, PermissionPluginType.VAULT_PLUGIN);
			return;
		} else {
			ConsoleLogger.info("Nenhum plugin foi detectado pelo hook do Vault. Utilizando deteccao propria do plugin...");
		}
		PluginManager pm = Bukkit.getPluginManager();
		if(pm.getPlugin("PermissionsEx") != null) {
			setPermissionAbstract(new PermissionsEx(), this, PermissionPluginType.PERMISSIONSEX);
		} else if(pm.getPlugin("LuckPerms") != null) {
			setPermissionAbstract(new LuckPerms(), this, PermissionPluginType.LUCKPERMS);
		} else if(pm.getPlugin("GroupManager") != null) {
			setPermissionAbstract(new GroupManager(), this, PermissionPluginType.GROUPMANAGER);
		} else {
			permissionPluginType = PermissionPluginType.UNKNOW;
			ConsoleLogger.warning("Nenhum plugin de permissoes detectado. Podem existir outros plugins que se conectem com o nProtect");
		}
	}
	private boolean setupVault() {
		PluginManager pm = Bukkit.getPluginManager();
		if(pm.getPlugin("Vault") != null) {
			RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
		    if (permissionProvider != null) {
		    	permissionPluginVault = (Permission)permissionProvider.getProvider();
		    }
		    return permissionPluginVault != null;
		}
		return false;
	}
	private void manageConfig() {
		if(!new File(getDataFolder(), "config.yml").exists()) saveResource("config.yml", false);
		for(String grupo : getConfig().getConfigurationSection("Config.Grupos").getKeys(false)) {
			String senha = getConfig().getString("Config.Grupos." + grupo);
			grupos.add(new Grupo(grupo, senha));
		}
	}
	private void registerListener(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, this);
	}
	private void registerListeners() {
		registerListener(new PlayerListeners());
	}
	private void registerCommands() {
		getCommand("loginstaff").setExecutor(new LoginStaff());
	}
}
