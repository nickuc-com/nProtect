package nickultracraft.protect.cache;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import nickultracraft.protect.Console;
import nickultracraft.protect.Console.ConsoleLevel;
import nickultracraft.protect.Main;
import nickultracraft.protect.api.PwManager;
import nickultracraft.protect.database.Conexao;
import nickultracraft.protect.database.Conexao.ConnectionType;

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
	private boolean staffer = false;
	private String ip = "127.0.0.1";
	
	public Conta(String name) {
		this.name = name;
		loadAccount();
	}
	public void loadAccount() {
		try {
			Connection connection = new Conexao(ConnectionType.SQLITE).getConnection();
			PreparedStatement stm = connection.prepareStatement("SELECT * FROM `nProtect` WHERE `Usuario` = ?");;
			stm.setString(1, name.toLowerCase());
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				setIP(rs.getString("IP"));
				setSenha(rs.getString("Senha"));
			}
			new BukkitRunnable() {
				@Override
				public void run() {
					if(Bukkit.getPlayer(name) != null && (Bukkit.getPlayer(name).hasPermission("loginstaff.staffer"))) {
						setStaffer(true);
					} 
				}
			}.runTaskLater(Main.m, 10);
			stm.close();
		} catch (Exception e) {
			new Console("Falha ao carregar usuario " + name, ConsoleLevel.ERRO).sendMessage();
			e.printStackTrace();
		}
	}
	public void submitChanges() {
		try {
			Connection connection = new Conexao(ConnectionType.SQLITE).getConnection();
			PreparedStatement stm = connection.prepareStatement("SELECT * FROM `nProtect` WHERE `Usuario` = ?");;
			stm.setString(1, name.toLowerCase());
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				PreparedStatement stm2 = connection.prepareStatement("UPDATE `nProtect` SET `Senha` = ?, `IP` = ? WHERE `Usuario` = ?");
				PwManager pwManager = new PwManager(getSenha());
				String salt = pwManager.generateRandomSalt();
				stm2.setString(1, pwManager.processKey(getSenha(), salt) + "$" + salt);
				stm2.setString(2, getIP());
				stm2.setString(3, name.toLowerCase());
				stm2.executeUpdate();
			} else {
				PreparedStatement stm2 = connection.prepareStatement("INSERT INTO `nProtect`(`Usuario`,`Senha`,`IP`) VALUES (?,?,?)");;
				PwManager pwManager = new PwManager(getSenha());
				String salt = pwManager.generateRandomSalt();
				stm2.setString(1, name.toLowerCase());
				stm2.setString(2, pwManager.processKey(getSenha(), salt) + "$" + salt);
				stm2.setString(3, getIP());
				stm2.executeUpdate();
			}
			stm.close();
		} catch (Exception e) {
			new Console("Falha ao carregar usuario " + name, ConsoleLevel.ERRO).sendMessage();
			e.printStackTrace();
		}
	}
	public void updateIP() {
		try {
			Connection connection = new Conexao(ConnectionType.SQLITE).getConnection();
			PreparedStatement stm2 = connection.prepareStatement("UPDATE `nProtect` SET `IP` = ? WHERE `Usuario` = ?");
			stm2.setString(1, getIP());
			stm2.setString(2, name.toLowerCase());
			stm2.executeUpdate();
		} catch (Exception e) {
			new Console("Falha ao atualizar o ip do usuario " + name, ConsoleLevel.ERRO).sendMessage();
			e.printStackTrace();
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
			Connection connection = new Conexao(ConnectionType.SQLITE).getConnection();
			PreparedStatement stm = connection.prepareStatement("DELETE FROM `nProtect` WHERE Usuario = ?");
			stm.setString(1, name.toLowerCase());
			stm.executeUpdate();
			stm.close();
		} catch (Exception e) {
			new Console("Falha ao desregistrar usuario " + name, ConsoleLevel.ERRO).sendMessage();
			e.printStackTrace();
		}
	}
}
