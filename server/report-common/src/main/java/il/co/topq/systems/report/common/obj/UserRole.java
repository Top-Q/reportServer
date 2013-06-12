package il.co.topq.systems.report.common.obj;

import java.util.HashMap;
import java.util.Map;

/**
 * This enum will represent the different roles that may be assigned to a User in the system.<br>
 * Role constant must begin with ROLE_XXX<br>
 * the value of the user permission will be the enum name
 * 
 * @author eran_g
 * 
 */
public enum UserRole {

	ROLE_DELETE_SCENARIO("Delete Scenario"),ROLE_USER_MANAGER("User Manager");
	private static Map<String, UserRole> lookup;

	static {
		lookup = new HashMap<String, UserRole>();
		for (UserRole userRole : UserRole.values()) {
			lookup.put(userRole.value.toLowerCase(), userRole);
		}
	}

	private UserRole(String value) {
		this.value = value;
	}

	private String value;

	public static UserRole getUserRole(String role) {
		return lookup.get(role.trim().toLowerCase());
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
