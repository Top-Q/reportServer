package il.co.topq.systems.report.common.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ServerMetadataHolder {

	private static String ip;
	private static String port;
	private static String applicationContextPath;
	private static String scenarioLogLocation;
	private static final String DEFAULT_SERVER_PORT = "8080";
	private static final String DEFAULT_REPORT_LOGS_FOLDER = "results";
	private static String tomcatWebappsFolderPath;

	private ServerMetadataHolder() {
	}

	public static String getServersPort() {
		return ServerMetadataHolder.port;
	}

	public static String getServerIP() {
		if (ServerMetadataHolder.ip == null) {
			retrieveServerIP();
		}
		return ServerMetadataHolder.ip;
	}

	private static void retrieveServerIP() {
		ServerMetadataHolder.ip = IPResolver.getServerIP();
	}

	@Value("${server.port:#{null}}")
	public void setServerPort(String serverPort) {
		if (serverPort != null && !serverPort.isEmpty()) {
			ServerMetadataHolder.port = serverPort;
		} else {
			ServerMetadataHolder.port = DEFAULT_SERVER_PORT;
		}
	}

	public static String getApplicationContextPath() {
		return ServerMetadataHolder.applicationContextPath;
	}

	public static void setApplicationContextPath(String applicationContextPath) {
		ServerMetadataHolder.applicationContextPath = applicationContextPath;
	}

	public static String getScenarioLogFolder() {
		return ServerMetadataHolder.scenarioLogLocation;
	}

	@Value("${scenario.log.location:#{null}}")
	public void setScenarioLogLocation(String scenarioLogLocation) {
		if (scenarioLogLocation != null && !scenarioLogLocation.isEmpty()) {
			ServerMetadataHolder.scenarioLogLocation = scenarioLogLocation;
		} else {
			ServerMetadataHolder.scenarioLogLocation = DEFAULT_REPORT_LOGS_FOLDER;
		}
	}
	
	public static String getTomcatWebappsFolderPath(){
		return ServerMetadataHolder.tomcatWebappsFolderPath;
	}
	
	public static void setTomcatWebappsFolder(String tomcatWebappsFolderPath){
		ServerMetadataHolder.tomcatWebappsFolderPath=tomcatWebappsFolderPath;
	}

}
