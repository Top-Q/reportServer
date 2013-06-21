package il.co.topq.systems.report.service.impl.migration.user;

import il.co.topq.systems.report.common.infra.log.ReportLogger;
import il.co.topq.systems.report.common.model.User;
import il.co.topq.systems.report.common.obj.UserRole;
import il.co.topq.systems.report.service.impl.migration.Migrator;
import il.co.topq.systems.report.service.impl.migration.infra.MigratorTask;
import il.co.topq.systems.report.service.infra.UserService;
import il.co.topq.systems.report.utils.file.xml.PropertiesFileHandler;

import java.io.File;

import org.apache.log4j.Logger;

public class UserMigrator extends Migrator implements MigratorTask {

	private UserService userService;
	private static final String CREATE_SUPER_USER = "create.super.user";
	private static final String USER_PROPERTIES = "user.properties";
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String FIRST_NAME = "firstName";
	private static final String LAST_NAME = "lastName";
	private static final String EMAIL= "email";

	private Logger log = ReportLogger.getInstance().getLogger(this.getClass());

	public UserMigrator(UserService userService) {
		this.userService = userService;
	}

	public void migrate() {
		log.info("running user migration");
		try {
			super.loadPropertiesFile();
			if (super.shouldMigrate(CREATE_SUPER_USER)) {
				User superUser = getSuperUser();
				User user = this.userService.getUser(superUser.getUsername(), superUser.getPassword());
				if (user == null) {
					User createdUser = userService.create(superUser);
					if (createdUser == null) {
						log.error("failed to create super user");
					}
				}
				super.propertiesFileHandler.setProperty(CREATE_SUPER_USER, PropertiesFileHandler.FALSE);
			}
			super.propertiesFileHandler.close();
		} catch (Exception e) {
			this.log.error("failure in UserMigrator: " + e.getMessage());
		} finally {
			log.info("UserMigrator migration completed");
		}
	}

	/**
	 * This user is a super user and there for will be assigned with all the roles exist in system
	 * 
	 * @return
	 * @throws Exception
	 */
	private User getSuperUser() throws Exception {
		PropertiesFileHandler keeper = this.propertiesFileHandler;
		User user = new User();
		try {
			super.loadPropertiesFile(new StringBuilder().append(super.configurationDir).append(File.separator)
					.append(USER_PROPERTIES).toString());

			String username = super.propertiesFileHandler.getProperty(USERNAME);
			if (username != null && !username.isEmpty()) {
				user.setUsername(super.propertiesFileHandler.getProperty(USERNAME));
			} else {
				user.setUsername("admin");
			}
			String password = super.propertiesFileHandler.getProperty(PASSWORD);
			if (password != null && !password.isEmpty()) {
				user.setPassword(password);
			} else {
				user.setPassword("admin");
			}

			String firstName = super.propertiesFileHandler.getProperty(FIRST_NAME);
			if (firstName != null && !firstName.isEmpty()) {
				user.setFirstName(firstName);
			} else {
				user.setFirstName("admin");
			}

			String lastName = super.propertiesFileHandler.getProperty(LAST_NAME);
			if (lastName != null && !lastName.isEmpty()) {
				user.setLastName(lastName);
			} else {
				user.setLastName("admin");
			}
			
			String email = super.propertiesFileHandler.getProperty(EMAIL);
			if (email != null && !email.isEmpty()) {
				user.setEmail(email);
			} else {
				user.setEmail("");//may need to leave null
			}

		} catch (Exception e) {
			log.info("failed to load user details from " + USER_PROPERTIES
					+ " file, creating default admin admin user.");
			user.setUsername("admin");
			user.setPassword("admin");
			user.setFirstName("admin");
			user.setLastName("admin");
			user.setEmail("");
		} finally {
			super.propertiesFileHandler.close();
			super.propertiesFileHandler = keeper;
		}
		user.setPermissions(getAllPermissions());
		return user;
	}

	private String getAllPermissions() {
		String permissions = "";
		for (UserRole role : UserRole.values()) {
			permissions += role.getValue() + ", ";
		}
		return permissions;
	}
}
