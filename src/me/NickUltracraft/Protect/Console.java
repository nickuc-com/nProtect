package me.NickUltracraft.Protect;

import org.bukkit.Bukkit;

/**
 * A class Console.java do projeto (PLUGIN - nProtect Rebuilt) pertence ao NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * Rebuild, do not copy
*/

public class Console {

	public enum ConsoleLevel {
		INFO, ERRO, ALERTA;
	}
	private String message;
	private ConsoleLevel level = ConsoleLevel.INFO;
	
	public Console(String message, ConsoleLevel level) {
		this.level = level;
		if(message == null) {
			this.message = "Texto nulo :(";
			new IllegalArgumentException("Voce nao pode inserir textos nulos!");
		} else {
			this.message = message;
		}
	}
	public void sendMessage() {
		switch (level) {
		case ALERTA:
			sendMethod("§6[nProtect]");
		case ERRO:
			sendMethod("§c[nProtect]");
		case INFO:
			sendMethod("[nProtect]");
		}
	}
	private void sendMethod(String color) {
		Bukkit.getConsoleSender().sendMessage(color + " " + message);
	}
	public void execute() {
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), message);
	}
}
