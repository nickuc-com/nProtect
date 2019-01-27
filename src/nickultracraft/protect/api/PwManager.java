package nickultracraft.protect.api;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A class PwComparator.java do projeto (PLUGIN - nProtect Rebuilt) pertence ao NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * Rebuild, do not copy
*/

public class PwManager {
	
	private String password;
	
	public PwManager(String inputPassword) {
		this.password = inputPassword;
	}
	public boolean comparatePassword(String passwordStored, String saltStored) {
		return processKey(password, saltStored).equals(passwordStored);
	}
	public String generateRandomSalt() {
		StringBuilder builder = new StringBuilder();
	    String s = "";
	    s = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	    for (int i = 0; i < 10; i++) {
	    	double index = Math.random() * s.length();
	    	builder.append(s.charAt((int)index));
	    }
	    return builder.toString();
	}
	public String hash(String senha) {
		try {
			MessageDigest md;
			md = MessageDigest.getInstance("SHA-256");
			md.update(senha.getBytes());
			byte[] b = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte bl : b) {
				sb.append((Integer.toHexString(bl & 0xff).toString()));
			}
			String codificada = sb.toString();
			return codificada;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return senha;
	}
	public String processKey(String password, String salt) {
		String hashedPassword = hash(password);
		return hash(hashedPassword + salt);
	}
	

}
