package me.NickUltracraft.Protect.Cache;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.bukkit.Bukkit;

import me.NickUltracraft.Protect.Console;
import me.NickUltracraft.Protect.API.PwManager;
import me.NickUltracraft.Protect.Console.ConsoleLevel;
import me.NickUltracraft.Protect.Database.Conexão;
import me.NickUltracraft.Protect.Database.Conexão.ConnectionType;

/**
 * A class Conta.java do projeto (PLUGIN - nProtect Rebuilt) pertence ao NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * Rebuild, do not copy
*/

public class Conta {
	
	private String name;
	private String senha;
	private boolean staffer;
	private String ip;
	
	public Conta(String name) {
		this.name = name;
		loadAccount();
	}
	public void loadAccount() {
		try {
			Connection connection = new Conexão(ConnectionType.SQLITE).getConnection();
			PreparedStatement stm = connection.prepareStatement("SELECT * FROM `nProtect` WHERE `Usuario` = ?");;
			stm.setString(1, name.toLowerCase());
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				setIP(rs.getString("IP"));
				setSenha(rs.getString("Senha"));
				setStaffer(Boolean.valueOf(rs.getString("Staffer")));
			}
			stm.close();
		} catch (Exception e) {
			new Console("Falha ao carregar usuario " + name, ConsoleLevel.ERRO).sendMessage();
		}
	}
	public void submitChanges() {
		try {
			Connection connection = new Conexão(ConnectionType.SQLITE).getConnection();
			PreparedStatement stm = connection.prepareStatement("SELECT * FROM `nProtect` WHERE `Usuario` = ?");;
			stm.setString(1, name.toLowerCase());
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				PreparedStatement stm2 = connection.prepareStatement("UPDATE `nProtect` SET `Senha` = ?, `IP` = ?, `Staffer` = ? WHERE `Usuario` = ?");;
				PwManager pwManager = new PwManager(getSenha());
				String salt = pwManager.generateRandomSalt();
				stm2.setString(1, pwManager.processKey(getSenha(), salt) + "$" + getSalt());
				stm2.setString(2, getIP());
				stm2.setString(3, String.valueOf(isStaffer()));
				stm2.setString(4, name.toLowerCase());
				stm2.executeUpdate();
			} else {
				PreparedStatement stm2 = connection.prepareStatement("INSERT INTO `nProtect`(`Usuario`,`Senha`,`IP`,`Staffer`) VALUES (?,?,?,?)");;
				PwManager pwManager = new PwManager(getSenha());
				String salt = pwManager.generateRandomSalt();
				stm2.setString(1, name.toLowerCase());
				stm2.setString(2, pwManager.processKey(getSenha(), salt) + "$" + getSalt());
				stm2.setString(3, getIP());
				stm2.setString(4, String.valueOf(isStaffer()));
				stm2.executeUpdate();
			}
			stm.close();
		} catch (Exception e) {
			new Console("Falha ao carregar usuario " + name, ConsoleLevel.ERRO).sendMessage();
		}
	}
	public String getName() {
		return name;
	}
	public String getSenha() {
		if(senha != null) return senha.split("\\$")[0]; return null;
	}
	public String getSalt() {
		if(senha != null) return senha.split("\\$")[1]; return null;
	}
	public String getIP() {
		return ip;
	}
	public boolean isStaffer() {
		if(Bukkit.getPlayer(name) != null && (Bukkit.getPlayer(name).hasPermission("loginstaff.staffer"))) {
			return true;
		} else if(Bukkit.getPlayer(name) != null && (!Bukkit.getPlayer(name).hasPermission("loginstaff.staffer"))) {
			return false;
		}
		return staffer;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public void setStaffer(boolean staffer) {
		this.staffer = staffer;
	}
	public void setIP(String ip) {
		this.ip = ip;
	}
	public void delete() {
		try {
			Connection connection = new Conexão(ConnectionType.SQLITE).getConnection();
			PreparedStatement stm = connection.prepareStatement("DELETE FROM `nProtect` WHERE Usuario = ?");
			stm.setString(1, name.toLowerCase());
			stm.executeUpdate();
			stm.close();
		} catch (Exception e) {
			new Console("Falha ao desregistrar usuario " + name, ConsoleLevel.ERRO).sendMessage();
		}
	}
}
