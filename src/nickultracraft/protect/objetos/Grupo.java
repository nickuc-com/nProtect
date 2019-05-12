package nickultracraft.protect.objetos;

import nickultracraft.protect.nProtect;

/**
 * A class Grupo.java da package (nickultracraft.protect.objetos) pertence ao NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * É expressamente proibído alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link do download/source original.
*/

public class Grupo {
	
	private String grupo;
	private String password;
	
	public Grupo(String grupo, String password) {
		this.grupo = grupo;
		this.password = password;
	}
	public String getGrupo() {
		return grupo;
	}
	public String getPassword() {
		return password;
	}
	public static Grupo getGrupoFromName(String grupoName) {
		for(Grupo grupo : nProtect.grupos) {
			if(grupo.getGrupo().equalsIgnoreCase(grupoName)) return grupo;
		}
		return null;
	}

}
