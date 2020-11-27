package rodovia.cummy.errors;

import net.dv8tion.jda.api.Permission;

public class UserMissingPermissionsException extends MissingPermissionsException {
	private static final long serialVersionUID = 1L;
	
	public UserMissingPermissionsException(String message, Permission... perms) {
		super(message, perms);
	}
	
	public UserMissingPermissionsException(Permission... permissions) {
		super(permissions);
	}
	
}
