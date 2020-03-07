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

package com.nickuc.protect;

import com.nickuc.ncore.api.config.nConfig;
import com.nickuc.ncore.api.logger.ConsoleLogger;
import com.nickuc.ncore.api.minecraft.spigot.AbstractPlugin;
import com.nickuc.protect.commands.LoginStaff;
import com.nickuc.protect.hook.LoginProvider;
import com.nickuc.protect.hook.PermissionProvider;
import com.nickuc.protect.hook.plugins.login.AuthMe;
import com.nickuc.protect.hook.plugins.login.LoginEnum;
import com.nickuc.protect.hook.plugins.login.MambaLogin;
import com.nickuc.protect.hook.plugins.login.nLogin;
import com.nickuc.protect.hook.plugins.permissions.*;
import com.nickuc.protect.listener.PlayerListeners;
import com.nickuc.protect.management.Messages;
import com.nickuc.protect.management.Settings;
import com.nickuc.protect.objects.Group;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.ArrayList;
import java.util.List;

public class nProtect extends AbstractPlugin {
	
	public static nProtect nprotect;
	public static LoginEnum loginType;
	public static PermissionEnum permissionType;
	public static LoginProvider loginProvider;
	public static PermissionProvider permissionProvider;
	public static List<Group> grupos = new ArrayList<>();
	public static Permission permissionPluginVault = null;

	public nProtect() {
		super("nProtect");
	}

	@Override
	public void enablePlugin() {
		nprotect = this;
		manageConfig();

		String c = "§b";
		ConsoleLogger.info(c+"         ___            _            _   ");
		ConsoleLogger.info(c+" _ __   / _ \\_ __ ___ | |_ ___  ___| |_ ");
		ConsoleLogger.info(c+"| '_ \\ / /_)/ '__/ _ \\| __/ _ \\/ __| __|");
		ConsoleLogger.info(c+"| | | / ___/| | | (_) | ||  __/ (__| |_ ");
		ConsoleLogger.info(c+"|_| |_\\/    |_|  \\___/ \\__\\___|\\___|\\__|");
		ConsoleLogger.info(c+"                                        ");
		ConsoleLogger.info(c+" By: www.nickuc.com - V " + getDescription().getVersion() + " RELEASE Build");
		ConsoleLogger.info("");
		ConsoleLogger.info(c+"Inicializando tarefas para inicialização...");

		if(getNplugin().getRequestedData().isConnectionAvailable()) {
			if (getNplugin().getRequestedData().isUpdateAvailable() || getNplugin().getRequestedData().isUpdateForced()) {
				ConsoleLogger.info(c+"Uma nova versão do nProtect está disponível (" + getDescription().getVersion() + " -> " + getNplugin().getRequestedData().getLatestVersion() + ")");
				getManagement().getUpdaterManager().setUpdateConfirm(true);
			}
		}

		Messages.load();
		Settings.load();

		registerListeners(new PlayerListeners());
		registerCommands(new LoginStaff(this));

		setupPermissionPlugin();
		setupLoginPlugin();

		ConsoleLogger.info("Inicializacao completa com sucesso");
	}

	@Override
	public void disablePlugin() {}

	public static void setLoginProvider(LoginProvider loginProvider, Listener listener, LoginEnum loginEnum) {
		nProtect.loginProvider = loginProvider;
		nProtect.loginType = loginEnum;
		Bukkit.getPluginManager().registerEvents(listener, nProtect.nprotect);
		ConsoleLogger.info("[Provider] Login provider is: " + loginProvider.getProviderClass().getSimpleName());
	}

	public static void setPermissionProvider(PermissionProvider permissionProvider, PermissionEnum permissionEnum) {
		nProtect.permissionProvider = permissionProvider;
		nProtect.permissionType = permissionEnum;
		ConsoleLogger.info("[Provider] Permission provider is: " + permissionProvider.getProviderClass().getSimpleName());
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
		nConfig config = new nConfig("config.yml", getDataFolder());
		if(!config.existsConfig()) {
			config.saveDefaultConfig("config.yml");
		}
		for(String grupo : config.getConfigurationSection("Config.Grupos")) {
			String senha = getConfig().getString("Config.Grupos." + grupo);
			grupos.add(new Group(grupo, senha));
		}
	}
}
