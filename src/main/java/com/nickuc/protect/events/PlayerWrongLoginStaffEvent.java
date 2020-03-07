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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.nickuc.ncore.api.minecraft.spigot.events.PluginEvent;
import org.bukkit.event.Cancellable;

@AllArgsConstructor @Getter
public final class PlayerWrongLoginStaffEvent extends PluginEvent implements Cancellable {

	private String player;
	private String password;
	@Setter private boolean cancelled;

}
