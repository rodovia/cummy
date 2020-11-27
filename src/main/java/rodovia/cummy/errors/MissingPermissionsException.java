package rodovia.cummy.errors;

import java.util.List;

import net.dv8tion.jda.api.Permission;

public class MissingPermissionsException extends CummyException {
	
	private static final long serialVersionUID = 1L;
	private Permission[] missingPermissions;
	private String message = "";
	
	public MissingPermissionsException(String message, Permission... perms) {
		this.missingPermissions = perms;
		this.message = message;
	}
	
	public MissingPermissionsException(Permission... permissions) {
		this.missingPermissions = permissions;
	}
	
	public Permission[] getMissingPermissions() {
		return this.missingPermissions;
	}
	
	public String getMessage() {
		return this.message;
	}
}
