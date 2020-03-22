/**
 * Copyright NickUC
 * -
 * Esta class pertence ao projeto de NickUC
 * Mais informações: https://nickuc.com
 * -
 * É expressamente proibido alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link para acesso da source original.
 * -
 * Este aviso não pode ser removido ou alterado de qualquer distribuição de origem.
 */

package com.nickuc.protect.objects;

import com.nickuc.protect.nProtect;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor @Getter @Setter @ToString
public final class Group {

	private String name;
	private String password;

	public static Group search(String grupoName) {
		for(Group grupo : nProtect.getGrupos()) {
			if(grupo.name.equalsIgnoreCase(grupoName)) return grupo;
		}
		return null;
	}
}
