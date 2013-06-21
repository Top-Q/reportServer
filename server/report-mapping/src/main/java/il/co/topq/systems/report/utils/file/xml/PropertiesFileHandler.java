package il.co.topq.systems.report.utils.file.xml;

import il.co.topq.systems.report.common.infra.log.ReportLogger;
import il.co.topq.systems.report.utils.file.listeners.FileClosedListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesFileHandler {

	private Properties properties;
	private FileClosedListener fileClosedListener;
	private File propertiesFile;

	private Logger log = ReportLogger.getInstance().getLogger(this.getClass());

	/*
	 * Will set to true on first setProperty operation
	 */
	private boolean fileChanged = false;
	private String propertiesFilePath;
	public final static String TRUE = "true";
	public final static String FALSE = "false";

	PropertiesFileHandler(FileClosedListener fileClosedListener) {
		this.properties = new Properties();
		this.fileClosedListener = fileClosedListener;
	}

	void load(String propertiesFilePath) throws Exception {
		setPropertiesFilePath(propertiesFilePath);
		this.propertiesFile = new File(propertiesFilePath);
		if (this.propertiesFile.exists()) {
			FileInputStream fileInputStream = null;
			try {
				fileInputStream = new FileInputStream(this.propertiesFile);
				this.properties.load(fileInputStream);
			} catch (IOException e) {
				log.error(e.getMessage());
				throw e;
			} finally {
				if (fileInputStream != null) {
					fileInputStream.close();
				} else {
					log.error("PropertiesFileHandler: unable to close resource");
				}
			}
		} else {
			log.info("migration.properties file was not found, not running properties migration");
			throw new Exception("property file: " + propertiesFilePath + " was not found");
		}
	}

	private synchronized void save() throws Exception {
		this.log.info("saving property file: " + this.propertiesFilePath);
		FileWriter fileWriter = null;
		if (this.propertiesFile.exists()) {
			synchronized (this.propertiesFile) {
				try {
					fileWriter = new FileWriter(this.propertiesFile);
					this.properties.store(fileWriter, "");
				} catch (Exception e) {
					this.log.error("failed storing properties file: " + e.getMessage());
					throw e;
				} finally {
					if (fileWriter != null) {
						fileWriter.close();
						this.fileClosedListener.fileClosed(this.propertiesFilePath);
					}
				}
			}
		} else {
			this.log.error("log property file trying to update does not exist");
			throw new Exception("log property file trying to update does not exist");
		}
	}

	/**
	 * This method will save the propertiesFile in case any changes were made.
	 * 
	 * @throws Exception
	 */
	public void close() throws Exception {
		if (fileChanged) {
			save();
		}
	}

	private void setPropertiesFilePath(String propertiesFilePath) {
		this.propertiesFilePath = propertiesFilePath;
	}

	public String getProperty(String property) {
		return this.properties.getProperty(property);
	}

	public void setProperty(String key, String value) {
		log.info("in set property method");
		log.info("prop key: " + key + " prop val: " + value);
		this.properties.setProperty(key, value);
		this.fileChanged = true;
	}
}
