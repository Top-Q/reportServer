package il.co.topq.systems.report.component.service;

import il.co.topq.systems.report.common.infra.log.ReportLogger;
import il.co.topq.systems.report.common.jaxbWrappers.PropertyValuesWrapperList;
import il.co.topq.systems.report.common.jaxbWrappers.ScenarioList;
import il.co.topq.systems.report.common.jaxbWrappers.ScenarioStatistics;
import il.co.topq.systems.report.common.jaxbWrappers.ScenarioStatisticsList;
import il.co.topq.systems.report.common.jaxbWrappers.TestList;
import il.co.topq.systems.report.common.jaxbWrappers.TestStatistics;
import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.model.Scenario;
import il.co.topq.systems.report.common.model.ScenarioProperty;
import il.co.topq.systems.report.common.model.Test;
import il.co.topq.systems.report.common.obj.ScenarioComparator;
import il.co.topq.systems.report.common.obj.ScenarioQuery;
import il.co.topq.systems.report.common.obj.TestQuery;
import il.co.topq.systems.report.component.utils.FileUtils;
import il.co.topq.systems.report.component.utils.converters.XmlToScenarioConverter;
import il.co.topq.systems.report.service.infra.PropertyService;
import il.co.topq.systems.report.service.infra.ScenarioService;
import il.co.topq.systems.report.service.infra.TestService;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/scenario")
public class ScenarioWebService {

	private Logger log = ReportLogger.getInstance().getLogger(this.getClass());

	@Autowired
	private ScenarioService scenarioService;

	@Autowired
	private TestService testService;

	@Autowired
	private PropertyService<ReportProperty> scenarioPropertyService;

	@Context
	private static ServletContext servletContext;

	/**
	 * This method gets Scenario id and return the matching Scenario from
	 * Database. URL: http://host:port/report-service/report/scenario/scenarioId
	 * Method: GET
	 * 
	 * @param scenarioId
	 *            -
	 * @return Scenario matching the scenario ID; see the <a href="{@docRoot}
	 *         /doc-files/scenario.xml">Produced XML file</a>.
	 * @throws Exception
	 *             -
	 */
	@RequestMapping(value = "/{scenarioId}", method = RequestMethod.GET)
	public @ResponseBody
	Scenario getScenario(@PathVariable("scenarioId") Integer scenarioId)
			throws Exception {

		log.info("in get scenario web service");
		Scenario scenario;
		try {
			scenario = scenarioService.get(scenarioId);
			return scenario;

		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	/**
	 * This method gets a ScenarioQuery and return the size of its resultSet;
	 * URL: http://host:port/report-service/report/scenario/querySize Method:
	 * POST
	 * 
	 * @param scenarioQuery
	 *            -
	 * @return Integer - size of result set according to ScenarioQuery received
	 *         as parameter. see the <a href="{@docRoot}
	 *         /doc-files/integer.xml">Produced XML file</a>.
	 */
	@RequestMapping(value = "/querySize", method = RequestMethod.POST)
	public void getSizeOfScenarioQueryByFilter(ServletResponse response,
			@RequestBody ScenarioQuery scenarioQuery) throws Exception {

		log.info("in get size of scenario query by filter web service");
		Integer querySize;
		try {
			querySize = scenarioService.getSizeOfScenarioQuery(scenarioQuery);
			response.getWriter().print(querySize);
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	/**
	 * This method gets a ScenarioQuery and return List of Scenario from
	 * Database according to ScenarioQuery.
	 * URL:http://host:port/report-service/report/scenario Method: POST
	 * 
	 * @param scenarioQuery
	 *            -
	 * @return List of Test answering the ScenarioQuery. see the <a
	 *         href="{@docRoot} /doc-files/listOfScenario.xml">Consumed XML
	 *         file</a>.
	 */
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	ScenarioList getScenariosByFilter(@RequestBody ScenarioQuery scenarioQuery)
			throws Exception {
		log.info("in get scenarios by filter web service");
		try {
			List<Scenario> scenarioList = scenarioService
					.getScenariosByFiltersWithSortingCol(scenarioQuery);

			for (Scenario scenario : scenarioList) {
				/**
				 * VERY IMPORTANT --> DO NOT REMOVE UNLESS UNDERSTANDING THE
				 * LOGIC!!! making the tests transient as it is not needed.
				 * Tests are fetched in a separate chunk query. The improvement
				 * in sending time of this HTTP request is big.
				 */
				scenario.setTests(null);
			}

			return new ScenarioList(scenarioList);
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	/**
	 * This method will get a Scenario id (number of IDS may be concatenate
	 * using ',' delimiter<br>
	 * , if parameter == "all" will delete all scenarios from DB.<br>
	 * and deletes the matching Scenarios from Database<br>
	 * Method will delete the scenario log from file system if exist.<br>
	 * URL:http://host:port/report-service/report/scenario/delete/scenarioId
	 * Method: GET
	 * 
	 * @param scenarioId
	 *            (list of IDS using delimiter ',' | "all" - stating delete all.
	 * @return Boolean representing the success of the operation. see the <a
	 *         href="{@docRoot} /doc-files/boolean.xml">Consumed XML file</a>.
	 */
	@Secured(value = "ROLE_DELETE_SCENARIO")
	@RequestMapping(value = "/delete/{scenarioId}", method = RequestMethod.GET)
	public void deleteScenario(ServletResponse response,
			ServletRequest request,
			@PathVariable("scenarioId") String scenarioIds) throws Exception {

		log.info("in delete scenario web service");
		try {
			String[] scenarioIDs = scenarioIds.split(",");
			for (String scenarioId : scenarioIDs) {
				Scenario scenario = scenarioService.get(Integer
						.parseInt(scenarioId));
				if (scenario != null) {
					String htmlDir = scenario.getHtmlDir();
					scenarioService.delete(Integer.parseInt(scenarioId));
					deleteScenarioLogDirectory(htmlDir);
				} else {
					log.error("Failed to retrieve scenarios with id: "
							+ scenarioId);
				}
			}
			response.getWriter().print(true);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
	}

	private void deleteScenarioLogDirectory(String logDirectory) {
		try {
			log.info("Attempt to delete scenario log directory: "
					+ logDirectory);
			File applicationResultsFile = new File(getResultsPath());
			if (applicationResultsFile.exists()) {
				if (logDirectory != null) {
					String[] logDirSplit = logDirectory.split("results");
					if (logDirSplit.length > 1) {
						String logDirIndexFile = logDirSplit[1];
						String logDir = logDirIndexFile.split("index.html")[0];
						if (!FileUtils.removeDirectory(new File(
								applicationResultsFile.getAbsoluteFile()
										+ logDir))) {
							log.error("Log dir could not be deleted");
						} else {
							log.info("Deleted successfully");
						}
					} else {
						log.error("Log dir could not be deleted, failed to construct path correctly");
					}
				}
			}
		} catch (Exception e) {
			log.error("Failed to delete scenario log directory: "
					+ logDirectory);
		}
	}

	/**
	 * This method will check if results folder exists.
	 * 
	 * @return
	 */
	private boolean isLogFolderExist() {
		try {
			return new File(getResultsPath()).exists();
		} catch (Exception e) {
			return false;
		}
	}

	private String getResultsPath() throws Exception {
		File configFile = new File(servletContext.getRealPath(""), "");

		if (new File(configFile + File.separator + "results").exists()) {
			return configFile + File.separator + "results" + File.separator;
		} else {
			throw new Exception("results path does not exist in webapps scope");
		}
	}

	@RequestMapping(value = "/deleteOrphan/", method = RequestMethod.GET)
	public void deleteOrphanScenarioProperties(ServletResponse response)
			throws Exception {
		log.info("In Delete Orphan Scenario Properties Web Service");
		try {
			scenarioPropertyService.deleteOrphanProperties();
			response.getWriter().print(true);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * This method will delete all result that match query.<br>
	 * It will set chunk inside query to be null, as chunk is meaningless for
	 * this feature.<br>
	 * 
	 * @param scenarioQuery
	 * @return True if all the returned results were deleted, False otherwise.
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete/", method = RequestMethod.POST)
	public void deleteScenarios(ServletResponse response,
			@RequestBody ScenarioQuery scenarioQuery) throws Exception {

		log.info("In DeleteScenarioBYQuery WebService");
		try {
			scenarioQuery.setChunk(null);
			List<Scenario> scenarios = scenarioService
					.getScenariosByFiltersWithSortingCol(scenarioQuery);
			for (Scenario scenario : scenarios) {
				String htmlDir = scenario.getHtmlDir();
				log.info("deleting scenario: " + scenario.getId());
				scenarioService.delete(scenario.getId());
				deleteScenarioLogDirectory(htmlDir);
			}
			response.getWriter().print(true);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * This method creates a scenario in DB as per scenario received as
	 * parameter. URL:http://host:port/report-service/report/scenario/create
	 * Method: POST see the <a href="{@docRoot}
	 * /doc-files/scenario.xml">Consumed XML file</a>.
	 * 
	 * @param scenario
	 *            -
	 * @return Integer representing the created scenario's ID in DB. see the <a
	 *         href="{@docRoot} /doc-files/integer.xml">Produced XML file</a>.
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public void createScenario(ServletResponse response,
			@RequestBody Scenario scenario) throws Exception {
		log.info("in create scenario web service");

		/**
		 * This is needed in order to make the connection between the tests and
		 * the scenario as scenario is xml transient and data was lost with
		 * operation of the rest service.
		 */
		Collection<Test> scenarioTests = scenario.getTests();

		for (Test test : scenarioTests) {
			test.setScenario(scenario);
		}

		Integer scenarioID = null;

		try {
			Scenario createdScenario = scenarioService.create(scenario);
			if (createdScenario != null) {
				scenarioID = createdScenario.getId();
			}
			log.info("Scenario created, ID: " + scenarioID);
			response.getWriter().print(scenario.getId());
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	/**
	 * This method will be the called from the JSystem client to check if the
	 * right data was entered as setting: application url application port
	 * 
	 * @return true stating setting were correct if reached; else will catch an
	 *         exception;
	 * @throws IOException
	 */
	@RequestMapping(value = "/connected", method = RequestMethod.GET)
	public void isReportServerOn(ServletResponse response) throws IOException {
		response.getWriter().print(true);
	}

	/**
	 * This method gets a Scenario id and return all of its tests.
	 * URL:http://host:port/report-service/report/scenario/tests/{scenarioId}
	 * Method: GET
	 * 
	 * @param scenarioId
	 *            The scenario's ID, Chunk start ind, Chunk length
	 * @return A List of tests for a specific scenario.<br>
	 *         An empty list is return if no test exists for the specific
	 *         scenarioId. TODO: create an xml represents the return value; see
	 *         the <a href="{@docRoot} /doc-files/integer.xml">Produced XML
	 *         file</a>.
	 */
	@RequestMapping(value = "tests/{scenarioId}", method = RequestMethod.POST)
	public @ResponseBody
	TestList getTestsSetByScenarioID(
			@PathVariable("scenarioId") Integer scenarioId,
			@RequestBody TestQuery testQuery) throws Exception {

		log.info("in get tests set by scenario ID web service");
		List<Test> testsList;
		try {
			testsList = testService.getTestsByScenarioId(scenarioId, testQuery);
			return new TestList(testsList);
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	/**
	 * This method counts all the Tests in the DB.
	 * URL:http://host:port/report-service/report/scenario/count Method: GET
	 * 
	 * @return Long - number of Tests in DB see the <a href="{@docRoot}
	 *         /doc-files/long.xml">Produced XML file</a>.
	 */
	@RequestMapping(value = "/count", method = RequestMethod.POST)
	public void countScenarios(ServletResponse response) throws Exception {
		log.info("in count scenarios web service");
		try {
			Long numOfScenarios = scenarioService.countAll(null);
			response.getWriter().print(numOfScenarios);
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	/**
	 * This method gets a ScenarioQuery and return the size of its resultSet;
	 * URL: http://host:port/report-service/report/scenario/querySize Method:
	 * POST
	 * 
	 * @param scenarioId
	 *            -
	 * @return Integer - size of result set according to ScenarioQuery received
	 *         as parameter. see the <a href="{@docRoot}
	 *         /doc-files/integer.xml">Produced XML file</a>.
	 */
	@RequestMapping(value = "scenarioTestsSize/{scenarioId}", method = RequestMethod.POST)
	public void getSizeOfScenarioTests(ServletResponse response,
			@PathVariable("scenarioId") Integer scenarioId) throws Exception {

		log.info("in get size of scenario Tests web service");
		Integer querySize;
		try {
			querySize = scenarioService
					.getSizeOfTestsSetByScenarioID(scenarioId);
			response.getWriter().print(querySize);
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	@RequestMapping(value = "/compareScenarios/{comparedScenarios}", method = RequestMethod.POST)
	public @ResponseBody
	ScenarioComparator compareScenarios(
			@PathVariable("comparedScenarios") String comparedScenarios)
			throws Exception {

		log.info("in compare scenarios web service");

		try {
			String[] split = comparedScenarios.split(",");

			Integer[] comparedScenariosID = new Integer[split.length];

			for (int i = 0; i < split.length; i++) {
				comparedScenariosID[i] = Integer.parseInt(split[i]);
			}

			return scenarioService.compareScenarios(comparedScenariosID);

		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	@RequestMapping(value = "/propertyValues/{propertyKeys}", method = RequestMethod.GET)
	public @ResponseBody
	PropertyValuesWrapperList getScenarioPropertiesValues(
			@PathVariable("propertyKeys") String propertyKeys) throws Exception {

		log.info("in get scenario properties values web service");

		Collection<ReportProperty> scenarioPropList = new HashSet<ReportProperty>();
		String[] keys = propertyKeys.split(",");
		for (String key : keys) {
			scenarioPropList.add(new ScenarioProperty(key, ""));
		}
		try {
			return new PropertyValuesWrapperList(
					scenarioPropertyService
							.getAllPropertiesValues(scenarioPropList));

		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	/**
	 * This method gets a ScenarioQuery and return the count of tests from all
	 * scenario matching the query.<br>
	 * for each scenario in DB will count the number of tests run and will
	 * accumulate the sum of all tests from all scenarios<br>
	 * URL:http://host:port/report-service/report/scenario Method: POST
	 * 
	 * @param scenarioQuery
	 *            -
	 * @return Integer number of Test answering the ScenarioQuery. see the <a
	 *         href="{@docRoot} /doc-files/listOfScenario.xml">Consumed XML
	 *         file</a>.
	 */
	@RequestMapping(value = "/countScenarioTests", method = RequestMethod.POST)
	public @ResponseBody
	TestStatistics countScenarioTests(@RequestBody ScenarioQuery scenarioQuery)
			throws Exception {

		log.info("in count scenario tests web service");

		Integer total = 0;
		Integer passed = 0;
		Integer failed = 0;
		Integer warned = 0;

		List<Scenario> scenarioList;
		try {
			scenarioList = scenarioService
					.getScenariosByFiltersWithSortingCol(scenarioQuery);
			for (Scenario scenario : scenarioList) {
				total += scenario.getRunTest();
				passed += scenario.getSuccessTests();
				failed += scenario.getFailTests();
				warned += scenario.getWarningTests();
			}
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
		return new TestStatistics(failed, passed, warned, total);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void updateScenario(ServletResponse response,
			@RequestBody Scenario scenario) throws Exception {
		log.info("in update scenario web service");
		try {
			scenarioService.update(scenario);
			response.getWriter().print(true);
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	/**
	 * This method gets a ScenarioQuery and return the scenarioStatistics object
	 * matching the query.<br>
	 * for each scenario type (unique by scenario name) in DB will count the
	 * number of tests run and will accumulate the sum of all tests from all
	 * scenario<br>
	 * URL:http://host:port/report-service/report/scenario/ scenarioStatistics
	 * Method: POST
	 * 
	 * @return List of ScenarioStatistics wrapped in Response object.<br>
	 *         see the <a href="{@docRoot}
	 *         /doc-files/listOfScenario.xml">Consumed XML file</a>.
	 */
	@RequestMapping(value = "/scenarioStatistics", method = RequestMethod.POST)
	public @ResponseBody
	ScenarioStatisticsList getScenarioTypeStatistics(
			@RequestBody ScenarioQuery scenarioQuery) throws Exception {

		log.info("in get scenario type statistics web service");
		List<Scenario> scenarioList;
		List<ScenarioStatistics> scenarioStatisticsList = new ArrayList<ScenarioStatistics>();

		try {
			scenarioList = scenarioService
					.getScenariosByFiltersWithSortingCol(scenarioQuery);
			for (Scenario scenario : scenarioList) {
				ScenarioStatistics scenarioStatistics = new ScenarioStatistics(
						scenario.getScenarioName(), new TestStatistics(
								scenario.getFailTests(),
								scenario.getSuccessTests(),
								scenario.getWarningTests(),
								scenario.getRunTest()));
				if (!scenarioStatisticsList.contains(scenarioStatistics)) {
					scenarioStatisticsList.add(scenarioStatistics);
				} else {
					ScenarioStatistics scenarioStatisticsToUpdate = scenarioStatisticsList
							.get(scenarioStatisticsList
									.indexOf(scenarioStatistics));
					scenarioStatisticsToUpdate
							.updateTestStatistics(scenarioStatistics
									.getTestStatistics());
				}
			}
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
		return new ScenarioStatisticsList(scenarioStatisticsList);
	}

	@RequestMapping(value = "/reportXML/", method = RequestMethod.POST)
	public void createScenarioFromReportXML(ServletResponse responses,
			@RequestBody byte[] arr) throws Exception {
		log.info("in create scenario from report xml web service");
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				arr);
		try {
			Scenario scenario = XmlToScenarioConverter
					.convert(byteArrayInputStream);
			Scenario scenarioFromDB = scenarioService.create(scenario);
			if (scenario != null) {
				responses.getWriter().print(scenarioFromDB.getId());
			}
		} catch (Exception e) {
			log.error(e);
			log.error(e.getLocalizedMessage());
			throw (e);
		}
	}

	@RequestMapping(value = "/setLogDir/", method = RequestMethod.GET)
	public void setScenarioLogDir(ServletResponse response,
			@RequestParam("logDir") String logDir,
			@RequestParam("scenarioID") Integer scenarioID) throws Exception {
		try {
			log.info("in set scenario log dir web service");
			Scenario scenario = scenarioService.get(scenarioID);
			scenario.setHtmlDir(logDir);
			scenarioService.update(scenario);
			response.getWriter().print(true);
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	// @GET
	// @Produces(MediaType.APPLICATION_XML)
	// @Path("/getGridConfiguration/{typeStr}/{id}")
	// public GridConfiguration getGridConfiguration(
	// @PathParam("typeStr") String typeStr, @PathParam("id") Integer id)
	// throws Exception {
	// SettingsBean settingsService = new SettingsBean();
	// try {
	// log.info("in getGridConfiguration web service");
	// Type type = Type.valueOf(typeStr);
	// log.info("type rcvd: " + type.toString());
	// log.info("id rcvd: " + id);
	//
	// GridConfiguration gridConfiguration = settingsService
	// .getGridConfiguration(type, id);
	// return gridConfiguration;
	// } catch (Exception e) {
	// log.error(e);
	// e.printStackTrace();
	// throw e;
	// }
	// }

	// @GET
	// @Path("/testMethod/{scenarioId}")
	// @Produces(MediaType.APPLICATION_XML)
	// public Scenario getScenario2(@PathParam("scenarioId") Integer scenarioId)
	// throws Exception {
	//
	// log.info("AVI");
	// ResponseBuilder rb = new ResponseBuilderImpl();
	// rb.status(Status.FORBIDDEN);
	// rb.entity(URLResponse.ERROR_MESSAGE + URLResponse.NO_PERMISSIONS);
	// Response response = rb.build();
	// throw new WebApplicationException(response);
	// }
	//
	// @Path("checked/mymappedexception")
	// @GET
	// @Produces("text/plain")
	// public String checkedMyMappedException() throws UserException {
	// System.out.println("in checkedMyMappedException method ");
	// throw new UserException();
	// }

	// @Provider
	// public static class UserExceptionMapper implements
	// ExceptionMapper<UserException> {
	// public Response toResponse(UserException exception) {
	// ResponseBuilder rb = new ResponseBuilderImpl();
	// rb.status(Status.FORBIDDEN);
	// rb.entity("error no permission");
	// Response response = rb.build();
	// return response;
	// // return Response.serverError().entity("Jersey mapped exception: " +
	// // exception.getClass().getName()).build();
	// }
	// }
}
