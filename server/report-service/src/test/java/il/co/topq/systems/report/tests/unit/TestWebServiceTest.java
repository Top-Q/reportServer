package il.co.topq.systems.report.tests.unit;

import static org.junit.Assert.assertEquals;
import il.co.topq.systems.report.common.jaxbWrappers.TestList;
import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.model.Scenario;
import il.co.topq.systems.report.common.model.Test;
import il.co.topq.systems.report.common.obj.Chunk;
import il.co.topq.systems.report.common.obj.TestQuery;
import il.co.topq.systems.report.common.obj.TimeRange;
import il.co.topq.systems.report.component.utils.URLParts;
import il.co.topq.systems.report.service.infra.ScenarioService;
import il.co.topq.systems.report.service.infra.TestService;
import il.co.topq.systems.report.tests.infra.WebserviceBaseTest;
import il.co.topq.systems.report.utils.RandomData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/applicationContext.xml" })
public class TestWebServiceTest extends WebserviceBaseTest {

	@Autowired
	private TestService testService;

	@Autowired
	private ScenarioService scenarioService;

	// TESTED
	@org.junit.Test
	public void getTestByFilters() throws Exception {

		Integer numberOfQueryProperties = 2;
		Integer chunkLength = 10;
		List<Test> tests = new ArrayList<Test>();

		// scenarioWebServiceTest.setNumberOfTestsForScenario();
		Scenario scenario = scenarioService.create(RandomData.getScenarioWithTests(3));

		// Create the scenarioQuery: properties, timeRange
		Collection<ReportProperty> queryTestProperties = RandomData.getRandomTestPropertyList(numberOfQueryProperties);
		TimeRange timeRange = RandomData.getTimeRange(2004, 1, 1, 2010, 1, 1);
		timeRange.setUpBoundDate(System.currentTimeMillis());
		TestQuery testQuery = new TestQuery();
		testQuery.setProperties(new ArrayList<ReportProperty>(queryTestProperties));
		testQuery.setTimeRange(timeRange);
		Chunk chunk;

		// Gets the size of this query;
		Integer sizeOfTestQuery = Integer.valueOf(getWebResource(URLParts.SIZE_OF_TESTS_QUERY_URL).post(String.class,
				testQuery));
		int counter = 0;
		// Gets the test list;
		for (int i = 0; i <= (sizeOfTestQuery / chunkLength); i++) {
			chunk = new Chunk(i * chunkLength, chunkLength);
			testQuery.setChunk(chunk);
			List<Test> tempList = getWebResource(URLParts.GET_TEST_URL).post(TestList.class, testQuery).getTests();
			if (tempList != null) {
				counter += tempList.size();
			}
		}
		Assert.assertEquals("expecting to match the size of quert", sizeOfTestQuery.intValue(), counter);

		// Validation timeRange and properties
		for (Test test : tests) {

			// Asserting the properties in test
			Collection<ReportProperty> testProperties2 = test.getProperties();
			for (ReportProperty testProperty : queryTestProperties) {
				assertEquals("Test does not contain the property in query.", true,
						testProperties2.contains(testProperty));
			}

			// Asserting the timeRange in test
			long testStartTime = test.getStartTime();
			assertEquals("test start time is not in query timeRange.", true,
					((testStartTime <= timeRange.getUpBoundDate()) && (testStartTime >= timeRange.getLowBoundDate())));

		}
		scenarioService.delete(scenario.getId());
	}

	// TESTED
	@org.junit.Test
	public void updateTest() throws Exception {
		int numberOfTests = 1;
		Scenario scenarioWithTests = RandomData.getScenarioWithTests(numberOfTests);
		List<Test> testsBeforePersist = new ArrayList<Test>(scenarioWithTests.getTests());
		testsBeforePersist.get(0).setStatus((short) 0);
		scenarioWithTests.setSuccessTests(1);
		scenarioWithTests.setWarningTests(0);
		scenarioWithTests.setFailTests(0);
		scenarioWithTests.setTests(testsBeforePersist);
		Scenario scenario = scenarioService.create(scenarioWithTests);
		Assert.assertNotNull("scenario failed to create", scenario);
		Assert.assertEquals(Integer.valueOf(1), scenario.getSuccessTests());

		List<Test> tests = testService.getTestsByScenarioId(scenario.getId(), null);
		Assert.assertTrue(!tests.isEmpty());
		Test test = tests.get(0);
		test.setStatus((short) 1);
		boolean isUpdated = Boolean.valueOf(getWebResource(URLParts.UPDATE_TEST).post(String.class, test));
		Assert.assertTrue(isUpdated);
		Test updatedTest = testService.get(test.getId());
		Assert.assertNotNull(updatedTest);
		Assert.assertEquals("Expecting updated status", Short.valueOf((short) 1), updatedTest.getStatus());
		scenario = scenarioService.get(scenario.getId());
		Assert.assertEquals(Integer.valueOf(0), scenario.getSuccessTests());
		Assert.assertEquals(Integer.valueOf(1), scenario.getFailTests());
		Assert.assertEquals(Integer.valueOf(0), scenario.getWarningTests());
		scenarioService.delete(scenario.getId());
	}

	// TESTED
	@org.junit.Test
	public void deleteTest() throws Exception {
		Test test = testService.create(RandomData.getRandomTest());
		boolean isDeleted = Boolean.valueOf(getWebResource(URLParts.DELETE_TESTS_URL + test.getId()).get(String.class));
		Assert.assertTrue(isDeleted);
	}

	// TESTED
	@org.junit.Test
	public void countTests() throws Exception {
		Test test = testService.create(RandomData.getRandomTest());
		Assert.assertNotNull(test);
		Long numOfTests = Long.valueOf(getWebResource(URLParts.COUNT_TESTS_URL).post(String.class));
		Assert.assertEquals("Expecting 1 test in DB", 1, numOfTests.intValue());
		testService.delete(test.getId());
	}

	// TESTED
	@org.junit.Test
	public void getTestByID() throws Exception {
		Test test = testService.create(RandomData.getRandomTest());
		Test testFromDB = getWebResource(URLParts.GET_TEST_URL + test.getId()).get(Test.class);
		Assert.assertNotNull(testFromDB);
		testService.delete(testFromDB.getId());
	}

	// TESTED
	@org.junit.Test
	public void getTestScenarioId() throws Exception {
		Scenario scenario = scenarioService.create(RandomData.getScenarioWithTests(4));
		Assert.assertNotNull(scenario);

		List<Test> tests = testService.getTestsByScenarioId(scenario.getId(), null);
		Assert.assertNotNull(tests);
		Assert.assertTrue(!tests.isEmpty());

		Integer scenarioId = Integer.valueOf(getWebResource(URLParts.GET_TEST_SCENARIO + tests.get(0).getId()).get(
				String.class));
		Assert.assertEquals("Expecting scenarioId to match", scenario.getId(), scenarioId);

		scenarioService.delete(scenario.getId());
	}

	// TODO: Eran - need to impl this test
	public void deleteOrphanTestProperties() {

	}

	// TODO: ERAN - need to impl this test
	public void getTestSummary() {

	}

	// TODO: ERAN - need to impl this test
	public void getTestSummarySize() {

	}

	// TODO: ERAN - need to impl this test
	public void getTestsByTestSummary() {

	}

	// TODO: ERAN - need to impl this test
	public void getTestsByTestSummarySize() {

	}

}
