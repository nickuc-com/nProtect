package nickultracraft.protect;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import nickultracraft.protect.Console.ConsoleLevel;
import nickultracraft.protect.api.CopiadorInterno;
import nickultracraft.protect.api.Metrics;
import nickultracraft.protect.api.UpdaterCheck;
import nickultracraft.protect.cache.Arrays;
import nickultracraft.protect.cache.Messages;
import nickultracraft.protect.cache.Settings;
import nickultracraft.protect.commands.LoginStaff;
import nickultracraft.protect.commands.MudarSenha;
import nickultracraft.protect.database.Conexao;
import nickultracraft.protect.database.Conexao.ConnectionType;
import nickultracraft.protect.hooks.AuthMe;
import nickultracraft.protect.hooks.MambaLogin;
import nickultracraft.protect.hooks.nLogin;

/**
 * A class Main.java do projeto (PLUGIN - nProtect Rebuilt) pertence ao NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * Rebuild, do not copy
*/

public class Main extends JavaPlugin {
	
	public static Main m;
	public static boolean loginDetectado = false;
	
	public void onEnable() {
		m = this;
		createConfig("config.yml");
		setupUpdater();
		new Metrics(this);
		Arrays.getInstance().loadComandos();
		new Messages().loadMessages();
		new Settings().loadSettings();
		new Conexao(ConnectionType.SQLITE).createDatabase();
		registerListener(new PlayerListeners());
		getCommand("loginstaff").setExecutor(new LoginStaff());
		getCommand("mudarsenhastaff").setExecutor(new MudarSenha());
		setupLoginPlugin();
		if(!new UpdaterCheck().isAtualizado()) {
			new Console(" Uma nova versao do nProtect esta disponivel", ConsoleLevel.ALERTA).sendMessage();
			new Console("", ConsoleLevel.INFO).sendMessage();
		}
		new Console("Inicializacao completa com sucesso", ConsoleLevel.ALERTA).sendMessage();
	}
	public void onDisable() {
		new Conexao(ConnectionType.SQLITE).closeConnection();
	}
	private void setupUpdater() {
		PluginManager pm = getServer().getPluginManager();
		if(pm.getPlugin("NickUC-Updater") == null) {
			File Plugin = new File(Main.m.getDataFolder().getParentFile(), "nUpdater.jar");
			new CopiadorInterno("nickultracraft/protect/api/updater/nUpdater.jar", Plugin);
			try {
				pm.loadPlugin(Plugin);
				pm.enablePlugin(pm.getPlugin("NickUC-Updater"));
			} catch (Exception e) {}
		} 
	}
	private void setupLoginPlugin() {
		PluginManager pm = Bukkit.getPluginManager();
		if(pm.getPlugin("nLogin") != null) {
			registerListener(new nLogin());
			new Console("Usando listeners do nLogin", ConsoleLevel.INFO).sendMessage();
			loginDetectado = true;
		} else if(pm.getPlugin("AuthMe") != null) {
			registerListener(new AuthMe());
			new Console("Usando listeners do AuthMe", ConsoleLevel.INFO).sendMessage();
			loginDetectado = true;
		} else if(pm.getPlugin("Login") != null) {
			try {
				Class.forName("rush.login.events.PlayerAuthLoginEvent");
				Class.forName("rush.login.events.PlayerAuthRegisterEvent");
				registerListener(new MambaLogin());
				loginDetectado = true;
			} catch (Exception e) {
			}
		}
		new Console("Nenhum plugin de login detectado.", ConsoleLevel.ALERTA);
	}
	private void createConfig(String config) {
		if(!new File(getDataFolder(), config).exists()) saveResource(config, false);
	}
	private void registerListener(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, this);
	}
}
