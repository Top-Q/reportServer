package il.co.topq.systems.report.service.impl.migration.properties;

import java.util.ArrayList;
import java.util.List;

import il.co.topq.systems.report.common.infra.log.ReportLogger;
import il.co.topq.systems.report.common.model.Scenario;
import il.co.topq.systems.report.common.model.Test;
import il.co.topq.systems.report.common.obj.Chunk;
import il.co.topq.systems.report.common.obj.ScenarioQuery;
import il.co.topq.systems.report.common.obj.TestQuery;
import il.co.topq.systems.report.service.impl.migration.Migrator;
import il.co.topq.systems.report.service.impl.migration.infra.MigratorTask;
import il.co.topq.systems.report.service.infra.ScenarioService;
import il.co.topq.systems.report.service.infra.TestService;
import il.co.topq.systems.report.utils.file.xml.PropertiesFileHandler;

import org.apache.log4j.Logger;

public class PropertiesMigrator extends Migrator implements MigratorTask {

	private ScenarioService scenarioService;
	private TestService testService;
	private final static String MIGRATE_PROPERTIES_STR = "migrate.propertiesStr";

	private Logger log = ReportLogger.getInstance().getLogger(this.getClass());

	public PropertiesMigrator(ScenarioService scenarioService, TestService testService) {

		super();
		this.scenarioService = scenarioService;
		this.testService = testService;
	}

	private void migrateScenarioProperties() {
		log.info("Migrating scenario properties");
		ScenarioQuery scenarioQuery;
		int chunkSize = 1000;

		long numOfScenarios = scenarioService.countAll(null);

		if (numOfScenarios > 0) {
			for (int i = 0; i <= numOfScenarios / chunkSize; i++) {
				scenarioQuery = new ScenarioQuery();
				scenarioQuery.setChunk(new Chunk(i * chunkSize, chunkSize));
				List<Scenario> scenarios = this.scenarioService.getScenariosByFiltersWithSortingCol(scenarioQuery);
				List<Scenario> updatedScenarios = new ArrayList<Scenario>();
				for (Scenario scenario : scenarios) {
					if (scenario.getPropertiesStr() == null) {
						scenario.setProperties(scenario.getProperties());
						updatedScenarios.add(scenario);
					}
				}
				if (updatedScenarios.size() > 0) {
					this.scenarioService.updateAll(updatedScenarios);
				}
			}
		}
	}

	private void migrateTestProperties() {
		log.info("Migrating test properties");
		TestQuery testQuery;
		int chunkSize = 1000;

		long numOfTests = testService.countAll(null);

		if (numOfTests > 0) {
			for (int j = 0; j <= numOfTests / chunkSize; j++) {
				testQuery = new TestQuery();
				testQuery.setChunk(new Chunk(j * chunkSize, chunkSize));
				List<Test> tests = this.testService.getTestsByFiltersWithSortingCol(testQuery);
				List<Test> updatedTests = new ArrayList<Test>();
				for (Test test : tests) {
					if (test.getPropertiesStr() == null) {
						test.setProperties(test.getProperties());
						updatedTests.add(test);
					}
				}
				if (updatedTests.size() > 0) {
					this.testService.updateAll(updatedTests);
				}
			}
		}
	}

	public void migrate() {
		log.info("running propertiesMigrator");
		try {
			super.loadPropertiesFile();
			if (shouldMigrate(MIGRATE_PROPERTIES_STR)) {
				migrateScenarioProperties();
				migrateTestProperties();
				propertiesFileHandler.setProperty(MIGRATE_PROPERTIES_STR, PropertiesFileHandler.FALSE);
				propertiesFileHandler.close();
				log.info("completed migration");
			}
		} catch (Exception e) {
			log.error("PropertiesMigrator error: " + e.getMessage());
		}
	}

}
