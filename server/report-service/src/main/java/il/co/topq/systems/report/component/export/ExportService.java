package il.co.topq.systems.report.component.export;

import il.co.topq.systems.report.common.infra.log.ReportLogger;
import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.model.ScenarioProperty;
import il.co.topq.systems.report.common.model.TestProperty;
import il.co.topq.systems.report.common.obj.ScenarioQuery;
import il.co.topq.systems.report.common.obj.SortingColumn;
import il.co.topq.systems.report.common.obj.TestQuery;
import il.co.topq.systems.report.common.obj.TimeRange;
import il.co.topq.systems.report.component.settings.ConfigurationService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Path("/export")
@Controller
public class ExportService {

	@Autowired
	JasperUtil util;

	private Logger log = ReportLogger.getInstance().getLogger(this.getClass());
	public static final String SCENARIO_REPORT_TEMPLATE = "empty_scenarios_template.jrxml";
	public static final String TEST_REPORT_TEMPLATE = "empty_tests_template.jrxml";
	public static final String CUSTOM_REPORT_TEMPLATE = "empty_custom_template.jrxml";
	public static final String SCENARIO_DETAILED_REPORT_TEMPLATE = "empty_scenario_details_template.jrxml";
	public static final String STYLE = "table_template.jrxml";

	// Templates

	/**
	 * This method will return the template file.<br>
	 * Assumption: template files exist under web-inf/resources/{file}
	 * 
	 * @throws Exception
	 *             case root pass could be found
	 */
	public static File getTemplateFile(String templateFileName) throws Exception {
		String rootPath = ConfigurationService.getRootPath();
		File webInfFile = new File(rootPath, "WEB-INF");
		File resourcesFile = new File(webInfFile, "resources");
		return new File(resourcesFile, templateFileName);
	}

	private Response buildFile(JasperPrint jasperPrint, String fileName, String type) throws Exception {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		if (type.equalsIgnoreCase("pdf")) {
			JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
			return Response.ok(baos.toByteArray()).header("Content-Type", "application/force-download")
					.header("Content-Disposition", "attachment;filename=" + fileName + ".pdf").build();
		} else if (type.equalsIgnoreCase("xls")) {
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, baos);
			exporterXLS.exportReport();
			return Response.ok(baos.toByteArray()).header("Content-Type", "application/force-download")
					.header("Content-Disposition", "attachment;filename=" + fileName + ".csv").build();
		} else {
			return Response.ok("The requested report format does not exists")
					.header("Content-Type", "application/force-download")
					.header("Content-Disposition", "attachment;filename=jsystem_test_report.txt").build();
		}
	}

	// WebService

	@POST
	@Path("/scenarioDetailedReport/{type}")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response exportScenarioDetailedReport(@PathParam("type") String type, MultivaluedMap<String, String> form)
			throws Exception {

		Integer scenarioID = getScenarioIDFromForm(form);
		List<String> configProperties = getConfigPropertiesFromForm(form);

		// JasperUtil util = new JasperUtil();
		util.setScenarioProperties(scenarioID);
		JasperPrint jasperPrint = util.buildDetailsReport("Details", scenarioID,
				getTemplateFile(SCENARIO_DETAILED_REPORT_TEMPLATE).getAbsolutePath(), configProperties);
		return buildFile(jasperPrint, "jsystem_scenario_details_report", type);
	}

	@POST
	@Path("/testCustomReport/{type}")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response exportTestCustomReport(@PathParam("type") String type, MultivaluedMap<String, String> form)
			throws Exception {

		TestQuery testQuery = multipleValueMapToTestQuery(form);
		Integer testCustomReportID = getTestCustomReportIDFromForm(form);

		// JasperUtil util = new JasperUtil();
		util.setTestQuery(testQuery);
		JasperPrint jasperPrint = util.buildCustomReport("customTest", testCustomReportID,
				getTemplateFile(CUSTOM_REPORT_TEMPLATE).getAbsolutePath());

		return buildFile(jasperPrint, "jsystem_test_custom_report", type);
	}

	@POST
	@Path("/scenarioCustomReport/{type}")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response exportScenarioCustomReport(@PathParam("type") String type, MultivaluedMap<String, String> form)
			throws Exception {

		ScenarioQuery scenarioQuery = multipleMalueMapToScenarioQuery(form);
		Integer scenarioCustomReportID = getScenarioCustomReportIDFromForm(form);

		// JasperUtil util = new JasperUtil();
		util.setScenarioQuery(scenarioQuery);
		JasperPrint jasperPrint = util.buildCustomReport("customScenario", scenarioCustomReportID,
				getTemplateFile(CUSTOM_REPORT_TEMPLATE).getAbsolutePath());

		return buildFile(jasperPrint, "jsystem_scenario_custom_report", type);
	}

	@POST
	@Path("/test/{type}")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response exportTestReport(@PathParam("type") String type, MultivaluedMap<String, String> form)
			throws Exception {

		TestQuery testQuery = multipleValueMapToTestQuery(form);
		// JasperUtil util = new JasperUtil();
		util.setTestQuery(testQuery);
		JasperPrint jasperPrint = util.buildReport("Test", getTemplateFile(TEST_REPORT_TEMPLATE).getAbsolutePath());

		return buildFile(jasperPrint, "jsystem_test_report", type);
	}

	@POST
	@Path("/scenario/{type}")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response exportScenarioReport(@PathParam("type") String type, MultivaluedMap<String, String> form)
			throws Exception {

		ScenarioQuery scenarioQuery = multipleMalueMapToScenarioQuery(form);
		// JasperUtil util = new JasperUtil();
		util.setScenarioQuery(scenarioQuery);
		JasperPrint jasperPrint = util.buildReport("Scenario", getTemplateFile(SCENARIO_REPORT_TEMPLATE)
				.getAbsolutePath());

		return buildFile(jasperPrint, "jsystem_scenario_report", type);
	}

	@GET
	@Path("/loadScenario/{fileName}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response loadScenarioReport(@PathParam("fileName") String fileName) throws Exception {

		String exportedFileDirAbsPath = getExportedFileDirAbsPath();

		File scenarioReportFile = new File(exportedFileDirAbsPath + fileName);

		byte[] byteArray = toByteArray(scenarioReportFile);

		return Response.ok(byteArray).header("Content-Type", "application/force-download")
				.header("Content-Disposition", "attachment;filename=jsystem_scenario_report.csv").build();
	}

	// WebService

	// Jasper Util

	private Integer getTestCustomReportIDFromForm(MultivaluedMap<String, String> form) throws Exception {

		Integer testCustomReportID;

		try {
			testCustomReportID = Integer.parseInt(form.getFirst("testCustomReportID"));
		} catch (Exception e) {
			throw new Exception(e.getMessage() + "failed to parse test custom report id");
		}

		return testCustomReportID;

	}

	/**
	 * This method will retrieve the scenario ID from web form<br>
	 * 
	 * @param form
	 * @return Integer scenario ID;
	 * @throws Exception
	 *             scenario ID not found in form.
	 */
	private Integer getScenarioIDFromForm(MultivaluedMap<String, String> form) throws Exception {
		Integer scenarioID;

		try {
			scenarioID = Integer.parseInt(form.getFirst("scenarioID"));
		} catch (Exception e) {
			throw new Exception(e.getMessage() + "failed to parse scenario id");
		}

		return scenarioID;
	}

	private Integer getScenarioCustomReportIDFromForm(MultivaluedMap<String, String> form) throws Exception {

		Integer scenarioCustomReportID;

		try {
			scenarioCustomReportID = Integer.parseInt(form.getFirst("scenarioCustomReportID"));
		} catch (Exception e) {
			throw new Exception(e.getMessage() + "failed to parse scenario custom report id");
		}

		return scenarioCustomReportID;
	}

	private List<String> getConfigPropertiesFromForm(MultivaluedMap<String, String> form) {
		List<String> configProperties = new ArrayList<String>();
		Set<String> allKeys = form.keySet();
		Iterator<String> it = allKeys.iterator();
		while (it.hasNext()) {
			String key = it.next();
			if (key.contains("config:"))
				configProperties.add(key);
		}
		return configProperties;
	}

	private TestQuery multipleValueMapToTestQuery(MultivaluedMap<String, String> form) {

		TestQuery testQuery = new TestQuery();
		TimeRange timeRange = new TimeRange();
		SortingColumn sortingColumn = new SortingColumn();

		Set<String> keySet = form.keySet();
		for (String key : keySet) {

			if (key.equalsIgnoreCase("lowBoundDate")) {
				try {
					timeRange.setLowBoundDate(Long.parseLong(form.getFirst(key)));
				} catch (Exception ignored) {

				}
			}
			if (key.equalsIgnoreCase("upBoundDate")) {
				try {
					timeRange.setUpBoundDate(Long.parseLong(form.getFirst(key)));
				} catch (Exception ignored) {

				}
			}
			if (key.equalsIgnoreCase("sortingColumnName")) {
				sortingColumn.setName(form.getFirst(key));
			}
			if (key.equalsIgnoreCase("sortingColumnAsc")) {
				sortingColumn.setAsc(Boolean.parseBoolean(form.getFirst(key)));

			}
			if (key.equalsIgnoreCase("properties")) {
				testQuery.setProperties(getTestPropertiesListFromUrlVariable(form.getFirst(key)));
			}

		}

		testQuery.setTimeRange(timeRange);
		testQuery.setSortingColumn(sortingColumn);

		return testQuery;
	}

	public ScenarioQuery multipleMalueMapToScenarioQuery(MultivaluedMap<String, String> form) {

		ScenarioQuery scenarioQuery = new ScenarioQuery();
		TimeRange timeRange = new TimeRange();
		SortingColumn sortingColumn = new SortingColumn();

		Set<String> keySet = form.keySet();
		for (String key : keySet) {

			if (key.equalsIgnoreCase("lowBoundDate")) {
				try {
					timeRange.setLowBoundDate(Long.parseLong(form.getFirst(key)));
				} catch (Exception ignored) {

				}
			}
			if (key.equalsIgnoreCase("upBoundDate")) {
				try {
					timeRange.setUpBoundDate(Long.parseLong(form.getFirst(key)));
				} catch (Exception ignored) {

				}
			}
			if (key.equalsIgnoreCase("sortingColumnName")) {
				sortingColumn.setName(form.getFirst(key));
			}
			if (key.equalsIgnoreCase("sortingColumnAsc")) {
				sortingColumn.setAsc(Boolean.parseBoolean(form.getFirst(key)));

			}
			if (key.equalsIgnoreCase("properties")) {
				scenarioQuery.setProperties(getScenarioPropertiesListFromUrlVariable(form.getFirst(key)));
			}

		}

		scenarioQuery.setTimeRange(timeRange);
		scenarioQuery.setSortingColumn(sortingColumn);

		return scenarioQuery;

	}

	private Collection<ReportProperty> getScenarioPropertiesListFromUrlVariable(String propertiesVeriable) {

		Collection<ReportProperty> scenarioProperties = new ArrayList<ReportProperty>();
		String[] properties = propertiesVeriable.split(";");
		for (String property : properties) {
			String[] split = property.split("=");
			if (split.length == 2) {
				scenarioProperties.add(new ScenarioProperty(split[0], split[1]));
			} else {
				scenarioProperties.add(new ScenarioProperty(split[0], ""));
			}
		}
		if (!scenarioProperties.isEmpty()) {
			return scenarioProperties;
		} else {
			return null;
		}

	}

	private Collection<ReportProperty> getTestPropertiesListFromUrlVariable(String propertiesVariable) {

		Collection<ReportProperty> testProperties = new ArrayList<ReportProperty>();
		String[] properties = propertiesVariable.split(";");
		for (String property : properties) {
			String[] split = property.split("=");
			if (split.length == 2) {
				testProperties.add(new TestProperty(split[0], split[1]));
			} else {
				testProperties.add(new TestProperty(split[0], ""));
			}
		}
		if (!testProperties.isEmpty()) {
			return testProperties;
		} else {
			return null;
		}

	}

	public static byte[] toByteArray(File file) throws IOException {
		long length = file.length();
		byte[] array = new byte[(int) length];
		InputStream in = new FileInputStream(file);
		long offset = 0;
		while (offset < length) {
			// noinspection ResultOfMethodCallIgnored
			in.read(array, (int) offset, (int) (length - offset));
			offset += length;
		}
		in.close();
		return array;
	}

	/**
	 * This method will check if Exports directory exist<br>
	 * if exist return the Absolute path to this dir.<br>
	 * else will create the dir and return its path.
	 * 
	 * @return
	 * @throws Exception
	 *             in case the export dir could not be created.
	 */
	public String getExportedFileDirAbsPath() throws Exception {

		File f = new File("temp.txt");
		String absPath = f.getAbsolutePath();
		absPath = absPath.replace("temp.txt", "");

		String exportedFileAbsPath = absPath + "webapps" + File.separator + "exports" + File.separator;

		if (!new File(absPath + "webapps" + File.separator + "exports").exists()) {
			boolean success = (new File(absPath + "webapps" + File.separator + "exports")).mkdir();
			if (!success) {
				throw new IOException("unable to open exports dir");
			}
		}

		return exportedFileAbsPath;
	}
}

// package il.co.topq.systems.report.component.export;
//
// import il.co.topq.systems.report.beans.ScenarioBean;
// import il.co.topq.systems.report.beans.ScenarioCustomReportBean;
// import il.co.topq.systems.report.beans.TestBean;
// import il.co.topq.systems.report.beans.TestCustomReportBean;
// import il.co.topq.systems.report.common.infra.ReportLogger;
// import il.co.topq.systems.report.common.model.Scenario;
// import il.co.topq.systems.report.common.model.ScenarioCustomReport;
// import il.co.topq.systems.report.common.model.ScenarioProperty;
// import il.co.topq.systems.report.common.model.Test;
// import il.co.topq.systems.report.common.model.TestCustomReport;
// import il.co.topq.systems.report.common.model.TestProperty;
// import il.co.topq.systems.report.common.obj.ScenarioQuery;
// import il.co.topq.systems.report.common.obj.SortingColumn;
// import il.co.topq.systems.report.common.obj.TestQuery;
// import il.co.topq.systems.report.common.obj.TimeRange;
// import il.co.topq.systems.report.component.settings.ConfigurationService;
//
// import java.io.ByteArrayOutputStream;
// import java.io.File;
// import java.io.FileInputStream;
// import java.io.IOException;
// import java.io.InputStream;
// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.Set;
//
// import javax.ws.rs.Consumes;
// import javax.ws.rs.GET;
// import javax.ws.rs.POST;
// import javax.ws.rs.Path;
// import javax.ws.rs.PathParam;
// import javax.ws.rs.Produces;
// import javax.ws.rs.core.MediaType;
// import javax.ws.rs.core.MultivaluedMap;
// import javax.ws.rs.core.Response;
//
// import net.sf.jasperreports.engine.JasperExportManager;
// import net.sf.jasperreports.engine.JasperFillManager;
// import net.sf.jasperreports.engine.JasperPrint;
// import net.sf.jasperreports.engine.export.JRXlsExporter;
// import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
//
// import org.apache.log4j.Logger;
//
// @Path("/export")
// public class ExportService {
//
// private Logger log = ReportLogger.getInstance().getLogger(this.getClass());
// public static final String SCENARIO_REPORT_TEMPLATE =
// "scenario_report_template.jasper";
// public static final String TEST_REPORT_TEMPLATE =
// "test_report_template.jasper";
// public static final String TEST_CUSTOM_REPORT_TEMPLATE =
// "test_custom_report_template.jasper";
// public static final String SCENARIO_CUSTOM_REPORT_TEMPLATE =
// "scenario_custom_report_template.jasper";
// public static final String SCENARIO_DETAILED_REPORT_TEMPLATE =
// "scenario_detailed_report_template.jasper";
//
// // Templates
//
// /**
// * This method will return the template file.<br>
// * Assumption: template files exist under web-inf/resources/{file}
// *
// * @throws Exception case root pass could be found
// */
// private File getTemplateFile(String templateFileName) throws Exception {
// String rootPath = ConfigurationService.getRootPath();
// File webInfFile = new File(rootPath, "WEB-INF");
// File resourcesFile = new File(webInfFile, "resources");
// return new File(resourcesFile, templateFileName);
// }
//
// // Templates
//
// // WebService
//
// @POST
// @Path("/scenarioDetailedReport/{type}")
// @Consumes("application/x-www-form-urlencoded")
// @Produces(MediaType.APPLICATION_OCTET_STREAM)
// public Response exportScenarioDetailedReport(@PathParam("type") String type,
// MultivaluedMap<String, String> form)
// throws Exception {
//
// Integer scenarioID = getScenarioIDFromForm(form);
//
// if (type.equalsIgnoreCase("pdf")) {
// return exportScenarioDetailsPDF(scenarioID);
// } else if (type.equalsIgnoreCase("xls")) {
// return exportScenarioDetailsXLS(scenarioID);
// } else {
// return Response.ok("The requested report format does not exists")
// .header("Content-Type", "application/force-download")
// .header("Content-Disposition",
// "attachment;filename=jsystem_test_report.txt").build();
// }
// }
//
// @POST
// @Path("/test/{type}")
// @Consumes("application/x-www-form-urlencoded")
// @Produces(MediaType.APPLICATION_OCTET_STREAM)
// public Response exportTestReport(@PathParam("type") String type,
// MultivaluedMap<String, String> form)
// throws Exception {
//
// TestQuery testQuery = multipleValueMapToTestQuery(form);
//
// if (type.equalsIgnoreCase("pdf")) {
// return exportTestPdf(testQuery);
// } else if (type.equalsIgnoreCase("xls")) {
// return exportTestXLS(testQuery);
// } else {
// return Response.ok("The requested report format does not exists")
// .header("Content-Type", "application/force-download")
// .header("Content-Disposition",
// "attachment;filename=jsystem_test_report.txt").build();
// }
// }
//
// @POST
// @Path("/testCustomReport/{type}")
// @Consumes("application/x-www-form-urlencoded")
// @Produces(MediaType.APPLICATION_OCTET_STREAM)
// public Response exportTestCustomReport(@PathParam("type") String type,
// MultivaluedMap<String, String> form)
// throws Exception {
//
// TestQuery testQuery = multipleValueMapToTestQuery(form);
// Integer testCustomReportID = getTestCustomReportIDFromForm(form);
//
// if (type.equalsIgnoreCase("pdf")) {
// return exportTestCustomReportPDF(testQuery, testCustomReportID);
// } else if (type.equalsIgnoreCase("xls")) {
// return exportTestCustomReportXLS(testQuery, testCustomReportID);
// } else {
// return Response.ok("The requested report format does not exists")
// .header("Content-Type", "application/force-download")
// .header("Content-Disposition",
// "attachment;filename=jsystem_test_report.txt").build();
// }
// }
//
// @POST
// @Path("/scenarioCustomReport/{type}")
// @Consumes("application/x-www-form-urlencoded")
// @Produces(MediaType.APPLICATION_OCTET_STREAM)
// public Response exportScenarioCustomReport(@PathParam("type") String type,
// MultivaluedMap<String, String> form)
// throws Exception {
//
// ScenarioQuery scenarioQuery = multipleMalueMapToScenarioQuery(form);
// Integer scenarioCustomReportID = getScenarioCustomReportIDFromForm(form);
//
// if (type.equalsIgnoreCase("pdf")) {
// return exportScenarioCustomReportPDF(scenarioQuery, scenarioCustomReportID);
// } else if (type.equalsIgnoreCase("xls")) {
// return exportScenarioCustomReportXLS(scenarioQuery, scenarioCustomReportID);
// } else {
// return Response.ok("The requested report format does not exists")
// .header("Content-Type", "application/force-download")
// .header("Content-Disposition",
// "attachment;filename=jsystem_test_report.txt").build();
// }
// }
//
// @POST
// @Path("/scenario/{type}")
// @Consumes("application/x-www-form-urlencoded")
// @Produces(MediaType.APPLICATION_OCTET_STREAM)
// public Response exportScenarioReport(@PathParam("type") String type,
// MultivaluedMap<String, String> form)
// throws Exception {
//
// ScenarioQuery scenarioQuery = multipleMalueMapToScenarioQuery(form);
//
// if (type.equals("pdf")) {
// return exportScenarioPdf(scenarioQuery);
// } else if (type.equals("xls")) {
// return exportScenarioXLS(scenarioQuery);
// } else {
// return Response.ok("The requested report format does not exists")
// .header("Content-Type", "application/force-download")
// .header("Content-Disposition",
// "attachment;filename=jsystem_scenario_report.txt").build();
// }
//
// }
//
// @GET
// @Path("/loadScenario/{fileName}")
// @Produces(MediaType.APPLICATION_OCTET_STREAM)
// public Response loadScenarioReport(@PathParam("fileName") String fileName)
// throws Exception {
//
// String exportedFileDirAbsPath = getExportedFileDirAbsPath();
//
// File scenarioReportFile = new File(exportedFileDirAbsPath + fileName);
//
// byte[] byteArray = toByteArray(scenarioReportFile);
//
// return Response.ok(byteArray).header("Content-Type",
// "application/force-download")
// .header("Content-Disposition",
// "attachment;filename=jsystem_scenario_report.csv").build();
//
// }
//
// // WebService
//
// // Jasper Util
//
// private Response exportTestPdf(TestQuery testQuery) throws Exception {
// JasperPrint jasperPrint = getTestJasperPrint2(testQuery);
// ByteArrayOutputStream baos = new ByteArrayOutputStream();
// JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
// return Response.ok(baos.toByteArray()).header("Content-Type",
// "application/force-download")
// .header("Content-Disposition",
// "attachment;filename=jsystem_test_report.pdf").build();
// }
//
// private Response exportScenarioDetailsPDF(Integer scenarioID) throws
// Exception {
// JasperPrint jasperPrint = getScenarioDetailedJasperPrint(scenarioID);
//
// ByteArrayOutputStream baos = new ByteArrayOutputStream();
// JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
// return Response.ok(baos.toByteArray()).header("Content-Type",
// "application/force-download")
// .header("Content-Disposition",
// "attachment;filename=jsystem_scenario_details_report.pdf").build();
// }
//
// private Response exportScenarioDetailsXLS(Integer scenarioID) throws
// Exception {
//
// ByteArrayOutputStream baos = new ByteArrayOutputStream();
// JasperPrint jasperPrint = getScenarioDetailedJasperPrint(scenarioID);
// JRXlsExporter exporterXLS = new JRXlsExporter();
// exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
// exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, baos);
// exporterXLS.exportReport();
// return Response.ok(baos.toByteArray()).header("Content-Type",
// "application/force-download")
// .header("Content-Disposition",
// "attachment;filename=jsystem_scenario_details_report.csv").build();
//
// }
//
// private Response exportTestCustomReportPDF(TestQuery testQuery, Integer
// testCustomReportID) throws Exception {
// JasperPrint jasperPrint = getTestCustomReportJasperPrint(testQuery,
// testCustomReportID);
// ByteArrayOutputStream baos = new ByteArrayOutputStream();
// JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
// return Response.ok(baos.toByteArray()).header("Content-Type",
// "application/force-download")
// .header("Content-Disposition",
// "attachment;filename=jsystem_test_custom_report.pdf").build();
// }
//
// private Response exportScenarioCustomReportPDF(ScenarioQuery scenarioQuery,
// Integer scenarioCustomReportID)
// throws Exception {
// JasperPrint jasperPrint = getScenarioCustomReportJasperPrint(scenarioQuery,
// scenarioCustomReportID);
// ByteArrayOutputStream baos = new ByteArrayOutputStream();
// JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
// return Response.ok(baos.toByteArray()).header("Content-Type",
// "application/force-download")
// .header("Content-Disposition",
// "attachment;filename=jsystem_scenario_custom_report.pdf").build();
// }
//
// private Response exportTestCustomReportXLS(TestQuery testQuery, Integer
// testCustomReportID) throws Exception {
// ByteArrayOutputStream baos = new ByteArrayOutputStream();
// JasperPrint jasperPrint = getTestCustomReportJasperPrint(testQuery,
// testCustomReportID);
// JRXlsExporter exporterXLS = new JRXlsExporter();
// exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
// exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, baos);
// exporterXLS.exportReport();
// return Response.ok(baos.toByteArray()).header("Content-Type",
// "application/force-download")
// .header("Content-Disposition",
// "attachment;filename=jsystem_test_custom_report.csv").build();
// }
//
// private Response exportScenarioCustomReportXLS(ScenarioQuery scenarioQuery,
// Integer scenarioCustomReportID)
// throws Exception {
// ByteArrayOutputStream baos = new ByteArrayOutputStream();
// JasperPrint jasperPrint = getScenarioCustomReportJasperPrint(scenarioQuery,
// scenarioCustomReportID);
// JRXlsExporter exporterXLS = new JRXlsExporter();
// exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
// exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, baos);
// exporterXLS.exportReport();
// return Response.ok(baos.toByteArray()).header("Content-Type",
// "application/force-download")
// .header("Content-Disposition",
// "attachment;filename=jsystem_scenario_custom_report.csv").build();
// }
//
// private Response exportTestXLS(TestQuery testQuery) throws Exception {
// ByteArrayOutputStream baos = new ByteArrayOutputStream();
// JasperPrint jasperPrint = getTestJasperPrint2(testQuery);
// JRXlsExporter exporterXLS = new JRXlsExporter();
// exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
// exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, baos);
// exporterXLS.exportReport();
// return Response.ok(baos.toByteArray()).header("Content-Type",
// "application/force-download")
// .header("Content-Disposition",
// "attachment;filename=jsystem_test_report.csv").build();
// }
//
// private Response exportScenarioXLS(ScenarioQuery scenarioQuery) throws
// Exception {
// ByteArrayOutputStream baos = new ByteArrayOutputStream();
// JasperPrint jasperPrint = getScenarioJasperPrint(scenarioQuery);
// JRXlsExporter exporterXLS = new JRXlsExporter();
// exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
// exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, baos);
// exporterXLS.exportReport();
// return Response.ok(baos.toByteArray()).header("Content-Type",
// "application/force-download")
// .header("Content-Disposition",
// "attachment;filename=jsystem_scenario_report.csv").build();
// }
//
// private JasperPrint getScenarioDetailedJasperPrint(Integer scenarioID) throws
// Exception {
//
// ScenarioBean scenarioBean = new ScenarioBean();
// Scenario scenario = scenarioBean.getScenario(scenarioID);
//
// try {
//
// Map<String, String> map = new HashMap<String, String>();
// return
// JasperFillManager.fillReport(getTemplateFile(SCENARIO_DETAILED_REPORT_TEMPLATE).getAbsolutePath(),
// map, new ScenarioDetailedReportSource(scenario));
//
// } catch (Exception ex) {
// String connectMsg = "Could not create the report " + ex.getMessage() + " " +
// ex.getLocalizedMessage();
// throw new Exception(connectMsg);
// }
//
// }
//
// private JasperPrint getTestJasperPrint2(TestQuery testQuery) throws Exception
// {
// TestBean tsb = new TestBean();
//
// try {
//
// List<Test> tests = tsb.getTestsByFiltersWithSortingCol(testQuery);
//
// Map<String, String> map = new HashMap<String, String>();
//
// return
// JasperFillManager.fillReport(getTemplateFile(TEST_REPORT_TEMPLATE).getAbsolutePath(),
// map,
// new TestReportSource(tests));
// } catch (Exception ex) {
// log.error(ex);
// log.error(ex.getStackTrace());
// String connectMsg = "Could not create the report " + ex.getMessage() + " " +
// ex.getLocalizedMessage();
// throw new Exception(connectMsg);
// }
// }
//
// private JasperPrint getTestCustomReportJasperPrint(TestQuery testQuery,
// Integer testCustomReportID)
// throws Exception {
//
// TestBean tsb = new TestBean();
// TestCustomReportBean testCustomReportBean = new TestCustomReportBean();
//
// try {
//
// List<Test> tests = tsb.getTestsByFiltersWithSortingCol(testQuery);
//
// TestCustomReport testCustomReport =
// testCustomReportBean.getTestCustomReport(testCustomReportID);
//
// TestCustomReportSource testCustomReportSource = new
// TestCustomReportSource(tests,
// testQuery.getTestProperties(), testCustomReport);
//
// Map<String, String> map = new HashMap<String, String>();
//
// return
// JasperFillManager.fillReport(getTemplateFile(TEST_CUSTOM_REPORT_TEMPLATE).getAbsolutePath(),
// map,
// testCustomReportSource);
// } catch (Exception ex) {
// log.error(ex);
// String connectMsg = "Could not create the report " + ex.getMessage() + " " +
// ex.getLocalizedMessage();
// throw new Exception(connectMsg);
// }
//
// }
//
// private JasperPrint getScenarioCustomReportJasperPrint(ScenarioQuery
// scenarioQuery, Integer scenarioCustomReportID)
// throws Exception {
//
// ScenarioBean scenarioBean = new ScenarioBean();
// ScenarioCustomReportBean scenarioCustomReportBean = new
// ScenarioCustomReportBean();
//
// try {
//
// List<Scenario> scenarios =
// scenarioBean.getScenariosByFiltersWithSortingCol(scenarioQuery);
//
// ScenarioCustomReport scenarioCustomReport = scenarioCustomReportBean
// .getScenarioCustomReport(scenarioCustomReportID);
//
// ScenarioCustomReportSource scenarioCustomReportSource = new
// ScenarioCustomReportSource(
// scenarioCustomReport, scenarioQuery.getScenarioProperties(), scenarios);
//
// Map<String, String> map = new HashMap<String, String>();
//
// return
// JasperFillManager.fillReport(getTemplateFile(SCENARIO_CUSTOM_REPORT_TEMPLATE).getAbsolutePath(),
// map, scenarioCustomReportSource);
// } catch (Exception ex) {
// String connectMsg = "Could not create the report " + ex.getMessage() + " " +
// ex.getLocalizedMessage();
// throw new Exception(connectMsg);
// }
//
// }
//
// private Response exportScenarioPdf(ScenarioQuery scenarioQuery) throws
// Exception {
// JasperPrint jasperPrint = getScenarioJasperPrint(scenarioQuery);
// ByteArrayOutputStream baos = new ByteArrayOutputStream();
// JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
// return Response.ok(baos.toByteArray()).header("Content-Type",
// "application/force-download")
// .header("Content-Disposition",
// "attachment;filename=jsystem_test_report.pdf").build();
// }
//
// private JasperPrint getScenarioJasperPrint(ScenarioQuery scenarioQuery)
// throws Exception {
// Map<String, String> map = new HashMap<String, String>();
// ScenarioBean ssb = new ScenarioBean();
//
// List<Scenario> scenarios =
// ssb.getScenariosByFiltersWithSortingCol(scenarioQuery);
//
// return
// JasperFillManager.fillReport(getTemplateFile(SCENARIO_REPORT_TEMPLATE).getAbsolutePath(),
// map,
// new ScenarioReportSource(scenarios));
// }
//
// // Jasper Util
//
// private Integer getTestCustomReportIDFromForm(MultivaluedMap<String, String>
// form) throws Exception {
//
// Integer testCustomReportID;
//
// try {
// testCustomReportID = Integer.parseInt(form.getFirst("testCustomReportID"));
// } catch (Exception e) {
// throw new Exception(e.getMessage() +
// "failed to parse test custom report id");
// }
//
// return testCustomReportID;
//
// }
//
// /**
// * This method will retrieve the scenario ID from web form<br>
// *
// * @param form
// * @return Integer scenario ID;
// * @throws Exception scenario ID not found in form.
// */
// private Integer getScenarioIDFromForm(MultivaluedMap<String, String> form)
// throws Exception {
// Integer scenarioID;
//
// try {
// scenarioID = Integer.parseInt(form.getFirst("scenarioID"));
// } catch (Exception e) {
// throw new Exception(e.getMessage() + "failed to parse scenario id");
// }
//
// return scenarioID;
// }
//
// private Integer getScenarioCustomReportIDFromForm(MultivaluedMap<String,
// String> form) throws Exception {
//
// Integer scenarioCustomReportID;
//
// try {
// scenarioCustomReportID =
// Integer.parseInt(form.getFirst("scenarioCustomReportID"));
// } catch (Exception e) {
// throw new Exception(e.getMessage() +
// "failed to parse scenario custom report id");
// }
//
// return scenarioCustomReportID;
// }
//
// private TestQuery multipleValueMapToTestQuery(MultivaluedMap<String, String>
// form) {
//
// TestQuery testQuery = new TestQuery();
// TimeRange timeRange = new TimeRange();
// SortingColumn sortingColumn = new SortingColumn();
//
// Set<String> keySet = form.keySet();
// for (String key : keySet) {
//
// if (key.equalsIgnoreCase("lowBoundDate")) {
// try {
// timeRange.setLowBoundDate(Long.parseLong(form.getFirst(key)));
// } catch (Exception ignored) {
//
// }
// }
// if (key.equalsIgnoreCase("upBoundDate")) {
// try {
// timeRange.setUpBoundDate(Long.parseLong(form.getFirst(key)));
// } catch (Exception ignored) {
//
// }
// }
// if (key.equalsIgnoreCase("sortingColumnName")) {
// sortingColumn.setName(form.getFirst(key));
// }
// if (key.equalsIgnoreCase("sortingColumnAsc")) {
// sortingColumn.setAsc(Boolean.parseBoolean(form.getFirst(key)));
//
// }
// if (key.equalsIgnoreCase("properties")) {
// testQuery.setTestProperties(getTestPropertiesListFromUrlVariable(form.getFirst(key)));
// }
//
// }
//
// testQuery.setTimeRange(timeRange);
// testQuery.setSortingColumn(sortingColumn);
//
// return testQuery;
// }
//
// private ScenarioQuery multipleMalueMapToScenarioQuery(MultivaluedMap<String,
// String> form) {
//
// ScenarioQuery scenarioQuery = new ScenarioQuery();
// TimeRange timeRange = new TimeRange();
// SortingColumn sortingColumn = new SortingColumn();
//
// Set<String> keySet = form.keySet();
// for (String key : keySet) {
//
// if (key.equalsIgnoreCase("lowBoundDate")) {
// try {
// timeRange.setLowBoundDate(Long.parseLong(form.getFirst(key)));
// } catch (Exception ignored) {
//
// }
// }
// if (key.equalsIgnoreCase("upBoundDate")) {
// try {
// timeRange.setUpBoundDate(Long.parseLong(form.getFirst(key)));
// } catch (Exception ignored) {
//
// }
// }
// if (key.equalsIgnoreCase("sortingColumnName")) {
// sortingColumn.setName(form.getFirst(key));
// }
// if (key.equalsIgnoreCase("sortingColumnAsc")) {
// sortingColumn.setAsc(Boolean.parseBoolean(form.getFirst(key)));
//
// }
// if (key.equalsIgnoreCase("properties")) {
// scenarioQuery.setScenarioProperties(getScenarioPropertiesListFromUrlVariable(form.getFirst(key)));
// }
//
// }
//
// scenarioQuery.setTimeRange(timeRange);
// scenarioQuery.setSortingColumn(sortingColumn);
//
// return scenarioQuery;
//
// }
//
// private List<ScenarioProperty>
// getScenarioPropertiesListFromUrlVariable(String propertiesVeriable) {
//
// List<ScenarioProperty> scenarioProperties = new
// ArrayList<ScenarioProperty>();
// String[] properties = propertiesVeriable.split(";");
// for (String property : properties) {
// String[] split = property.split("=");
// if (split.length == 2) {
// scenarioProperties.add(new ScenarioProperty(split[0], split[1]));
// } else {
// scenarioProperties.add(new ScenarioProperty(split[0], ""));
// }
// }
// if (!scenarioProperties.isEmpty()) {
// return scenarioProperties;
// } else {
// return null;
// }
//
// }
//
// private List<TestProperty> getTestPropertiesListFromUrlVariable(String
// propertiesVariable) {
//
// List<TestProperty> testProperties = new ArrayList<TestProperty>();
// String[] properties = propertiesVariable.split(";");
// for (String property : properties) {
// String[] split = property.split("=");
// if (split.length == 2) {
// testProperties.add(new TestProperty(split[0], split[1]));
// } else {
// testProperties.add(new TestProperty(split[0], ""));
// }
// }
// if (!testProperties.isEmpty()) {
// return testProperties;
// } else {
// return null;
// }
//
// }
//
// public static byte[] toByteArray(File file) throws IOException {
// long length = file.length();
// byte[] array = new byte[(int) length];
// InputStream in = new FileInputStream(file);
// long offset = 0;
// while (offset < length) {
// //noinspection ResultOfMethodCallIgnored
// in.read(array, (int) offset, (int) (length - offset));
// offset += length;
// }
// in.close();
// return array;
// }
//
// /**
// * This method will check if Exports directory exist<br>
// * if exist return the Absolute path to this dir.<br>
// * else will create the dir and return its path.
// *
// * @return
// * @throws Exception in case the export dir could not be created.
// */
// public String getExportedFileDirAbsPath() throws Exception {
//
// File f = new File("temp.txt");
// String absPath = f.getAbsolutePath();
// absPath = absPath.replace("temp.txt", "");
//
// String exportedFileAbsPath = absPath + "webapps" + File.separator + "exports"
// + File.separator;
//
// if (!new File(absPath + "webapps" + File.separator + "exports").exists()) {
// boolean success = (new File(absPath + "webapps" + File.separator +
// "exports")).mkdir();
// if (!success) {
// throw new IOException("unable to open exports dir");
// }
// }
//
// return exportedFileAbsPath;
// }
//
// }
