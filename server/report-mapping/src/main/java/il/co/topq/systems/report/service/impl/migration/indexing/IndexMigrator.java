package il.co.topq.systems.report.service.impl.migration.indexing;

import il.co.topq.systems.report.service.impl.migration.Migrator;
import il.co.topq.systems.report.service.impl.migration.infra.MigratorTask;
import il.co.topq.systems.report.service.infra.ScenarioService;
import il.co.topq.systems.report.service.infra.TestService;
import il.co.topq.systems.report.utils.file.xml.PropertiesFileHandler;

public class IndexMigrator extends Migrator implements MigratorTask {
	private final static String MIGRATE_SCENARIO_START_TIME = "migrate.scenario.startTime";
	private final static String MIGRATE_TEST_START_TIME = "migrate.test.startTime";

	
	private ScenarioService scenarioService;
	private TestService testService;

	public IndexMigrator(ScenarioService scenarioService, TestService testService) {
		super();
		this.scenarioService = scenarioService;
		this.testService = testService;
	}

	public void migrate() {
		try {
			super.loadPropertiesFile();
			migrateScenarioIndex();
			migrateTestIndex();
			super.propertiesFileHandler.close();
		} catch (Exception e) {
			this.log.error("failure in indexMigrator: " + e.getMessage());
		}
		log.info("indexing migration completed");
	}

	private void migrateScenarioIndex() {

		log.info("indexing: " + MIGRATE_SCENARIO_START_TIME);
		if (super.shouldMigrate(MIGRATE_SCENARIO_START_TIME)) {
			try {
				Object result = this.scenarioService.createIndex("published_runs_01", "startTime", "startTimeIndex");
				log.info("create index returned result: " + result);
				super.propertiesFileHandler.setProperty(MIGRATE_SCENARIO_START_TIME, PropertiesFileHandler.FALSE);
			} catch (Exception e) {
				log.error("create index failed:" + e.getMessage());
			}
		} else {
			log.info("indexing is not needed");
		}
	}

	private void migrateTestIndex() {
		log.info("indexing: " + MIGRATE_TEST_START_TIME);
		if (super.shouldMigrate(MIGRATE_TEST_START_TIME)) {
			try {
				Object result = this.testService.createIndex("published_test_01", "startTime", "startTimeIndex");
				log.info("create index returned result: " + result);
				super.propertiesFileHandler.setProperty(MIGRATE_TEST_START_TIME, PropertiesFileHandler.FALSE);
			} catch (Exception e) {
				log.error("create index failed:" + e.getMessage());
			}
		} else {
			log.info("indexing is not needed");
		}
	}
}
