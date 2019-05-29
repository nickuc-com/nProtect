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

	public enum ConsoleLevel {
		INFO, ERRO, ALERTA, INVASAO;
	}
	public static void warning(String message) {
		send("§6[nProtect] " + message);
	}
	public static void error(String message) {
		send("§c[nProtect] " + message);
	}
	public static void invasion(String message) {
		send("§c[Invasao] " + message);
	}
	public static void info(String message) {
		send("[nProtect] " + message);
	}
	private static void send(String message) {
		Bukkit.getConsoleSender().sendMessage(message);
	}
}
