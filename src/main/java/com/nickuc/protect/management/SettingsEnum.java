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

package com.nickuc.protect.management;

import com.nickuc.ncore.api.config.*;
import com.nickuc.ncore.api.settings.*;
import lombok.*;

import java.util.*;

@AllArgsConstructor @Getter
public enum SettingsEnum implements ISettingsEnum {

    USAR_TITLE("Config.UsarTitle", true),
    AUTO_LOGIN("Config.AutoLogin", true),
    TEMPO_LOGAR("Config.TempoLogar", 25),
    SENHA_DEFAULT_SEM_CARGO("Config.SenhaDefault", "nprotect_default"),
    COMANDOS_PERMITIDOS("Config.ComandosPermitidos", new ArrayList<>()),
    ;

    private final String key;
    private final Object defaultValue;

    public static void reload(nConfig config) {
        Settings.loadSettings(SettingsEnum.values(), config);
    }

}
