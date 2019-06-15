package nickultracraft.protect.hooks;

/**
 * Copyright 2019 NickUltracraft
 *
 * A class PermissionPluginType.java pertence ao projeto (PLUGIN - nProtectV2) pertencente à NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * É expressamente proibído alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link para acesso da source original.
 *
 * Este aviso não pode ser removido ou alterado de qualquer distribuição de origem.
*/

public enum PermissionPluginType {
	
	PERMISSIONSEX("PermissionsEx"),
	LUCKPERMS("LuckPerms"),
	GROUPMANAGER("GroupManager"),
	VAULT_PLUGIN("VaultPermission"),
	UNKNOW("Unknow"),
	OTHER("Other");
	
	public String name;
	
	PermissionPluginType(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}

}
