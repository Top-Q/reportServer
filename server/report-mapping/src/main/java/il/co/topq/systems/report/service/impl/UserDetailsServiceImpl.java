package il.co.topq.systems.report.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	// @Autowired
	// private UserDetailsDAO userDetailsDAO;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {

		// com.bmfusion.rnd.model.User user = userDetailsDAO.getUserByUsername(username);
		// if (user == null)
		// throw new UsernameNotFoundException("Invalid username.");

		@SuppressWarnings("unused")
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		// for (SecurityRole role : user.getRoles()) {
		// authorities.add(new GrantedAuthorityImpl(role.getRoleName()));
		// }
		User userDetails = new User(username, "", true, true, true, true, authorities);
		// User userDetails = new User(username, user.getPassword(), user.isActive(), user.isActive(), user.isActive(),
		// user.isActive(), authorities);
		return userDetails;
	}
	
	
}
