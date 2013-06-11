package il.co.topq.systems.report.component.settings;

import java.io.File;

import javax.servlet.ServletContext;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import org.jfree.util.Log;
import org.springframework.stereotype.Controller;

@Path("/config")
@Controller
public class ConfigurationService {

	// public static final String CONFIG_FILE = "config.properties";

	@Context
	private static ServletContext servletContext;

	// @RequestMapping(value = "/isAlive", method = RequestMethod.GET)
	// public void isAlive(ServletResponse response) {
	// System.out.println("in configurationService isAlive method");
	// }

	// @RequestMapping(method = RequestMethod.GET)
	// public void isThereConfig(ServletResponse response) throws IOException {
	// response.getWriter().print(true);
	// File configFile = new File(servletContext.getRealPath(""), CONFIG_FILE);
	// if (configFile.exists()) {
	// response.getWriter().print(true);
	// } else {
	// response.getWriter().print(false);
	// }
	// }

	// @RequestMapping(method = RequestMethod.POST)
	// public void setConfig(ServletResponse response, @RequestParam("username") String username,
	// @RequestParam("password") String password) throws Exception {
	// FileWriter fileWriter = null;
	// File configFile = new File(servletContext.getRealPath(""), CONFIG_FILE);
	// try {
	// fileWriter = new FileWriter(configFile);
	// Properties prop = new Properties();
	// prop.put("username", username);
	// prop.put("password", password);
	// prop.store(fileWriter, "");
	// response.getWriter().print(true);
	// } catch (Exception e) {
	// Log.error(e.getMessage());
	// throw e;
	// } finally {
	// if (fileWriter != null) {
	// fileWriter.flush();
	// fileWriter.close();
	// }
	// }
	// }

	public static String getRootPath() throws Exception {
		String rootPath = servletContext.getRealPath(File.separator);

		if (new File(servletContext.getRealPath(File.separator)).exists()) {
			return servletContext.getRealPath(File.separator);
		} else {
			Log.info("application root path does not exist: " + rootPath);
			throw new Exception("report service root path: " + rootPath + " does not exist");
		}

		// String tomcatRootPath = getTomcatRootPath();
		// String reportServiceRootPath = "";

		// if (new File(tomcatRootPath + "webapps" + File.separator + "report-service").exists()) {
		// if (new File(tomcatRootPath + File.separator + "report-service").exists()) {
		// reportServiceRootPath = tomcatRootPath + File.separator + "report-service" + File.separator;
		// return reportServiceRootPath;
		// } else {
		// throw new Exception("report service root path: " + reportServiceRootPath
		// + " does not exist in webapps scope");
		// }
	}

	/**
	 * This method will return the results (log folder) root path<br>
	 * 
	 * @return String represents the absolute path.
	 * @throws Exception
	 */
	public static String getResultsRootPath() throws Exception {

		String tomcatRootPath = getTomcatRootPath();
		String resultsPath = "";
		if (new File(tomcatRootPath + "webapps" + File.separator + "results").exists()) {
			resultsPath = tomcatRootPath + "webapps" + File.separator + "results" + File.separator;
			return resultsPath;
		} else {
			throw new Exception("results path: " + resultsPath + " does not exist in webapps scope");
		}

	}

	private static String getTomcatRootPath() throws Exception {

		String catalinaBase = System.getProperty("catalina.base");
		File configFile = new File(servletContext.getRealPath(""), "");

		String reportServiceRoot = configFile.getParent();
		String tomcatRootPath = new File(reportServiceRoot).getParent();

		System.out.println("catalina.base=" + catalinaBase);
		System.out.println("tomcatRootPath=" + tomcatRootPath);
		return tomcatRootPath;

		// String currDir = System.getProperty("user.dir");
		// String[] split = currDir.split("\\" + File.separator);
		// Integer tomcatIndex = null;
		// String tomcatPath = "";
		//
		// for (int i = 0; i < split.length; i++) {
		// //if this is the Tomcat folder(can be also "Tomcat6" or "Apache Tomcat X.X.XX"
		// if ((split[i].toLowerCase().contains("tomcat"))) {
		//
		// tomcatIndex = i;
		// }
		// }
		//
		// if (tomcatIndex == null) {
		// throw new Exception("Could not find Tomcat or tomcat in Curr Dir: " + currDir);
		// }
		//
		// for (int i = 0; i <= tomcatIndex; i++) {
		// tomcatPath += split[i] + File.separator;
		// }
		// return tomcatPath;
	}

}
