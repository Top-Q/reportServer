package il.co.topq.systems.report.utils.file.xml;

import il.co.topq.systems.report.common.infra.log.ReportLogger;
import il.co.topq.systems.report.utils.file.listeners.FileClosedListener;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class PropertiesFileManager {

	private static Logger log = ReportLogger.getInstance().getLogger(PropertiesFileManager.class);
	private static Map<String, PropertiesFileHandler> openPropertiesFiles = new HashMap<String, PropertiesFileHandler>();

	private PropertiesFileManager() {
	}

	/**
	 * This method will load a property file by the given path<br>
	 * In case changes are made to property file call {@link PropertiesFileHandler#close PropertiesFileHandler.close }
	 * 
	 * @param propertiesFilePath
	 *            the path to the propertiesFile wish to open.
	 * @return
	 * @throws Exception
	 */
	public static PropertiesFileHandler load(String propertiesFilePath) throws Exception {

		PropertiesFileHandler propertiesFileHandler;
		synchronized (openPropertiesFiles) {

			if (openPropertiesFiles.containsKey(propertiesFilePath)) {
				propertiesFileHandler = openPropertiesFiles.get(propertiesFilePath);
			} else {
				propertiesFileHandler = new PropertiesFileHandler(new FileClosedListener() {

					@Override
					public void fileClosed(String path) {
						if (openPropertiesFiles != null) {
							synchronized (openPropertiesFiles) {
								openPropertiesFiles.remove(path);
							}
						}
					}
				});

				try {
					log.info("loading propertiesFile: " + propertiesFilePath);
					propertiesFileHandler.load(propertiesFilePath);
					openPropertiesFiles.put(propertiesFilePath, propertiesFileHandler);
				} catch (Exception e) {
					log.error("failed to load properties file: " + e.getMessage());
					throw e;
				}
			}
		}
		return propertiesFileHandler;
	}

}
