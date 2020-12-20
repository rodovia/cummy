package rodovia.cummy.errors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.dv8tion.jda.api.Permission;

public class MissingPermissionsException extends CummyException {
	
	private static final long serialVersionUID = 1L;
	private Permission[] missingPermissions;
	
	public MissingPermissionsException(String message, Permission... perms) {
		super(message);
		this.missingPermissions = perms;
	}
	
	public MissingPermissionsException(Permission... permissions) {
		super();
		this.missingPermissions = permissions;
	}
	
	public List<String> getMissingPermissions() {
		Stream<String> stream = new ArrayList<Permission>(Arrays.asList(this.missingPermissions))
				.stream().map((it) -> it.getName());
		List<String> l = stream.collect(Collectors.toList());
		List<String> missingPermissionsName = new ArrayList<String>(l);
		
		return missingPermissionsName;
	}
}
