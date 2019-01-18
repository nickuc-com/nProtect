package me.NickUltracraft.Protect;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.NickUltracraft.Protect.Cache.Arrays;
import me.NickUltracraft.Protect.Cache.Messages;
import me.NickUltracraft.Protect.Comandos.LoginStaff;
import me.NickUltracraft.Protect.Comandos.MudarSenha;
import me.NickUltracraft.Protect.Console.ConsoleLevel;
import me.NickUltracraft.Protect.Database.Conexão;
import me.NickUltracraft.Protect.Database.Conexão.ConnectionType;

/**
 * A class Main.java do projeto (PLUGIN - nProtect Rebuilt) pertence ao NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * Rebuild, do not copy
*/

public class Main extends JavaPlugin {
	
	public static Main m;
	
	public void onEnable() {
		m = this;
		createConfig("config.yml");
		Arrays.getInstance().loadComandos();
		new Messages().loadMessages();
		new Conexão(ConnectionType.SQLITE).createDatabase();
		registerListener(new PlayerListeners());
		getCommand("loginstaff").setExecutor(new LoginStaff());
		getCommand("mudarsenha").setExecutor(new MudarSenha());
		new Console("Inicializacao completa com sucesso", ConsoleLevel.INFO);
	}
	public void onDisable() {
		new Conexão(ConnectionType.SQLITE).closeConnection();
	}
	private void createConfig(String config) {
		if(!new File(getDataFolder(), config).exists()) saveResource(config, false);
	}
	private void registerListener(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, this);
	}
}
