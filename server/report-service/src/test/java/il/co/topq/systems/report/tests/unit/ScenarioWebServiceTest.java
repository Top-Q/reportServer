package il.co.topq.systems.report.tests.unit;

import static org.junit.Assert.assertEquals;
import il.co.topq.systems.report.common.infra.log.ReportLogger;
import il.co.topq.systems.report.common.jaxbWrappers.ScenarioList;
import il.co.topq.systems.report.common.jaxbWrappers.ScenarioStatistics;
import il.co.topq.systems.report.common.jaxbWrappers.TestList;
import il.co.topq.systems.report.common.jaxbWrappers.TestStatistics;
import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.model.Scenario;
import il.co.topq.systems.report.common.model.ScenarioProperty;
import il.co.topq.systems.report.common.model.Test;
import il.co.topq.systems.report.common.obj.Chunk;
import il.co.topq.systems.report.common.obj.ScenarioQuery;
import il.co.topq.systems.report.common.obj.TestQuery;
import il.co.topq.systems.report.common.obj.TimeRange;
import il.co.topq.systems.report.component.utils.URLParts;
import il.co.topq.systems.report.service.infra.ScenarioService;
import il.co.topq.systems.report.tests.infra.WebserviceBaseTest;
import il.co.topq.systems.report.utils.RandomData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Eran.Golan & Tomer.Gafner
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/applicationContext.xml" })
public class ScenarioWebServiceTest extends WebserviceBaseTest {

	private Logger log = ReportLogger.getInstance().getLogger(this.getClass());

	@Autowired
	private ScenarioService scenarioService;

	/*
	 * TODO: IMPL
	 */
	public void createScenarioByObject() {

	}

	/*
	 * TODO: put in resources jsystem xml file.
	 */
	@org.junit.Test
	public void createScenarioFromXmlFile() throws IOException {
		File f = new File(
				"C:\\Users\\eran_g\\Downloads\\current\\reports.0.xml");
		byte[] scenarioByteArr = FileUtils.readFileToByteArray(f);
		getWebResource(URLParts.CREATE_SCENARIO_FROM_XML).post(String.class,
				new byte[1024]);
		// Client c = Client.create();
		// WebResource r =
		// c.resource("http://localhost:8080/report-service/report/scenario/reportXML/");
		//
		// try {
		// ClientResponse clientResponse =
		// r.accept(MediaType.APPLICATION_XML).type(MediaType.APPLICATION_XML)
		// .post(ClientResponse.class, new byte[1024]);
		//
		// if (clientResponse == null) {
		// } else {
		// }
		//
		// } catch (Exception ex) {
		// log.error(ex);
		// }
	}

	// TESTED
	@org.junit.Test
	public void deleteScenario() throws Exception {
		Scenario scenario = scenarioService.create(RandomData
				.getScenarioWithTests(3));
		Assert.assertNotNull(scenario);
		Boolean isDeleted = Boolean.valueOf(getWebResource(
				URLParts.DELETE_SCENARIO_URL + scenario.getId()).get(
				String.class));
		Assert.assertTrue(isDeleted);
	}

	// TESTED
	@org.junit.Test
	public void getScenarioByID() throws Exception {

		Scenario scenario = scenarioService.create(RandomData
				.getScenarioWithTests(3));
		Assert.assertNotNull(scenario);
		Scenario scenarioFromDB = getWebResource(
				URLParts.GET_SCENARIO_URL + scenario.getId()).get(
				Scenario.class);
		scenarioService.delete(scenarioFromDB.getId());
	}

	// TESTED
	@org.junit.Test
	public void getTestsByScenarioID() throws Exception {

		Integer chunkLength = 10;
		Chunk chunk = new Chunk();
		chunk.setStartIndex(0);
		chunk.setLength(chunkLength);
		int numOfTests = 3;
		TestQuery testQuery = new TestQuery();
		testQuery.setChunk(chunk);
		Scenario scenario = scenarioService.create(RandomData
				.getScenarioWithTests(numOfTests));
		Assert.assertNotNull(scenario);
		Integer scenarioTestsSize = Integer.valueOf(getWebResource(
				URLParts.SIZE_OF_SCENARIO_TESTS_URL + scenario.getId()).post(
				String.class));

		List<Test> tests = getWebResource(
				URLParts.SCENARIO_TESTS_URL + scenario.getId()).post(
				TestList.class, testQuery).getTests();
		Assert.assertTrue("expecting " + numOfTests + " in scenario",
				tests.size() == 3);
		Assert.assertTrue("expecting " + numOfTests + " in scenario",
				scenarioTestsSize == 3);
		scenarioService.delete(scenario.getId());
	}

	// TODO: need to work on this test's logic. NOT TESTED
	@org.junit.Test
	public void getScenariosByFilters() throws Exception {

		Integer numberOfScenarios = 50;
		Integer chunkLength = 10;
		List<Scenario> scenarios = new ArrayList<Scenario>();
		int counter = 0;

		// will be used for created scenario id
		List<Integer> ids = new ArrayList<Integer>();

		// Creates {numberOfScenarios}
		for (int i = 0; i < numberOfScenarios; i++) {
			ids.add(scenarioService.create(RandomData.getScenarioWithTests(3))
					.getId());
		}

		// Create the scenarioQuery: properties, timeRange
		TimeRange timeRange = RandomData.getTimeRange(2004, 1, 1, 2010, 1, 1);
		timeRange.setUpBoundDate(System.currentTimeMillis());
		ScenarioQuery scenarioQuery = new ScenarioQuery();
		scenarioQuery.setTimeRange(timeRange);

		// Gets the size of this query;
		Integer sizeOfScenarioQuery = Integer.valueOf(getWebResource(
				URLParts.SIZE_OF_SCENARIO_QUERY_URL).post(String.class,
				scenarioQuery));

		// Gets the scenarioList
		for (int i = 0; i <= (sizeOfScenarioQuery / chunkLength); i++) {
			Chunk chunk = new Chunk(i * chunkLength, chunkLength);
			scenarioQuery.setChunk(chunk);
			List<Scenario> tempList = getWebResource(URLParts.GET_SCENARIO_URL)
					.post(ScenarioList.class, scenarioQuery).getScenarios();
			counter += tempList.size();
		}

		Assert.assertEquals("expecting " + +sizeOfScenarioQuery + " scenarios",
				sizeOfScenarioQuery.intValue(), counter);

		// Assertion for each scenario exist in list to verify date and
		// properties
		for (Scenario scenario : scenarios) {

			// Asserting the timeRange in scenario
			long scenarioStartTime = scenario.getStartTime();
			assertEquals(
					"scenario start time not in query timeRange",
					true,
					((scenarioStartTime <= timeRange.getUpBoundDate()) && (scenarioStartTime >= timeRange
							.getLowBoundDate())));
		}

		// Deletes all the scenarios created from the DB.
		for (Integer id : ids) {
			scenarioService.delete(id);
		}
	}

	// TODO: impl and check logic
	// NOT TESTED
	@org.junit.Test
	public void executionStatistics() {
		List<Scenario> scenarioList;
		List<ScenarioStatistics> scenarioStatisticsList = new ArrayList<ScenarioStatistics>();
		ScenarioQuery scenarioQuery = new ScenarioQuery();
		Collection<ReportProperty> scenarioPropertyList = new ArrayList<ReportProperty>();
		scenarioPropertyList.add(new ScenarioProperty("Version", ""));
		scenarioQuery.setProperties(scenarioPropertyList);

		scenarioList = scenarioService
				.getScenariosByFiltersWithSortingCol(scenarioQuery);
		for (Scenario scenario : scenarioList) {
			ScenarioStatistics scenarioStatistics = new ScenarioStatistics(
					scenario.getScenarioName(), new TestStatistics(
							scenario.getFailTests(),
							scenario.getSuccessTests(),
							scenario.getWarningTests(), scenario.getRunTest()));
			if (!scenarioStatisticsList.contains(scenarioStatistics)) {
				scenarioStatisticsList.add(scenarioStatistics);
			} else {
				ScenarioStatistics scenarioStatisticsToUpdate = scenarioStatisticsList
						.get(scenarioStatisticsList.indexOf(scenarioStatistics));
				scenarioStatisticsToUpdate
						.updateTestStatistics(scenarioStatistics
								.getTestStatistics());
				log.info("");
			}
		}
	}

	// TODO: Delete
	@org.junit.Test
	public void getScenariosByFiltersWithProperties() throws Exception {

		Integer numberOfScenarios = 50;
		Integer chunkLength = 10;
		List<Scenario> scenarios = new ArrayList<Scenario>();
		int counter = 0;

		// will be used for created scenario id
		List<Integer> ids = new ArrayList<Integer>();

		// Creates {numberOfScenarios}
		for (int i = 0; i < numberOfScenarios; i++) {
			ids.add(scenarioService.create(RandomData.getScenarioWithTests(3))
					.getId());
		}

		// Create the scenarioQuery: properties, timeRange
		TimeRange timeRange = RandomData.getTimeRange(2004, 1, 1, 2010, 1, 1);
		timeRange.setUpBoundDate(System.currentTimeMillis());
		ScenarioQuery scenarioQuery = new ScenarioQuery();
		scenarioQuery.setTimeRange(timeRange);
		scenarioQuery.setProperties(RandomData
				.getRandomScenarioCustomReportProperties(4));

		// Gets the size of this query;
		Integer sizeOfScenarioQuery = Integer.valueOf(getWebResource(
				URLParts.SIZE_OF_SCENARIO_QUERY_URL).post(String.class,
				scenarioQuery));

		// Gets the scenarioList
		for (int i = 0; i <= (sizeOfScenarioQuery / chunkLength); i++) {
			Chunk chunk = new Chunk(i * chunkLength, chunkLength);
			scenarioQuery.setChunk(chunk);
			List<Scenario> tempList = getWebResource(URLParts.GET_SCENARIO_URL)
					.post(ScenarioList.class, scenarioQuery).getScenarios();
			counter += tempList.size();
		}

		Assert.assertEquals("expecting " + +sizeOfScenarioQuery + " scenarios",
				sizeOfScenarioQuery.intValue(), counter);

		// Assertion for each scenario exist in list to verify date and
		// properties
		for (Scenario scenario : scenarios) {

			// Asserting the timeRange in scenario
			long scenarioStartTime = scenario.getStartTime();
			assertEquals(
					"scenario start time not in query timeRange",
					true,
					((scenarioStartTime <= timeRange.getUpBoundDate()) && (scenarioStartTime >= timeRange
							.getLowBoundDate())));
		}

		// Deletes all the scenarios created from the DB.
		for (Integer id : ids) {
			scenarioService.delete(id);
		}
	}

}
