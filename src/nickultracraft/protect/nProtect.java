package nickultracraft.protect;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import nickultracraft.protect.api.Console;
import nickultracraft.protect.api.Metrics;
import nickultracraft.protect.api.UpdaterAPI;
import nickultracraft.protect.api.Console.ConsoleLevel;
import nickultracraft.protect.commands.LoginStaff;
import nickultracraft.protect.hooks.LoginAbstract;
import nickultracraft.protect.hooks.LoginPluginType;
import nickultracraft.protect.hooks.plugins.AuthMe;
import nickultracraft.protect.hooks.plugins.MambaLogin;
import nickultracraft.protect.hooks.plugins.nLogin;
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
	public static LoginAbstract loginAbstract;
	public static List<Grupo> grupos = new ArrayList<>();

	public void onEnable() {
		m = this;
		manageConfig();
		new Metrics(this);
		updaterAPI = new UpdaterAPI(this, "nProtect");
		updaterAPI.defaultEnableExecute();
		if(updaterAPI.isUpdateAvailable()) {
			new Console(" Uma nova versao do nProtect esta disponivel " + getDescription().getVersion() + " -> " + updaterAPI.getLastVersion(), ConsoleLevel.ALERTA).sendMessage();
			new Console("", ConsoleLevel.INFO).sendMessage();
		}
		Arrays.getInstance().loadComandos();
		Messages.getInstance().loadMessages();
		Settings.getInstance().loadSettings();
		registerListeners();
		registerCommands();
		setupLoginPlugin();

		new Console("Inicializacao completa com sucesso", ConsoleLevel.ALERTA).sendMessage();
	}
	public void onDisable() {
		if(updaterAPI != null) updaterAPI.defaultDisableExecute();
	}
	public static void setLoginAbstract(LoginAbstract loginAbstract, Listener listener, Plugin plugin, LoginPluginType loginPluginType) {
		nProtect.loginAbstract = loginAbstract;
		Bukkit.getPluginManager().registerEvents(listener, plugin);
		new Console("Usando listeners do " + plugin.getName(), ConsoleLevel.INFO).sendMessage();
	}
	public static LoginAbstract getLoginAbstract() {
		return loginAbstract;
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
		}
		loginPluginType = LoginPluginType.UNKNOW;
		new Console("Nenhum plugin de login detectado. Podem existir outros plugins que se conectem com o nProtect", ConsoleLevel.INFO);
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
