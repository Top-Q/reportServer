package il.co.topq.systems.report.component.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import il.co.topq.systems.report.common.infra.log.ReportLogger;
import il.co.topq.systems.report.common.model.User;
import il.co.topq.systems.report.common.obj.Chunk;
import il.co.topq.systems.report.common.obj.UserRole;
import il.co.topq.systems.report.component.jaxbWrappers.UserList;
import il.co.topq.systems.report.component.jaxbWrappers.UserRoleList;
import il.co.topq.systems.report.service.infra.UserService;

import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/user")
public class UserWebService {
	private Logger log = ReportLogger.getInstance().getLogger(this.getClass());

	@Autowired
	UserService userService;

	@Secured(value = "ROLE_USER_MANAGER")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	User createUser(ServletResponse response, @RequestBody User user)
			throws Exception {
		log.info("in create user method");
		try{
			User createdUser = userService.create(user);
			return createdUser; 
		}catch(Exception e){
			response.getWriter().println(e.getMessage());
			return null;
		}
	}

	@Secured(value = "ROLE_USER_MANAGER")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	User updateUser(ServletResponse response, @RequestBody User user)
			throws Exception {
		log.info("in update user method");
		try {
			return userService.update(user);
		} catch (Exception e) {
			log.error("update user failed: " + e.getMessage());
			throw e;
		}
	}

	@Secured(value = "ROLE_USER_MANAGER")
	@RequestMapping(value = "/delete/{userId}", method = RequestMethod.GET)
	public void deleteUser(ServletResponse response,
			@PathVariable("userId") int userId) {
		log.info("in delete user method");
		userService.delete(userId);
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public @ResponseBody
	User getUser(@PathVariable("userId") int userId) {
		log.info("in get User method ");
		return userService.get(userId);
	}

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public @ResponseBody
	UserList getUsers(Chunk chunk) {
		log.info("get chunk of users");
		if (chunk != null) {
			return new UserList(userService.getChunk(chunk));
		} else {
			return new UserList(userService.getAll());
		}
	}

	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public void countUsers(ServletResponse response) throws IOException {
		log.info("in count users method");
		long numOfUsers = userService.countAll(null);
		response.getWriter().println(numOfUsers);
	}

	@RequestMapping(value = "/userRoles", method = RequestMethod.GET)
	public @ResponseBody
	UserRoleList getUserRolesList() throws Exception {
		log.info("in getUsersRoles list method");
		UserRole[] userRoles = UserRole.values();
		List<String> userRoleList = new ArrayList<String>();
		for (UserRole userRole : userRoles) {
			userRoleList.add(userRole.getValue());
		}
		return new UserRoleList(userRoleList);
	}

	@RequestMapping(value = "/allUsers", method = RequestMethod.GET)
	public @ResponseBody
	UserList getAllUsers() throws Exception {
		log.info("in get all users method");
		List<User> users = userService.getAll();
		// for (User user : users) {
		// user.setUsername("");
		// user.setPassword("");
		// }
		return new UserList(users);
	}
}
