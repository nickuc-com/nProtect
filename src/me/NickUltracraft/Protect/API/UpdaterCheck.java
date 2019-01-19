package me.NickUltracraft.Protect.API;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import me.NickUltracraft.Protect.Console;
import me.NickUltracraft.Protect.Console.ConsoleLevel;
import me.NickUltracraft.Protect.Main;

/**
 * A class UpdaterCheck.java do projeto (PLUGIN - nProtect REBUILT) pertence ao NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * Rebuild, do not copy
*/

public class UpdaterCheck {
	
	private boolean atualizado = false;
	
	public UpdaterCheck() {
		try {
			String result = readFrom("https://www.nickuc.tk/plugin/info?nProtect").split("-")[0];
			if(result.equalsIgnoreCase("Plugin inexistente.")) {
				new Console("Erro ao checar por updates", ConsoleLevel.ERRO).sendMessage();;
				return;
			}
			atualizado = result.equals(Main.m.getDescription().getVersion());
		} catch (Exception e) {
			new Console("Erro ao checar por updates", ConsoleLevel.ERRO).sendMessage();;
		}
		atualizado = true;
	}
	public boolean isAtualizado() {
		return atualizado;
	}
	private String readFrom(String url) throws Exception {
		InputStream is;
		URLConnection openConnection = new URL(url).openConnection();
		openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
		is = openConnection.getInputStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
        	sb.append((char) cp);
        }
        return sb.toString();
	}

}
