package nickultracraft.protect.objects;

/**
 * Copyright 2019 NickUltracraft
 *
 * A class Grupo.java pertence ao projeto (PLUGIN - nProtectV2) pertencente à NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * É expressamente proibído alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link para acesso da source original.
 *
 * Este aviso não pode ser removido ou alterado de qualquer distribuição de origem.
*/

import nickultracraft.protect.nProtect;

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
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String toString() {
		return "Grupo [grupo=" + grupo + ", password=" + password + "]";
	}
	public static Grupo getGrupoFromName(String grupoName) {
		for(Grupo grupo : nProtect.grupos) {
			if(grupo.getGrupo().equalsIgnoreCase(grupoName)) return grupo;
		}
		return null;
	}

}
