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

package nickultracraft.protect;

import net.milkbowl.vault.permission.Permission;
import nickultracraft.ncore.minecraft.spigot.SpigotCore;
import nickultracraft.ncore.minecraft.spigot.logging.ConsoleLogger;
import nickultracraft.ncore.minecraft.spigot.management.CommandHandler;
import nickultracraft.ncore.minecraft.spigot.metrics.Metrics;
import nickultracraft.protect.commands.LoginStaff;
import nickultracraft.protect.hook.LoginProvider;
import nickultracraft.protect.hook.PermissionProvider;
import nickultracraft.protect.hook.plugins.login.AuthMe;
import nickultracraft.protect.hook.plugins.login.LoginEnum;
import nickultracraft.protect.hook.plugins.login.MambaLogin;
import nickultracraft.protect.hook.plugins.login.nLogin;
import nickultracraft.protect.hook.plugins.permissions.*;
import nickultracraft.protect.listener.PlayerListeners;
import nickultracraft.protect.objects.Arrays;
import nickultracraft.protect.objects.Group;
import nickultracraft.protect.objects.Messages;
import nickultracraft.protect.objects.Settings;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class nProtect extends JavaPlugin {
	
	public static nProtect instance;
	public static LoginEnum loginType;
	public static PermissionEnum permissionType;
	public static LoginProvider loginProvider;
	public static PermissionProvider permissionProvider;
	public static List<Group> grupos = new ArrayList<>();
	public static Permission permissionPluginVault = null;

	public void onEnable() {
		instance = this;
		manageConfig();
		new Metrics(this);
		try {
			new SpigotCore("nProtect", this);
			ConsoleLogger.warning(SpigotCore.core.getNplugin().getSecureKeyType().getSecureKeyMessage());
			if(!SpigotCore.core.getNplugin().getPluginRequestedData().isUpdateAvailable()) {
				ConsoleLogger.warning(" Uma nova versao do nProtect esta disponivel " + getDescription().getVersion() + " -> " + SpigotCore.core.getNplugin().getPluginRequestedData().getLatestVersion());
				ConsoleLogger.info("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Arrays.loadComandos();
		Messages.loadMessages();
		Settings.loadSettings();
		SpigotCore.core.getManagement().registerListeners(new PlayerListeners());
		SpigotCore.core.getManagement().registerCommands(new CommandHandler(this.getClass(), "loginstaff", new LoginStaff(), new LoginStaff(), java.util.Arrays.asList("ls"), "Comando para realizar a autenticação como staffer."));
		setupPermissionPlugin();
		setupLoginPlugin();
		ConsoleLogger.info("Inicializacao completa com sucesso");
	}

	public void onDisable() {
		SpigotCore.core.onDisable();
	}

	public static void setLoginProvider(LoginProvider loginProvider, Listener listener, LoginEnum loginEnum) {
		nProtect.loginProvider = loginProvider;
		nProtect.loginType = loginEnum;
		Bukkit.getPluginManager().registerEvents(listener, nProtect.instance);
		ConsoleLogger.info("[Provider] Permission provider is: " + loginProvider.getInjectorClass().getSimpleName());
	}

	public static void setPermissionProvider(PermissionProvider permissionProvider, PermissionEnum permissionEnum) {
		nProtect.permissionProvider = permissionProvider;
		nProtect.permissionType = permissionEnum;
		ConsoleLogger.info("[Provider] Permission provider is: " + permissionProvider.getInjectorClass().getSimpleName());
	}

	private void setupLoginPlugin() {
		PluginManager pm = Bukkit.getPluginManager();
		if(pm.getPlugin("nLogin") != null) {
			setLoginProvider(new nLogin(), new nLogin(), LoginEnum.NLOGIN);
		} else if(pm.getPlugin("AuthMe") != null) {
			setLoginProvider(new AuthMe(), new AuthMe(), LoginEnum.AUTHME);
		} else if(pm.getPlugin("Login") != null) {
			try {
				Class.forName("rush.login.events.PlayerAuthLoginEvent");
				Class.forName("rush.login.events.PlayerAuthRegisterEvent");
				setLoginProvider(new MambaLogin(), new MambaLogin(), LoginEnum.MAMBALOGIN);
			} catch (ClassNotFoundException e) {}
		} else {
			loginType = LoginEnum.UNKNOWN;
			ConsoleLogger.warning("Nenhum plugin de login detectado. Podem existir outros plugins que se conectem com o nProtect");
		}
	}

	private void setupPermissionPlugin() {
		if(setupVault()) {
			ConsoleLogger.warning("Plugin de permissoes foi detectado automaticamente pelo hook do Vault (" + permissionPluginVault.getName() + ")");
			setPermissionProvider(new VaultPlugin(), PermissionEnum.VAULT_PLUGIN);
			return;
		} else {
			ConsoleLogger.info("Nenhum plugin foi detectado pelo hook do Vault. Utilizando deteccao propria do plugin...");
		}
		PluginManager pm = Bukkit.getPluginManager();
		if(pm.getPlugin("PermissionsEx") != null) {
			setPermissionProvider(new PermissionsEx(), PermissionEnum.PERMISSIONSEX);
		} else if(pm.getPlugin("LuckPerms") != null) {
			setPermissionProvider(new LuckPerms(), PermissionEnum.LUCKPERMS);
		} else if(pm.getPlugin("GroupManager") != null) {
			setPermissionProvider(new GroupManager(), PermissionEnum.GROUPMANAGER);
		} else {
			permissionType = PermissionEnum.UNKNOW;
			ConsoleLogger.warning("Nenhum plugin de permissoes detectado. Podem existir outros plugins que se conectem com o nProtect");
		}
	}

	private boolean setupVault() {
		PluginManager pm = Bukkit.getPluginManager();
		if(pm.getPlugin("Vault") != null) {
			RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
		    if (permissionProvider != null) {
		    	permissionPluginVault = permissionProvider.getProvider();
		    }
		    return permissionPluginVault != null;
		}
		return false;
	}

	private void manageConfig() {
		if(!new File(getDataFolder(), "config.yml").exists()) {
			saveResource("config.yml", false);
		}
		for(String grupo : getConfig().getConfigurationSection("Config.Grupos").getKeys(false)) {
			String senha = getConfig().getString("Config.Grupos." + grupo);
			grupos.add(new Group(grupo, senha));
		}
	}
}
