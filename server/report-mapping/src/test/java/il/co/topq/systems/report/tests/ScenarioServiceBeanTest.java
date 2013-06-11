package il.co.topq.systems.report.tests;

import static org.junit.Assert.assertEquals;
import il.co.topq.systems.report.common.infra.log.ReportLogger;
import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.model.Scenario;
import il.co.topq.systems.report.common.model.ScenarioProperty;
import il.co.topq.systems.report.common.model.Test;
import il.co.topq.systems.report.common.obj.Chunk;
import il.co.topq.systems.report.common.obj.ScenarioQuery;
import il.co.topq.systems.report.common.obj.SortingColumn;
import il.co.topq.systems.report.common.obj.TestQuery;
import il.co.topq.systems.report.service.infra.PropertyService;
import il.co.topq.systems.report.service.infra.ScenarioService;
import il.co.topq.systems.report.service.infra.TestService;
import il.co.topq.systems.report.utils.RandomData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

public class ScenarioServiceBeanTest extends SpringBaseTest {

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
		// scenarioPropertyService.deleteOrphanProperties();
		// testPropertyService.deleteOrphanProperties();
		// assertEquals(scenarioService.countAll(null), 0);
		// assertEquals(testService.countAll(null), 0);
	}

	// TODO: Eran - implement this test make sure tests are ordered in startTime asc order
	public void compareScenarios() {

	}

	// @org.junit.Test
	// public void updateAll() {
	// long start = System.currentTimeMillis();
	// List<Scenario> scenarios = scenarioService.getAll();
	// for (Scenario scenario : scenarios) {
	// scenario.setProperties(scenario.getProperties());
	// }
	// scenarioService.updateAll(scenarios);
	// System.out.println("Start:" + start);
	// System.out.println("End:" + System.currentTimeMillis());
	// }

	public void loadDB() throws Exception {
		List<String> vals = Arrays.asList("val", "val2", "val3", "val4", "val5", "val6", "val7", "val8", "val9");
		List<String> keys = Arrays.asList("key", "key2", "key3", "key4", "key5", "key6", "key7", "key8", "key9");
		for (int i = 0; i < 1000000; i++) {
			Collection<ReportProperty> scenarioProperties = new HashSet<ReportProperty>();
			int numberOfProperties = new Random().nextInt(vals.size());
			for (int j = 0; j < numberOfProperties; j++) {
				scenarioProperties.add(new ScenarioProperty(keys.get(new Random().nextInt(keys.size())), vals
						.get(new Random().nextInt(vals.size()))));
			}
			Scenario scenario = RandomData.getScenarioWithTests(1);
			scenario.setProperties(scenarioProperties);
			scenarioService.create(scenario);
		}
	}

	public void LoadDBUsingChunk() throws Exception {

		List<String> vals = Arrays.asList("val", "val2", "val3", "val4", "val5", "val6", "val7", "val8", "val9");
		List<String> keys = Arrays.asList("key", "key2", "key3", "key4", "key5", "key6", "key7", "key8", "key9");
		for (int i = 0; i < 1000; i++) {
			List<Scenario> toCreate = new ArrayList<Scenario>();
			for (int k = 0; k < 10000; k++) {
				// Collection<ReportProperty> scenarioProperties = new HashSet<ReportProperty>();
				// int numberOfProperties = new Random().nextInt(vals.size());
				// for (int j = 0; j < numberOfProperties; j++) {
				// scenarioProperties.add(new ScenarioProperty(keys.get(new Random().nextInt(keys.size())), vals
				// .get(new Random().nextInt(vals.size()))));
				// }
				Scenario scenario = RandomData.getScenarioWithTests(1);
				scenario.setProperties(null);
				// scenario.setProperties(scenarioProperties);
				toCreate.add(scenario);
			}

			scenarioService.updateAll(toCreate);
		}
	}

	@org.junit.Test
	public void updateScenarioWithInvalidProperties() throws Exception {
		Scenario scenario = RandomData.getScenarioWithTests(3);
		scenario.setProperties(RandomData.getRandomScenarioPropertyList(3));
		Scenario scenarioFromDB = scenarioService.create(scenario);

		Assert.assertNotNull(scenarioFromDB);
		Integer scenarioId = scenarioFromDB.getId();
		Assert.assertNotNull(scenarioId);

		Collection<ReportProperty> invalidProperties = new HashSet<ReportProperty>();
		invalidProperties.add(new ScenarioProperty());
		scenarioFromDB.setProperties(invalidProperties);
		Scenario updatedScenario = null;
		try {
			updatedScenario = scenarioService.update(scenarioFromDB);
		} catch (Exception e) {
			;// expecting this;
		}
		scenarioService.delete(scenarioId);
		if (updatedScenario != null) {
			throw new Exception("update scenario should have failed");
		}

	}

	@org.junit.Test
	public void updateScenarioWithValidProperties() throws Exception {
		Scenario scenario = RandomData.getScenarioWithTests(3);
		scenario.setProperties(RandomData.getRandomScenarioPropertyList(3));
		Scenario scenarioFromDB = scenarioService.create(scenario);

		Assert.assertNotNull(scenarioFromDB);
		Integer scenarioId = scenarioFromDB.getId();
		Assert.assertNotNull(scenarioId);

		Collection<ReportProperty> validProperties = new HashSet<ReportProperty>();
		validProperties.add(new ScenarioProperty("validkey", "validValue"));
		scenarioFromDB.setProperties(validProperties);
		Scenario updatedScenario = scenarioService.update(scenarioFromDB);
		Assert.assertNotNull(updatedScenario);
		scenarioService.delete(scenarioId);
	}

	@org.junit.Test
	public void createScenarioWithInvalidProperties() throws Exception {
		Scenario scenario = RandomData.getScenarioWithTests(3);
		Collection<ReportProperty> invalidProperties = RandomData.getRandomScenarioPropertyList(3);
		invalidProperties.add(new ScenarioProperty());
		scenario.setProperties(invalidProperties);
		Scenario scenarioFromDB = null;
		try {
			scenarioFromDB = scenarioService.create(scenario);
		} catch (Exception e) {
			;// expecting this;
		} finally {
			if (scenarioFromDB != null) {
				scenarioService.delete(scenarioFromDB.getId());
				throw new Exception("create scenario should have failed");
			}
		}
	}

	@org.junit.Test
	public void getScenarioNumberOfTestsByScenarioId() throws Exception {
		int numberOfTests = 3;
		Scenario scenarioFromDB = scenarioService.create(RandomData.getScenarioWithTests(numberOfTests));

		Assert.assertNotNull(scenarioFromDB);
		Integer scenarioId = scenarioFromDB.getId();
		Assert.assertNotNull(scenarioId);

		int numberOfTestsFromDB = scenarioService.getSizeOfTestsSetByScenarioID(scenarioId);

		Assert.assertEquals("Expecting same number of tests", numberOfTests, numberOfTestsFromDB);
		scenarioService.delete(scenarioId);
	}

	@org.junit.Test
	public void getChunkOfScenarios() throws Exception {
		List<Scenario> scenarioList;
		int numOfScenarios = 50;
		Integer chunkLength = 14;
		Integer numberOfScenariosRetrieved = 0;
		Chunk chunk;

		// create scenarios in DB
		for (int i = 0; i < numOfScenarios; i++) {
			Scenario scenario = scenarioService.create(RandomData.getScenarioWithTests(3));
			Assert.assertNotNull(scenario);
			Integer scenarioId = scenario.getId();
			Assert.assertNotNull(scenarioId);
		}

		// fetch chunk of scenarios
		for (int i = 0; i <= (numOfScenarios / chunkLength); i++) {
			chunk = new Chunk(i * chunkLength, chunkLength);
			scenarioList = scenarioService.getChunk(chunk);
			if (i < (numOfScenarios / chunkLength)) {
				Assert.assertEquals("expecting size of fetched scenarios is equal to the size of chunk",
						chunkLength.intValue(), scenarioList.size());
			} else {
				int expected = numOfScenarios - (chunkLength * (numOfScenarios / chunkLength));
				Assert.assertEquals("the remains should be less than the chunk size", expected, scenarioList.size());
			}
			numberOfScenariosRetrieved += scenarioList.size();
		}

		assertEquals("should return" + numOfScenarios + " scenarios", numOfScenarios,
				numberOfScenariosRetrieved.intValue());

		// delete all scenarios
		for (Scenario scenario : scenarioService.getAll()) {
			scenarioService.delete(scenario.getId());
		}
	}

	@org.junit.Test
	public void createScenarioWithNullProperty() throws Exception {
		Scenario scenario = RandomData.getScenarioWithTests(3);
		Collection<ReportProperty> invalidProperties = RandomData.getRandomScenarioPropertyList(3);
		invalidProperties.add(null);
		scenario.setProperties(invalidProperties);
		Scenario scenarioFromDB = null;
		try {
			scenarioFromDB = scenarioService.create(scenario);
		} catch (Exception e) {
			;// expecting this;
		} finally {
			if (scenarioFromDB != null) {
				scenarioService.delete(scenarioFromDB.getId());
				throw new Exception("create scenario should have failed");
			}
		}
	}

	@org.junit.Test
	public void updateScenarioWithNullProperty() throws Exception {
		Scenario scenario = RandomData.getScenarioWithTests(3);
		scenario.setProperties(RandomData.getRandomScenarioPropertyList(3));
		Scenario scenarioFromDB = scenarioService.create(scenario);

		Assert.assertNotNull(scenarioFromDB);
		Integer scenarioId = scenarioFromDB.getId();
		Assert.assertNotNull(scenarioId);

		Collection<ReportProperty> invalidProperties = new HashSet<ReportProperty>();
		invalidProperties.add(null);
		scenarioFromDB.setProperties(invalidProperties);
		Scenario updatedScenario = null;
		try {
			updatedScenario = scenarioService.update(scenarioFromDB);
		} catch (Exception e) {
			;// expecting this;
		}
		scenarioService.delete(scenarioId);
		if (updatedScenario != null) {
			throw new Exception("update should have failed");
		}
	}

	/**
	 * tests the creation of a scenario and the insertion into to DB<br>
	 * tests the deletion of the scenario created;
	 * 
	 * @throws Exception
	 */
	@org.junit.Test
	public void createScenario() throws Exception {
		Scenario scenario = RandomData.getScenarioWithTests(1);
		scenario.setProperties(RandomData.getRandomScenarioPropertyList(3));
		Scenario scenarioFromDB = scenarioService.create(scenario);

		Assert.assertNotNull(scenarioFromDB);
		Integer scenarioId = scenarioFromDB.getId();
		Assert.assertNotNull(scenarioId);

		scenarioService.delete(scenarioId);
	}

	/**
	 * This test will be used to check visibly in UI the compare scenario fitcher.<Br>
	 * 
	 * @throws Exception
	 */
	@org.junit.Test
	public void createScenarioForCompare() throws Exception {
		Collection<Test> randomTests = RandomData.getRandomTests(5);
		Scenario s1 = RandomData.getScenarioWithTests(5);
		Scenario s2 = RandomData.getScenarioWithTests(5);
		for (Test test : randomTests) {
			test.setScenario(s1);
		}
		s1.setTests(randomTests);
		scenarioService.create(s1);
		for (Test test : randomTests) {
			test.setId(null);
			test.setScenario(s2);
		}
		s2.setTests(randomTests);
		scenarioService.create(s2);
		scenarioService.delete(s1.getId());
		scenarioService.delete(s2.getId());
	}

	@org.junit.Test
	public void create2ScenariosWithSameProperties() throws Exception {
		Collection<ReportProperty> scenarioProperties = new HashSet<ReportProperty>();
		scenarioProperties.add(new ScenarioProperty("key", "val"));

		Scenario scenario = RandomData.getScenarioWithTests(50);
		scenario.setProperties(scenarioProperties);
		Scenario scenario1 = scenarioService.create(scenario);
		Assert.assertNotNull(scenario1);
		Integer scenario1Id = scenario1.getId();
		Assert.assertNotNull(scenario1Id);
		Collection<ReportProperty> scenario1Properties = scenario1.getProperties();
		Assert.assertEquals(1, scenario1Properties.size());

		Scenario scenario2 = RandomData.getScenarioWithTests(50);
		scenario2.setProperties(scenarioProperties);
		Scenario scenario2FromDb = scenarioService.create(scenario2);
		Assert.assertNotNull(scenario2);
		Integer scenario2Id = scenario2.getId();
		Assert.assertNotNull(scenario2Id);
		Collection<ReportProperty> scenario2Properties = scenario2FromDb.getProperties();
		Assert.assertEquals(1, scenario2Properties.size());

		scenarioService.delete(scenario1Id);
		scenarioService.delete(scenario2Id);
	}

	/**
	 * create scenario with tests and fetch it<br>
	 * 
	 * @throws Exception
	 */
	@org.junit.Test
	public void getScenariosTests() throws Exception {

		int numOfTests = 3;
		Scenario scenario = scenarioService.create(RandomData.getScenarioWithTests(numOfTests));
		Assert.assertNotNull(scenario);
		Integer scenarioId = scenario.getId();
		Assert.assertNotNull(scenarioId);

		List<Test> scenarioTests = testService.getTestsByScenarioId(scenarioId, new TestQuery());
		Assert.assertNotNull(scenarioTests);
		Assert.assertTrue("Expecting list of tests to not be emtpy", !scenarioTests.isEmpty());

		assertEquals("Expecting " + numOfTests + " tests in scenarioTests", numOfTests, scenarioTests.size());

		scenarioService.delete(scenarioId);
	}

	/**
	 * This method will test the retrieval of a scenario by its id.<br>
	 * it will create a random scenario with tests and save it id.<br>
	 * than it will try and get the scenario by its id from DB.
	 * 
	 * @throws Exception
	 *             case could not get the scenario by its ID
	 */
	@org.junit.Test
	public void getScenarioByID() throws Exception {
		Scenario scenario = scenarioService.create(RandomData.getScenarioWithTests(3));
		Assert.assertNotNull(scenario);
		Integer scenarioId = scenario.getId();
		Assert.assertNotNull(scenarioId);

		Scenario scenario2 = scenarioService.get(scenarioId);
		Assert.assertNotNull(scenario2);

		scenarioService.delete(scenarioId);
	}

	/**
	 * This method will test the retrieval of scenario with an empty scenario Query.<br>
	 * expected: the result should contain all the scenarios from DB.
	 * 
	 * @throws Exception
	 */
	@org.junit.Test
	public void getScenarioWithNoFilters() throws Exception {
		List<Scenario> scenarioList;
		List<Scenario> createdScenarios = new ArrayList<Scenario>();
		int numOfScenarios = 50;
		Integer chunkLength = 14;
		Integer numberOfScenariosRetrieved = 0;
		Chunk chunk;

		// create scenarios in DB
		for (int i = 0; i < numOfScenarios; i++) {
			Scenario scenario = scenarioService.create(RandomData.getScenarioWithTests(3));
			Assert.assertNotNull(scenario);
			Integer scenarioId = scenario.getId();
			Assert.assertNotNull(scenarioId);
			createdScenarios.add(scenario);
		}

		// fetch chunk of scenarios
		for (int i = 0; i <= (numOfScenarios / chunkLength); i++) {
			chunk = new Chunk(i * chunkLength, chunkLength);
			ScenarioQuery scenarioQuery = new ScenarioQuery();
			scenarioQuery.setChunk(chunk);
			scenarioList = scenarioService.getScenariosByFiltersWithSortingCol(scenarioQuery);
			numberOfScenariosRetrieved += scenarioList.size();
		}

		assertEquals("should return" + numOfScenarios + " scenarios", numOfScenarios,
				numberOfScenariosRetrieved.intValue());

		// delete all scenarios
		for (Scenario scenario : createdScenarios) {
			scenarioService.delete(scenario.getId());
		}
	}

	/**
	 * This method will test the retrieval of scenarios written with properties.<br>
	 * it will create X scenario and will assign all of them the same random properties which no other scenario in DB
	 * holds. than will get a query based on these properties.<br>
	 * Expected: result size X
	 * 
	 * @throws Exception
	 */
	@org.junit.Test
	public void getScenariosWithProperties() throws Exception {

		Integer numOfScenarios = 20;
		Integer numOfProperties = 3;
		List<Integer> scenarioIds = new ArrayList<Integer>();

		Collection<ReportProperty> scenarioPropList = RandomData.getRandomScenarioPropertyList(numOfProperties);

		for (int i = 0; i < numOfScenarios; i++) {
			Scenario scenario = RandomData.getScenarioWithTests(2);
			scenario.setProperties(scenarioPropList);
			scenarioService.create(scenario);
			Assert.assertNotNull(scenario);
			Integer scenarioId = scenario.getId();
			Assert.assertNotNull(scenarioId);
			scenarioIds.add(scenarioId);
		}

		ScenarioQuery scenarioQuery = new ScenarioQuery();
		scenarioQuery.setProperties(scenarioPropList);

		List<Scenario> scenarios = scenarioService.getScenariosByFiltersWithSortingCol(scenarioQuery);
		assertEquals("should return" + numOfScenarios + " scenarios", numOfScenarios.longValue(),
				((Integer) scenarios.size()).longValue());

		for (Integer id : scenarioIds) {
			scenarioService.delete(id);
		}
	}

	/**
	 * This method test the retrieval of scenarios by timeRange. it will check the scenario that answers a timeRange and
	 * than will create scenario that don't match the timeRange. the result should be the result prior to the creation
	 * of the scenarios.
	 * 
	 * @throws Exception
	 */
	@org.junit.Test
	public void getScenariosByTimeRange() throws Exception {

		ScenarioQuery scenarioQuery = new ScenarioQuery();
		Integer numOfScenarios = 20;
		Integer numOfProperties = 3;
		List<Integer> scenarioIds = new ArrayList<Integer>();

		scenarioQuery.setTimeRange(RandomData.getTimeRange(2005, 1, 1, 2013, 1, 1));
		List<Scenario> scenariosBefore = scenarioService.getScenariosByFiltersWithSortingCol(scenarioQuery);
		Integer numOfScenarioAnsweringTimeRangeBeforeCreationOfMoreScenarios = scenariosBefore.size();

		Collection<ReportProperty> scenarioPropList = RandomData.getRandomScenarioPropertyList(numOfProperties);

		for (int i = 0; i < numOfScenarios; i++) {
			Scenario scenario = RandomData.getScenarioWithTests(2);
			scenario.setStartTime(System.currentTimeMillis());
			scenario.setProperties(scenarioPropList);
			Scenario createdScenario = scenarioService.create(scenario);
			Assert.assertNotNull(createdScenario);
			Integer scenarioId = createdScenario.getId();
			Assert.assertNotNull(scenarioId);
			scenarioIds.add(scenarioId);
		}

		scenarioQuery = new ScenarioQuery();
		scenarioQuery.setTimeRange(RandomData.getTimeRange(2005, 1, 1, 2013, 1, 1));

		List<Scenario> scenariosAfter = scenarioService.getScenariosByFiltersWithSortingCol(scenarioQuery);
		assertEquals("should return" + numOfScenarioAnsweringTimeRangeBeforeCreationOfMoreScenarios + " scenarios",
				numOfScenarioAnsweringTimeRangeBeforeCreationOfMoreScenarios.longValue(),
				((Integer) scenariosAfter.size()).longValue());

		for (Integer id : scenarioIds) {
			scenarioService.delete(id);
		}
	}

	/**
	 * This method test the retrieval of scenarios by timeRange. it will check the scenario that answers a timeRange and
	 * than will create scenario that don't match the timeRange. the result should be the result prior to the creation
	 * of the scenarios.
	 * 
	 * @throws Exception
	 */
	@org.junit.Test
	public void getScenariosByTimeRangeAndScenarioName() throws Exception {

		ScenarioQuery scenarioQuery = new ScenarioQuery();
		Integer numOfScenarios = 20;
		Integer numOfProperties = 3;
		List<Integer> scenarioIds = new ArrayList<Integer>();

		scenarioQuery.setTimeRange(RandomData.getTimeRange(2005, 1, 1, 2013, 1, 1));
		List<Scenario> scenariosBefore = scenarioService.getScenariosByFiltersWithSortingCol(scenarioQuery);
		Integer numOfScenarioAnsweringTimeRangeBeforeCreationOfMoreScenarios = scenariosBefore.size();

		Collection<ReportProperty> scenarioPropList = RandomData.getRandomScenarioPropertyList(numOfProperties);

		for (int i = 0; i < numOfScenarios; i++) {
			Scenario scenario = RandomData.getScenarioWithTests(2);
			scenario.setStartTime(System.currentTimeMillis());
			scenario.setProperties(scenarioPropList);
			Scenario createdScenario = scenarioService.create(scenario);
			Assert.assertNotNull(createdScenario);
			Integer scenarioId = createdScenario.getId();
			Assert.assertNotNull(scenarioId);
			scenarioIds.add(scenarioId);
		}

		scenarioQuery = new ScenarioQuery();
		scenarioQuery.setTimeRange(RandomData.getTimeRange(2005, 1, 1, 2013, 1, 1));

		scenarioQuery.setSearchName("de");
		List<Scenario> scenariosAfter = scenarioService.getScenariosByFiltersWithSortingCol(scenarioQuery);
		assertEquals("should return" + numOfScenarioAnsweringTimeRangeBeforeCreationOfMoreScenarios + " scenarios",
				numOfScenarioAnsweringTimeRangeBeforeCreationOfMoreScenarios.longValue(),
				((Integer) scenariosAfter.size()).longValue());

		for (Integer id : scenarioIds) {
			scenarioService.delete(id);
		}
	}

	/**
	 * will test the get size of result set by property filters;
	 * 
	 * @throws Exception
	 */
	@org.junit.Test
	public void testGetSizeOfFilterQuery() throws Exception {

		Integer numOfScenarios = 20;
		Integer numOfProperties = 3;
		List<Integer> scenarioIds = new ArrayList<Integer>();

		Collection<ReportProperty> scenarioPropList = RandomData.getRandomScenarioPropertyList(numOfProperties);

		for (int i = 0; i < numOfScenarios; i++) {
			Scenario scenario = RandomData.getScenarioWithTests(2);
			scenario.setProperties(scenarioPropList);
			Scenario createdScenario = scenarioService.create(scenario);
			Assert.assertNotNull(createdScenario);
			Integer scenarioId = createdScenario.getId();
			Assert.assertNotNull(scenarioId);
			scenarioIds.add(scenarioId);
		}

		ScenarioQuery scenarioQuery = new ScenarioQuery();
		scenarioQuery.setProperties(scenarioPropList);

		Integer sizeOfQuery = scenarioService.getSizeOfScenarioQuery(scenarioQuery);
		assertEquals("should return" + numOfScenarios + " scenarios", numOfScenarios.longValue(),
				sizeOfQuery.longValue());

		for (Integer id : scenarioIds) {
			scenarioService.delete(id);
		}
	}

	/**
	 * Main goal: Test that when a test is fetched from the db its scenario is fetched too. (Not lazy)
	 * 
	 * @throws Exception
	 */
	@org.junit.Test
	public void fetchScenarioByQueryTest() throws Exception {
		TestQuery testQuery = new TestQuery();
		testQuery.setSortingColumn(new SortingColumn("startTime", true));
		Scenario scenario = RandomData.getScenarioWithTests(2);
		Scenario scenarioFromDB = scenarioService.create(scenario);
		Assert.assertNotNull(scenarioFromDB);
		Integer scenarioId = scenarioFromDB.getId();
		Assert.assertNotNull(scenarioId);

		List<Test> testsByScenarioID = testService.getTestsByScenarioId(scenarioId, testQuery);
		Test test = testsByScenarioID.get(0);
		Test test1 = testService.get(test.getId());
		Scenario scenario1 = test1.getScenario();
		Assert.assertEquals(scenario1, scenario);
		scenarioService.delete(scenarioId);

	}

}
