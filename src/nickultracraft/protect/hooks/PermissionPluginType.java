package nickultracraft.protect.hooks;

/**
 * A class LoginPluginType.java da package (nickultracraft.protect.hooks) pertence ao NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * É expressamente proibído alterar o nome do proprietário do código, sem
 * expressar e deixar claramente o link do download/source original.
*/

public enum PermissionPluginType {
	
	PERMISSIONSEX("PermissionsEx"),
	LUCKPERMS("LuckPerms"),
	GROUPMANAGER("GroupManager"),
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
