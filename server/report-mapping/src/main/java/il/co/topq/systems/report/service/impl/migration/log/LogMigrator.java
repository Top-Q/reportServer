package il.co.topq.systems.report.service.impl.migration.log;

import il.co.topq.systems.report.common.model.Scenario;
import il.co.topq.systems.report.common.utils.ServerMetadataHolder;
import il.co.topq.systems.report.service.impl.migration.Migrator;
import il.co.topq.systems.report.service.impl.migration.infra.MigratorTask;
import il.co.topq.systems.report.service.infra.ScenarioService;
import il.co.topq.systems.report.utils.file.xml.PropertiesFileHandler;

import java.util.List;

public class LogMigrator extends Migrator implements MigratorTask {
	private static final String MIGRATE_LOG = "migrate.log";

	private ScenarioService scenarioService;

	public LogMigrator(ScenarioService scenarioService) {
		super();
		this.scenarioService = scenarioService;
	}

	@Override
	public void migrate() {

		log.info("running log migration");
		try {
			super.loadPropertiesFile();
			if (super.shouldMigrate(MIGRATE_LOG)) {
				while (!isApplicationDeployed()) {
					long sleepTime = 5000;
					System.out.println("log migration could not be started before application deployed, sleep for "
							+ sleepTime);
					Thread.sleep(sleepTime);
				}
				migrateScenariosHtmlDir();
				super.propertiesFileHandler.setProperty(MIGRATE_LOG, PropertiesFileHandler.FALSE);
			}
			super.propertiesFileHandler.close();
		} catch (Exception e) {
			this.log.error("failure in LogMigrator: " + e.getMessage());
		} finally {
			log.info("LogMigrator migration completed");
		}
	}

	private boolean isApplicationDeployed() {
		return ServerMetadataHolder.getApplicationContextPath() != null;
	}

	private void migrateScenariosHtmlDir() {

		List<Scenario> allSenarios = scenarioService.getAll();
		for (Scenario scenario : allSenarios) {
			String htmlDir = scenario.getHtmlDir();

			if (htmlDir == null) {
				continue;
			}

			log.info("html dir from DB before migration: " + htmlDir);
			if (isOldHtmlDir(htmlDir)) {
				updateScenarioHtmlDir(scenario, htmlDir);
			} else if (isNewUnderAppFolder(htmlDir)) {
				String[] url = htmlDir.split("/");
				String resultFolder = url[url.length - 2];
				updateScenarioHtmlDir(scenario, resultFolder);
			} else {
				log.info("html dir: " + htmlDir + " does not require migration");
			}
			// the pattern with only the TS of the upload (report-server V1.0)
			// e.g. '1370352093303'

			// boolean isHtmlDirInOldPattern = false;
			// try {
			// Integer.parseInt(htmlDir);
			// isHtmlDirInOldPattern = true;
			// } catch (NumberFormatException e) {
			// // in case of not number
			// }
			//
			// if (isHtmlDirInOldPattern) {
			//
			// } else {
			//
			// }

		}
	}

	private void updateScenarioHtmlDir(Scenario scenario, String htmlDir) {
		String newHtmlDir = constructNewHtmlDir(htmlDir);

		scenario.setHtmlDir(newHtmlDir);
		try {
			log.info("attempting to update scenario's html dir to: " + newHtmlDir);
			scenarioService.update(scenario);
		} catch (Exception e) {
			log.error("failed to update scenario" + e.getMessage());
		}
	}

	private String constructNewHtmlDir(String htmlDirName) {
		String newHtmlDir = "http://" + ServerMetadataHolder.getServerIP() + ":"
				+ ServerMetadataHolder.getServersPort() + "/" + ServerMetadataHolder.getScenarioLogFolder() + "/"
				+ htmlDirName + "/index.html";
		log.info("new html dir: " + newHtmlDir);
		return newHtmlDir;
	}

	private boolean isOldHtmlDir(String htmlDir) {
		return !htmlDir.contains(ServerMetadataHolder.getServerIP());
	}

	private boolean isNewUnderAppFolder(String htmlDir) {
		String oldAppContext = "report-service";
		return htmlDir.contains(oldAppContext + "/"
				+ ServerMetadataHolder.getScenarioLogFolder());
	}
}
