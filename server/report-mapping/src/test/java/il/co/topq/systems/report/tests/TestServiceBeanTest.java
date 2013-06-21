package il.co.topq.systems.report.tests;

import static org.junit.Assert.assertEquals;
import il.co.topq.systems.report.common.infra.log.ReportLogger;
import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.model.Scenario;
import il.co.topq.systems.report.common.model.Test;
import il.co.topq.systems.report.common.model.TestProperty;
import il.co.topq.systems.report.common.obj.Chunk;
import il.co.topq.systems.report.common.obj.TestQuery;
import il.co.topq.systems.report.service.infra.PropertyService;
import il.co.topq.systems.report.service.infra.ScenarioService;
import il.co.topq.systems.report.service.infra.TestService;
import il.co.topq.systems.report.utils.RandomData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

public class TestServiceBeanTest extends SpringBaseTest {

	private Logger log = ReportLogger.getInstance().getLogger(this.getClass());

	@Autowired
	private ScenarioService scenarioService;

	@Autowired
	private TestService testService;

	@Autowired
	private PropertyService<ReportProperty> scenarioPropertyService;

	@Autowired
	private PropertyService<ReportProperty> testPropertyService;

	@After
	public void afterEachTest() {
		scenarioPropertyService.deleteOrphanProperties();
		testPropertyService.deleteOrphanProperties();
		assertEquals(scenarioService.countAll(null), 0);
		assertEquals(testService.countAll(null), 0);
	}

	@org.junit.Test
	public void createTest() throws Exception {
		Test test = testService.create(RandomData.getRandomTest());
		Assert.assertNotNull(test);
		Assert.assertNotNull(test.getId());
		testService.delete(test.getId());
	}

	@org.junit.Test
	public void deleteTest() throws Exception {
		Test test = testService.create(RandomData.getRandomTest());
		Assert.assertNotNull(test);
		Assert.assertNotNull(test.getId());
		testService.delete(test.getId());
		Test test2 = testService.get(test.getId());
		Assert.assertNull(test2);
	}

	@org.junit.Test
	public void getTestById() throws Exception {

		Test test = testService.create(RandomData.getRandomTest());
		Assert.assertNotNull(test);
		Assert.assertNotNull(test.getId());
		Test testFromDb = testService.get(test.getId());
		Assert.assertNotNull(testFromDb);

		testService.delete(testFromDb.getId());
	}

	@org.junit.Test
	public void getChunkByQuery() throws Exception {
		Scenario scenario = scenarioService.create(RandomData.getScenarioWithTests(100));
		Assert.assertNotNull("scenario failed to create", scenario);

		Integer chunkLength = 14;
		Long numberOfTestsInDB = testService.countAll(null);
		Integer numberOfTestsRetrieved = 0;
		List<Test> testList;
		Chunk chunk;
		TestQuery testQuery;
		for (int i = 0; i <= (numberOfTestsInDB / chunkLength); i++) {
			chunk = new Chunk(i * chunkLength, chunkLength);
			testQuery = new TestQuery();
			testQuery.setGroupBy(0);
			testQuery.setChunk(chunk);
			testList = testService.getTestsByFiltersWithSortingCol(testQuery);
			numberOfTestsRetrieved += testList.size();
		}

		assertEquals("should return" + numberOfTestsInDB + " tests", numberOfTestsInDB.intValue(),
				numberOfTestsRetrieved.intValue());

		scenarioService.delete(scenario.getId());
	}

	@org.junit.Test
	public void getChunk() throws Exception {
		int numberOfTests = 100;
		Scenario scenario = scenarioService.create(RandomData.getScenarioWithTests(numberOfTests));
		Assert.assertNotNull("scenario failed to create", scenario);

		Integer chunkLength = 14;
		Integer numberOfTestsRetrieved = 0;
		List<Test> testList;
		Chunk chunk;
		for (int i = 0; i <= (numberOfTests / chunkLength); i++) {
			chunk = new Chunk(i * chunkLength, chunkLength);
			testList = testService.getChunk(chunk);
			numberOfTestsRetrieved += testList.size();
		}
		assertEquals(numberOfTests, numberOfTestsRetrieved.intValue());
		scenarioService.delete(scenario.getId());
	}

	@org.junit.Test
	public void update() throws Exception {
		int numberOfTests = 1;
		Scenario scenarioWithTests = RandomData.getScenarioWithTests(numberOfTests);
		List<Test> testsBeforePersist = new ArrayList<Test>(scenarioWithTests.getTests());
		testsBeforePersist.get(0).setStatus((short) 0);
		scenarioWithTests.setSuccessTests(1);
		scenarioWithTests.setWarningTests(0);
		scenarioWithTests.setFailTests(0);
		scenarioWithTests.setTests(new HashSet<Test>(testsBeforePersist));
		Scenario scenario = scenarioService.create(scenarioWithTests);
		Assert.assertNotNull("scenario failed to create", scenario);
		Assert.assertEquals(Integer.valueOf(1), scenario.getSuccessTests());

		List<Test> tests = testService.getTestsByScenarioId(scenario.getId(), null);
		Assert.assertTrue(!tests.isEmpty());
		Test test = tests.get(0);
		test.setStatus((short) 1);
		Test updatedTest = testService.update(test);
		Assert.assertNotNull(updatedTest);
		Assert.assertEquals("Expecting updated status", Short.valueOf((short) 1), updatedTest.getStatus());
		scenario = scenarioService.get(scenario.getId());
		Assert.assertEquals(Integer.valueOf(0), scenario.getSuccessTests());
		Assert.assertEquals(Integer.valueOf(1), scenario.getFailTests());
		Assert.assertEquals(Integer.valueOf(0), scenario.getWarningTests());
		scenarioService.delete(scenario.getId());
	}

	@org.junit.Test
	public void updateTestWithInvalidEmptyProperties() throws Exception {
		int numberOfTests = 1;
		Scenario scenarioWithTests = RandomData.getScenarioWithTests(numberOfTests);
		List<Test> testsBeforePersist = new ArrayList<Test>(scenarioWithTests.getTests());
		testsBeforePersist.get(0).setStatus((short) 0);
		scenarioWithTests.setSuccessTests(1);
		scenarioWithTests.setWarningTests(0);
		scenarioWithTests.setFailTests(0);
		scenarioWithTests.setTests(new HashSet<Test>(testsBeforePersist));
		Scenario scenario = scenarioService.create(scenarioWithTests);
		Assert.assertNotNull("scenario failed to create", scenario);
		Assert.assertEquals(Integer.valueOf(1), scenario.getSuccessTests());

		List<Test> tests = testService.getTestsByScenarioId(scenario.getId(), null);
		Assert.assertTrue(!tests.isEmpty());
		Test test = tests.get(0);
		test.setStatus((short) 1);
		Collection<ReportProperty> invalidProperties = new HashSet<ReportProperty>();
		invalidProperties.add(new TestProperty());
		test.setProperties(invalidProperties);
		Test updatedTest = null;
		try {
			updatedTest = testService.update(test);
		} catch (Exception e) {
			;// as expected
		}
		Assert.assertNull(updatedTest);
		scenarioService.delete(scenario.getId());
	}

	@org.junit.Test
	public void updateTestWithInvalidNullProperties() throws Exception {
		int numberOfTests = 1;
		Scenario scenarioWithTests = RandomData.getScenarioWithTests(numberOfTests);
		Scenario scenario = scenarioService.create(scenarioWithTests);
		Assert.assertNotNull("scenario failed to create", scenario);

		List<Test> tests = testService.getTestsByScenarioId(scenario.getId(), null);
		Assert.assertTrue(!tests.isEmpty());
		Test test = tests.get(0);
		Collection<ReportProperty> invalidProperties = new HashSet<ReportProperty>();
		invalidProperties.add(null);
		test.setProperties(invalidProperties);
		Test updatedTest = null;
		try {
			updatedTest = testService.update(test);
		} catch (Exception e) {
			;// as expected
		}
		Assert.assertNull(updatedTest);
		scenarioService.delete(scenario.getId());
	}

	@org.junit.Test
	public void getTestsWithNullTestQuery() throws Exception {
		int numberOfTests = 100;
		Scenario scenarioWithTests = RandomData.getScenarioWithTests(numberOfTests);
		Scenario scenario = scenarioService.create(scenarioWithTests);
		Assert.assertNotNull("scenario failed to create", scenario);

		List<Test> tests = testService.getTestsByScenarioId(scenario.getId(), null);
		Assert.assertEquals(numberOfTests, tests.size());
		scenarioService.delete(scenario.getId());
	}

	/**
	 * This method will create a scenario with X tests and assign a random set of properties to each set.<br>
	 * than it will look for tests with ALL the properties assigned.<br>
	 * 
	 * @throws Exception
	 *             thrown in case scenario was not deleted;
	 */
	@org.junit.Test
	public void getTestsWithPropNoTimeRange() throws Exception {

		Integer numOfTests = 20;
		Integer numOfProperties = 3;

		Collection<ReportProperty> testPropList = RandomData.getRandomTestPropertyList(numOfProperties);

		Scenario scenarioWithTests = RandomData.getScenarioWithTests(numOfTests);
		Collection<Test> tests = scenarioWithTests.getTests();
		for (Test test : tests) {
			test.setProperties(testPropList);
		}

		Scenario scenario = scenarioService.create(scenarioWithTests);
		Assert.assertNotNull(scenario);

		List<Test> testSet;
		TestQuery testQuery = new TestQuery();
		testQuery.setProperties(testPropList);

		testSet = testService.getTestsByFiltersWithSortingCol(testQuery);

		assertEquals("should return" + numOfTests + " tests", numOfTests.intValue(), testSet.size());

		scenarioService.delete(scenario.getId());

	}

	/**
	 * This method will create a scenario with X tests and assign a random set of properties to each set.<br>
	 * than it will look for tests with only some of the properties assigned.<br>
	 * 
	 * @throws Exception
	 *             thrown in case scenario was not deleted;
	 */
	@org.junit.Test
	public void getTestsWithPropTimeRange() throws Exception {

		Integer numOfTests = 20;
		Integer numOfProperties = 3;

		Collection<ReportProperty> testPropList = RandomData.getRandomTestPropertyList(numOfProperties);

		Scenario scenarioWithTests = RandomData.getScenarioWithTests(numOfTests);

		Collection<Test> tests = scenarioWithTests.getTests();
		for (Test test : tests) {
			test.setProperties(testPropList);
		}

		Scenario scenario = scenarioService.create(scenarioWithTests);
		Assert.assertNotNull(scenario);
		Integer scenarioId = scenario.getId();

		List<Test> testsFromDb;
		TestQuery testQuery = new TestQuery();
		testQuery.setProperties(testPropList);
		testQuery.setTimeRange(RandomData.getTimeRange(2000, 1, 1, 2004, 1, 1));
		testsFromDb = testService.getTestsByFiltersWithSortingCol(testQuery);

		assertEquals("should return 0" + " tests", 0, testsFromDb.size());

		scenarioService.delete(scenarioId);
	}

	/**
	 * This method will write a scenario with test containing properties.<br>
	 * it will than look up all tests containing the same properties.<br>
	 * 
	 * @throws Exception
	 *             will be thrown in case scenario could not be deleted
	 */
	@org.junit.Test
	public void getSizeOfQuery() throws Exception {

		Integer numOfTests = 20;
		Integer numOfProperties = 3;

		Collection<ReportProperty> testPropList = RandomData.getRandomTestPropertyList(numOfProperties);

		Scenario scenarioWithTests = RandomData.getScenarioWithTests(numOfTests);
		Collection<Test> tests = scenarioWithTests.getTests();
		for (Test test : tests) {
			test.setProperties(testPropList);
		}

		Scenario scenario = scenarioService.create(scenarioWithTests);
		Assert.assertNotNull(scenario);

		TestQuery testQuery = new TestQuery();
		testQuery.setProperties(testPropList);

		Integer sizeOfQuery = testService.getSizeOfTestQuery(testQuery);
		log.info(sizeOfQuery);
		assertEquals("should return" + numOfTests + " tests", numOfTests.longValue(), sizeOfQuery.longValue());

		scenarioService.delete(scenario.getId());
	}

	@org.junit.Test
	public void getTestsWithPropTimeRangeTestName() throws Exception {

		Integer numOfTests = 20;
		Integer numOfProperties = 3;

		Collection<ReportProperty> testPropList = RandomData.getRandomTestPropertyList(numOfProperties);

		Scenario scenarioWithTests = RandomData.getScenarioWithTests(numOfTests);

		Collection<Test> tests = scenarioWithTests.getTests();
		for (Test test : tests) {
			test.setProperties(testPropList);
		}

		Scenario scenario = scenarioService.create(scenarioWithTests);
		Assert.assertNotNull(scenario);
		Integer scenarioId = scenario.getId();

		List<Test> testsFromDb;
		TestQuery testQuery = new TestQuery();
		testQuery.setProperties(testPropList);
		testQuery.setTimeRange(RandomData.getTimeRange(2000, 1, 1, 2013, 1, 1));
		testQuery.setSearchName("13");
		testsFromDb = testService.getTestsByFiltersWithSortingCol(testQuery);

		assertEquals("should return 0" + " tests", 0, testsFromDb.size());

		scenarioService.delete(scenarioId);
	}
}
