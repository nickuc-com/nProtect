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

public class Console {

	public enum ConsoleLevel {
		INFO, ERRO, ALERTA, INVASAO;
	}
	private String message;
	private ConsoleLevel level = ConsoleLevel.INFO;
	
	public Console(String message, ConsoleLevel level) {
		this.level = level;
		if(message == null) {
			this.message = "Erro ao enviar esta mensagem.";
			new IllegalArgumentException("Voce nao pode inserir textos nulos!");
		} else {
			this.message = message;
		}
	}
	public void sendMessage() {
		switch (level) {
		case INVASAO:	
			sendMethod("§c[Invasao] ");
			return;
		case ALERTA:
			sendMethod("§6[nProtect]");
			return;
		case ERRO:
			sendMethod("§c[nProtect]");
			return;
		case INFO:
			sendMethod("[nProtect]");
			return;
		}
	}
	private void sendMethod(String color) {
		Bukkit.getConsoleSender().sendMessage(color + " " + message);
	}
	public void execute() {
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), message);
	}
}
