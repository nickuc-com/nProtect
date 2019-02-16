package nickultracraft.protect.api;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A class CopiadorInterno.java do projeto (PLUGIN - nProtect REBUILT) pertence ao NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * Rebuild, do not copy
*/
public class CopiadorInterno {

	private String copiar;
	private File colar;
	
	public CopiadorInterno(String copiar, File colar) {
		this.copiar = copiar;
		this.colar = colar;
		copiar();
	}
	public static CopiadorInterno getInstance(String copiar, File colar) {
		return new CopiadorInterno(copiar, colar);
	}
	private void copiar() {
		try {
			InputStream is = getClass().getResourceAsStream("/" + copiar);
			OutputStream os = new BufferedOutputStream(new FileOutputStream(colar));
			byte[] buffer = new byte[4096];
			int size;
			while ((size = is.read(buffer)) != -1) {
				os.write(buffer, 0, size);
			}
			is.close();
			os.close();
		} catch (Exception e) {}
	}
}
