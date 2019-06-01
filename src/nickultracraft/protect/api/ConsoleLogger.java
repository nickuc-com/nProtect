package nickultracraft.protect.api;

import org.bukkit.Bukkit;

/**
 * A class Console.java da package (nickultracraft.protect) pertence ao NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * É expressamente proibído alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link do download/source original.
*/

public class ConsoleLogger {
	
	public static void warning(String message) {
		Bukkit.getLogger().warning("§6" + message);
	}
	public static void error(String message) {
		Bukkit.getLogger().warning("§c" + message);
	}
	public static void invasion(String message) {
		Bukkit.getLogger().warning("§c[Invasao] " + message);
	}
	public static void info(String message) {
		Bukkit.getLogger().info(message);
	}
}
