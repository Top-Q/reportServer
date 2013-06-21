package il.co.topq.systems.report.service.impl.migration;

import il.co.topq.systems.report.common.infra.log.ReportLogger;
import il.co.topq.systems.report.utils.file.xml.PropertiesFileHandler;
import il.co.topq.systems.report.utils.file.xml.PropertiesFileManager;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

public abstract class Migrator {

	protected String configurationDir = System.getenv("REPORT_SERVER_CONFIG");
	protected final static String MIGRATION_PROPERTIES_FILE_NAME = "migration.properties";
	private Logger log = ReportLogger.getInstance().getLogger(this.getClass());

	protected PropertiesFileHandler propertiesFileHandler;

	protected boolean shouldMigrate(String propertyKey) {

		boolean migrate = false;
		log.info("check if migration is needed");
		if (this.propertiesFileHandler != null) {
			if (Boolean.valueOf(this.propertiesFileHandler.getProperty(propertyKey))) {
				log.info("migration is needed: " + propertyKey + " = true");
				migrate = true;
			} else {
				log.info("migration is NOT needed: " + propertyKey + " = false");
			}
		} else {
			log.info("check failed as propertiesFileHandler is null");
		}
		return migrate;
	}

	protected void loadPropertiesFile() throws Exception {
		loadPropertiesFile(this.configurationDir + File.separator + Migrator.MIGRATION_PROPERTIES_FILE_NAME);
	}

	protected void loadPropertiesFile(String propertiesFilePath) throws Exception {
		this.propertiesFileHandler = PropertiesFileManager.load(propertiesFilePath);
	}
}
