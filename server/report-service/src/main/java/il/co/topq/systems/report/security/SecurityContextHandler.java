package il.co.topq.systems.report.security;

import il.co.topq.systems.report.common.model.User;
import il.co.topq.systems.report.common.obj.UserRole;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityContextHandler {

	public static void setUserContext(User user) {

		Collection<GrantedAuthority> authorities = permissionsToGrantedAutorities(user
				.getPermissions());
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(
				user.getUsername(), user.getPassword(), authorities);
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				userDetails, user.getPassword(), userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);

	}

	@SuppressWarnings("deprecation")
	private static Collection<GrantedAuthority> permissionsToGrantedAutorities(
			String permissions) {
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if (permissions != null && !permissions.isEmpty()) {
			String[] permissionsArr = permissions.split(",");
			for (String permission : permissionsArr) {
				UserRole role = UserRole.getUserRole(permission);
				if (role != null) {
					authorities.add(new GrantedAuthorityImpl(role.name()));
				}
			}
		}
		return authorities;
	}
}
