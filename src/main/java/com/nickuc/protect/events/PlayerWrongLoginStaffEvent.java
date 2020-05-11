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

package com.nickuc.protect.events;

import com.nickuc.ncore.api.plugin.bukkit.events.*;
import lombok.*;

@AllArgsConstructor @Getter
public final class PlayerWrongLoginStaffEvent extends CancellableEvent {

	private final String player;
	private final String password;

}
