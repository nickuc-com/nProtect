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
import com.nickuc.ncore.api.plugin.bukkit.AbstractPlugin;
import com.nickuc.ncore.api.plugin.bukkit.events.Listener;
import com.nickuc.protect.commands.LoginStaff;
import com.nickuc.protect.hook.LoginPlugin;
import com.nickuc.protect.hook.LoginProvider;
import com.nickuc.protect.hook.plugins.AuthMe;
import com.nickuc.protect.hook.plugins.MambaLogin;
import com.nickuc.protect.hook.plugins.nLogin;
import com.nickuc.protect.listener.PlayerListeners;
import com.nickuc.protect.management.MessagesEnum;
import com.nickuc.protect.management.SettingsEnum;
import com.nickuc.protect.objects.Group;
import lombok.Getter;
import lombok.Setter;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.ArrayList;
import java.util.List;

public class nProtect extends AbstractPlugin {

	@Getter @Setter private static LoginProvider loginProvider;
	@Getter private static final List<Group> grupos = new ArrayList<>();
	public static Permission permission;

	public nProtect() {
		super("nProtect");
	}

	@Override
	public void enablePlugin() {

		/**
		 * Plugin startup
		 */
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

		getNplugin().notifyUpdate(c, true);

		/**
		 * Setup config
		 */
		config();

		/**
		 * Register commands
		 */
		registerCommands(new LoginStaff());

		/**
		 * Register listeners
		 */
		registerListeners(new PlayerListeners());

		/**
		 * Setup hook
		 */
		setupPermissionPlugin();
		setupLoginPlugin();
	}

	public void setLoginProvider(LoginProvider loginProvider, Listener<nProtect> listener, LoginPlugin loginEnum) {
		nProtect.loginProvider = loginProvider;
		registerListeners(listener);
		ConsoleLogger.info("[Provider] Login provider is: " + loginProvider.getLoginPlugin().getName());
	}

	private void setupLoginPlugin() {
		PluginManager pm = Bukkit.getPluginManager();
		if(pm.getPlugin("nLogin") != null) {
			nLogin nlogin = new nLogin();
			setLoginProvider(nlogin, nlogin, LoginPlugin.NLOGIN);
		} else if(pm.getPlugin("AuthMe") != null) {
			AuthMe authme = new AuthMe();
			setLoginProvider(authme, authme, LoginPlugin.AUTHME);
		} else if(pm.getPlugin("Login") != null) {
			try {
				Class.forName("rush.login.events.PlayerAuthLoginEvent");
				Class.forName("rush.login.events.PlayerAuthRegisterEvent");
				MambaLogin mambalogin = new MambaLogin();
				setLoginProvider(mambalogin, mambalogin, LoginPlugin.MAMBALOGIN);
			} catch (ClassNotFoundException e) {}
		} else {
			setLoginProvider(new LoginProvider() {
				@Override
				public LoginPlugin getLoginPlugin() {
					return LoginPlugin.UNKNOWN;
				}
			});
			ConsoleLogger.warning("Nenhum plugin de login detectado. Usando listeners do Bukkit");
		}
	}

	private void setupPermissionPlugin() {
		ConsoleLogger.warning(setupVault() ? "Plugin de permissões foi detectado automaticamente pelo hook do Vault (" + permission.getName() + ")" : "Nenhum plugin foi detectado pelo hook do Vault. Utilizando deteccao propria do plugin...");
	}

	private boolean setupVault() {
		PluginManager pm = Bukkit.getPluginManager();
		if(pm.getPlugin("Vault") != null) {
			RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
		    if (permissionProvider != null) {
		    	permission = permissionProvider.getProvider();
		    }
		    return permission != null;
		}
		return false;
	}

	private void config() {
		nConfig config = new nConfig("config.yml", getDataFolder());
		if(!config.exists()) {
			config.saveDefaultConfig("config.yml");
		}
		for(String grupo : config.getConfigurationSection("Config.Grupos")) {
			String senha = getConfig().getString("Config.Grupos." + grupo);
			grupos.add(Group.wrap(grupo, senha));
		}

		/**
		 * Load settings
		 */
		MessagesEnum.reload(config);
		SettingsEnum.reload(config);
	}
}
