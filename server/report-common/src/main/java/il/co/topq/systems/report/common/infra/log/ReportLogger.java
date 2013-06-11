package il.co.topq.systems.report.common.infra.log;

import il.co.topq.systems.report.common.exception.SoftwareException;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class ReportLogger {

	private static ReportLogger instance;

	private ReportLogger() {
		Properties logProperties = new Properties();
		try {
			logProperties.load(this.getClass().getResourceAsStream(
					"/log.properties"));

		} catch (IOException e) {
			throw new SoftwareException(
					"Cannot initialized report system logger", e);
		}
		PropertyConfigurator.configure(logProperties);
	}

	public static ReportLogger getInstance() {
		if (null == instance) {
			try {
				instance = new ReportLogger();
			} catch (Exception e) {
				throw new SoftwareException(
						"Cannot initialized report system logger", e);
			}
		}
		return instance;
	}

	public Logger getLogger(final Class<?> clazz) {
		return Logger.getLogger(clazz);
	}

}
