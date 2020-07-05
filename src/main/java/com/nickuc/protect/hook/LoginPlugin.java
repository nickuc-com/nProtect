/**
 * Copyright NickUC
 * -
 * Esta class pertence ao projeto de NickUC
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.com
 * -
 * É expressamente proibido alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link para acesso da source original.
 * -
 * Este aviso não pode ser removido ou alterado de qualquer distribuição de origem.
 */

package com.nickuc.protect.hook;

import lombok.*;

@AllArgsConstructor @Getter
public enum LoginPlugin {
	
	NLOGIN("nLogin"),
	AUTHME("AuthMe"),
	MAMBALOGIN("Mamba Login"),
	UNKNOWN("Unknown"),
	OTHER("Other");

	private final String name;

	public static LoginPlugin search(String plugin) {
		for (LoginPlugin loginEnum : LoginPlugin.values()) {
			if (loginEnum.name.equals(plugin)) return loginEnum;
		}
		return null;
	}

}
