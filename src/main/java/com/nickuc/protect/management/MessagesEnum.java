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

import com.nickuc.ncore.api.config.nConfig;
import com.nickuc.ncore.api.settings.IMessagesEnum;
import com.nickuc.ncore.api.settings.Messages;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum MessagesEnum implements IMessagesEnum {

    INVALID_ARGS("Mensagens.argumentos_invalidos", "&cVocê deve usar: /loginstaff <sua senha>"),
    NAO_STAFFER("Mensagens.nao_staffer", "&cVocê deve ser um staffer para executar este comando."),
    JA_AUTENTICADO("Mensagens.ja_autenticado", "&cVocê já está autenticado como staffer."),
    INCORRECT_PASS("Mensagens.senha_incorreta", "&cA senha inserida está incorreta."),
    DEMOROU_LOGAR("Mensagens.demorou_logar", "&cVocê demorou muito para se autenticar como staffer"),
    MUDOUSENHA_SUCESSO("Mensagens.mudousenha_sucesso", "&aVocê alterou a sua senha com sucesso."),
    LOGINSTAFF_TITLE("Mensagens.loginstaff_title", "&e&lLOGIN STAFF"),
    LOGOU_SUBTITLE("Mensagens.logou_subtitle", "&eVocê logou com sucesso!"),
    LOGOU_SUBTITLE_SESSION("Mensagens.logou_subtitle_session", "&eSessão continuada com sucesso"),
    LOGOU_CHAT("Mensagens.logou_chat", "&aVocê logou como staffer com sucesso."),
    LOGAR_SUBTITLE("Mensagens.logar_subtitle", "Se autentique usando /loginstaff <senha>"),
    LOGAR_CHAT("Mensagens.logar_chat", "&ePara se autenticar como staffer, utilize /loginstaff <sua senha>\n&e&lDICA: &eVocê deve inserir a senha do grupo %grupo%"),
    LOGAR_CHAT2("Mensagens.logar_chat2", "&ePara se autenticar como staffer, utilize /loginstaff <sua senha>\n&e&lDICA: &eVocê deve inserir a senha para os grupos default.");

    private String key;
    private String defaultValue;

    public static void reload(nConfig config) {
        Messages.loadMessages(MessagesEnum.values(), config);
    }

}
