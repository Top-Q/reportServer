package il.co.topq.systems.report.service.impl.migration;

import il.co.topq.systems.report.common.infra.log.ReportLogger;
import il.co.topq.systems.report.service.impl.migration.indexing.IndexMigrator;
import il.co.topq.systems.report.service.impl.migration.infra.MigratorTask;
import il.co.topq.systems.report.service.impl.migration.properties.PropertiesMigrator;
import il.co.topq.systems.report.service.impl.migration.user.UserMigrator;
import il.co.topq.systems.report.service.infra.DBMigrationService;
import il.co.topq.systems.report.service.infra.ScenarioService;
import il.co.topq.systems.report.service.infra.TestService;
import il.co.topq.systems.report.service.infra.UserService;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBMigrationServiceImpl implements DBMigrationService {

	@Autowired
	private ScenarioService scenarioService;

	@Autowired
	private TestService testService;

	@Autowired
	private UserService userService;

	private Logger log = ReportLogger.getInstance().getLogger(this.getClass());
	private static boolean migrated = false;

	@PostConstruct
	@Override
	public void doMigrate() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				if (!migrated) {
					migrated = true;
					log.info("------------------------------------------------------------------------------------------");
					log.info("--------------------------------------DB MIGRATION SERVICE--------------------------------");
					log.info("------------------------------------------------------------------------------------------");

					MigratorTask userMigrator = new UserMigrator(userService);
					userMigrator.migrate();
					MigratorTask indexMigrator = new IndexMigrator(scenarioService, testService);
					indexMigrator.migrate();
					MigratorTask propertiesMigrator = new PropertiesMigrator(scenarioService, testService);
					propertiesMigrator.migrate();
				}
			}
		}).start();
	}

	public ScenarioService getScenarioService() {
		return scenarioService;
	}

	public void setScenarioService(ScenarioService scenarioService) {
		this.scenarioService = scenarioService;
	}

	public TestService getTestService() {
		return testService;
	}

	public void setTestService(TestService testService) {
		this.testService = testService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
