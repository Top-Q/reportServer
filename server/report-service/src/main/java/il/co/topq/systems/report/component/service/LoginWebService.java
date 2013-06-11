package il.co.topq.systems.report.component.service;

import il.co.topq.systems.report.common.exception.ErrorMessage;
import il.co.topq.systems.report.common.infra.log.ReportLogger;
import il.co.topq.systems.report.common.model.User;
import il.co.topq.systems.report.context.SecurityContextHandler;
import il.co.topq.systems.report.service.infra.ScenarioService;
import il.co.topq.systems.report.service.infra.UserService;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

//TODO: change pass to login
@Controller
@RequestMapping(value = "/authorization")
public class LoginWebService {

	private Logger log = ReportLogger.getInstance().getLogger(this.getClass());

	@Autowired
	ScenarioService scenarioService;

	@Autowired
	UserService userService;

	@RequestMapping(value = "isAlive", method = RequestMethod.GET)
	public void isAlive(ServletResponse response) {
		log.info("in login service is alive method");
	}

	@RequestMapping(value = "/{username}", method = RequestMethod.POST)
	public @ResponseBody
	User login(ServletResponse response, @PathVariable("username") String username,
			@RequestParam("password") String password) throws Exception {
		log.info("in isAuthorized web service");
		User user = this.userService.getUser(username, password);
		if (user != null) {
			SecurityContextHandler.setUserContext(user);
			return user;
		} else {
			throw new Exception(ErrorMessage.INVALID_USER_PERMISSIONS);
		}

	}

	@RequestMapping(value = "/configuration", method = RequestMethod.GET)
	public void getUIConfigurations(ServletResponse response) throws IOException {
		// assuming 1 user exist in db.
		List<User> allUsers = this.userService.getAll();
		log.info("number of users in system: " + allUsers.size());
		if (allUsers.size() > 0) {
			User user = allUsers.get(0);
			if (user.getUiConfigurations() != null) {
				response.getWriter().println(user.getUiConfigurations());
			}
		} else {
			log.info("there are no users in DB");
		}
	}

	@RequestMapping(value = "/set-configuration", method = RequestMethod.POST)
	public void updateUIConfigurations(ServletResponse response, @RequestBody String uiConfigurations) throws Exception {
		List<User> allUsers = this.userService.getAll();
		log.info("number of users in system: " + allUsers.size());
		if (allUsers.size() > 0) {
			User user = allUsers.get(0);
			user.setUiConfigurations(uiConfigurations);
			userService.update(user);
			response.getWriter().print("true");
		} else {
			log.info("there are no users in DB");
		}
	}
}
