package il.co.topq.systems.report.component.service;

import il.co.topq.systems.report.common.exception.ErrorMessage;
import il.co.topq.systems.report.common.infra.log.ReportLogger;
import il.co.topq.systems.report.common.model.User;
import il.co.topq.systems.report.common.utils.ServerMetadataHolder;
import il.co.topq.systems.report.security.SecurityContextHandler;
import il.co.topq.systems.report.service.infra.ScenarioService;
import il.co.topq.systems.report.service.infra.UserService;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

	@Autowired
	ServletContext context;

	// TODO: move to some base controller
	@PostConstruct
	private void setAppContextToServerMetadataHolder() {
		String contextPath = context.getContextPath();
		ServerMetadataHolder.setApplicationContextPath(contextPath);
		ServerMetadataHolder.setTomcatWebappsFolder(new File(context.getRealPath("")).getParent());
	}

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
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = this.userService.getUser(authentication.getName(), authentication.getCredentials()
				.toString());
		if (currentUser != null) {
			String uiConfigurations = currentUser.getUiConfigurations();
			if (uiConfigurations != null) {
				response.getWriter().println(uiConfigurations);
			}
		} else {
			log.info(ErrorMessage.USER_NOT_FOUND_IN_CONTEXT);
		}
	}

	@RequestMapping(value = "/set-configuration", method = RequestMethod.POST)
	public void updateUIConfigurations(ServletResponse response, @RequestBody String uiConfigurations) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = this.userService.getUser(authentication.getName(), authentication.getCredentials()
				.toString());
		if (currentUser != null) {
			currentUser.setUiConfigurations(uiConfigurations);
			userService.update(currentUser);
			response.getWriter().print("true");
		} else {
			log.info(ErrorMessage.USER_NOT_FOUND_IN_CONTEXT);
		}
	}
}
