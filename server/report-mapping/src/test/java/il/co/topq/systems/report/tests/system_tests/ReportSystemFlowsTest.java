//package il.co.topq.systems.report.tests.system_tests;
//
//import il.co.topq.systems.report.beans.SettingsBean;
//import il.co.topq.systems.report.common.model.Scenario;
//import il.co.topq.systems.report.common.model.ScenarioProperty;
//import il.co.topq.systems.report.common.model.Test;
//import il.co.topq.systems.report.common.model.TestSummary;
//import il.co.topq.systems.report.common.obj.Chunk;
//import il.co.topq.systems.report.common.obj.SortingColumn;
//import il.co.topq.systems.report.common.obj.SystemSettings;
//import il.co.topq.systems.report.common.obj.TestQuery;
//import il.co.topq.systems.report.common.obj.TestSummaryQuery;
//import il.co.topq.systems.report.common.obj.TimeRange;
//import il.co.topq.systems.report.tests.players.AdminUser;
//import il.co.topq.systems.report.tests.players.Database;
//import il.co.topq.systems.report.tests.players.ExecutionReportSimulator;
//import il.co.topq.systems.report.tests.players.JSystemGUI;
//import il.co.topq.systems.report.tests.players.SettingsReportSimulator;
//import il.co.topq.systems.report.tests.players.TestGroupSimulator;
//import il.co.topq.systems.report.tests.players.TestsReportSimulator;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.Random;
//
//import javax.sql.rowset.serial.SerialException;
//
//import org.hibernate.annotations.OrderBy;
//import org.junit.Assert;
//
//public class ReportSystemFlowsTest extends AbstractSystemTest {
//
//	/**
//	 * Main Goal: Test if a Scenario with properties is deleted the properties
//	 * that are orphan are deleted too.
//	 */
//	@org.junit.Test
//	public void deletingAScenarioWithOrphanProperties() {
//		// Clean Database
//		new AdminUser().cleanDatabase();
//		int randNumberOfTests = new Random().nextInt(300);
//		// Publish a Scenario with a random number of tests
//		Scenario scenario = new JSystemGUI().publish(JSystemGUI.PublishType.SCENARIO_PROPERTIES, randNumberOfTests);
//		Assert.assertEquals("Number of scenario properties ", 2, new Database().countScenarioProperties().intValue());
//
//		new ExecutionReportSimulator().deleteSpecificScenario(scenario);
//		// call delete orphan scenario properties. and only than assert.
//		// Assert.assertEquals("Number of scenario properties ", 0, new
//		// Database().countScenarioProperties().intValue());
//	}
//
//	@org.junit.Test
//	public void addingPropertiesToAScenario() {
//		// Clean Database
//		new AdminUser().cleanDatabase();
//		int randNumberOfTests = new Random().nextInt(300);
//		// Publish a Scenario with a random number of tests
//		Scenario scenario = new JSystemGUI().publish(JSystemGUI.PublishType.SCENARIO_PROPERTIES, randNumberOfTests);
//		Assert.assertEquals("Number of scenario properties ", 2, new Database().countScenarioProperties().intValue());
//
//		ScenarioProperty scenarioProperty = new ScenarioProperty("newKey", "newValue");
//		new ExecutionReportSimulator().addPropertyToScenario(scenario, scenarioProperty);
//		Assert.assertEquals("Number of scenario properties ", 3, new Database().countScenarioProperties().intValue());
//
//		// Verify that all properties are deleted including the new one
//		new ExecutionReportSimulator().deleteSpecificScenario(scenario);
//
//		// call delete orphan scenario properties. and only than assert.
//		// Assert.assertEquals("Number of scenario properties ", 0, new
//		// Database().countScenarioProperties().intValue());
//	}
//
//	/**
//	 * Verify that when a test update its status the scenario also update its
//	 * counters
//	 */
//	@org.junit.Test
//	public void updateTestStatus() {
//		// Clean Database
//		new AdminUser().cleanDatabase();
//		// Publish a Scenario with a random number of tests
//		Scenario scenario = new JSystemGUI().publish(JSystemGUI.PublishType.NO_PROPERTIES, 1);
//		Assert.assertEquals("Number of scenario", 1, new Database().countScenarios().intValue());
//		Assert.assertEquals("Number of tests", 1, new Database().countTests().intValue());
//		Test test = new ExecutionReportSimulator().selectTestAt(scenario, 0);
//		switch (test.getStatus()) {
//		case 0:
//			Assert.assertTrue(scenario.getSuccessTests() == 1);
//			break;
//		case 1:
//			Assert.assertTrue(scenario.getFailTests() == 1);
//			break;
//		case 2:
//			Assert.assertTrue(scenario.getWarningTests() == 1);
//			break;
//		}
//		test.setStatus((short) ((test.getStatus() + 1) % 3));
//		new ExecutionReportSimulator().updateTest(test);
//		Scenario persistedScenario = new ExecutionReportSimulator().getScenario(scenario.getId());
//		switch (test.getStatus()) {
//		case 0:
//			Assert.assertTrue(persistedScenario.getSuccessTests() == 1);
//			break;
//		case 1:
//			Assert.assertTrue(persistedScenario.getFailTests() == 1);
//			break;
//		case 2:
//			Assert.assertTrue(persistedScenario.getWarningTests() == 1);
//			break;
//		}
//	}
//
//	/**
//	 * Main goal: Verify that when retrieving the tests in desc order works
//	 * properly
//	 */
//	@org.junit.Test
//	public void verifyGetTestsInDescOrder() {
//		// Clean Database
//		new AdminUser().cleanDatabase();
//		int randNumberOfTests = 20;
//		// Publish a Scenario with a random number of tests
//		new JSystemGUI().publish(JSystemGUI.PublishType.SCENARIO_PROPERTIES, randNumberOfTests);
//
//		TestQuery testQuery = new TestQuery();
//		testQuery.setGroupBy(0);
//		testQuery.setChunk(new Chunk(0, randNumberOfTests));
//		testQuery.setSortingColumn(new SortingColumn("startTime", false));
//		List<Test> tests = new TestsReportSimulator().getTests(testQuery);
//		Assert.assertTrue(randNumberOfTests == tests.size());
//		Test test = tests.get(0);
//		for (int i = 1; i < tests.size(); i++) {
//			Assert.assertTrue(test.getStartTime() > tests.get(i).getStartTime());
//			test = tests.get(i);
//		}
//	}
//
//	/**
//	 * Main goal: Verify that user can save update and load system settings
//	 * 
//	 * @throws java.io.IOException
//	 *             -
//	 * @throws ClassNotFoundException
//	 *             -
//	 * @throws SQLException
//	 * @throws SerialException
//	 */
//	@org.junit.Test
//	public void saveUpdateLoadSystemSettings() throws IOException, ClassNotFoundException, SerialException,
//			SQLException {
//		// Clean Database
//		new AdminUser().cleanDatabase();
//
//		Integer defaultTestGrouping = 2;
//		// Save settings to database
//		SystemSettings systemSettings = new SystemSettings();
//		systemSettings.setDefaultTestGrouping(defaultTestGrouping);
//		new SettingsReportSimulator().saveSystemSettings(systemSettings);
//
//		// Load system settings to database
//		SystemSettings persistedSettings = new SettingsReportSimulator().getSystemSettings();
//		Assert.assertTrue(persistedSettings.getDefaultTestGrouping().equals(defaultTestGrouping));
//
//		SystemSettings updatedSystemSettings = new SettingsReportSimulator().getSystemSettings();
////		SystemSettings updatedSystemSettings = new SystemSettings();
//		updatedSystemSettings.setDefaultTestGrouping(defaultTestGrouping + 1);
//		new SettingsReportSimulator().updateSystemSettings(updatedSystemSettings);
//
//		// Load system settings to database
//		persistedSettings = new SettingsReportSimulator().getSystemSettings();
//		Assert.assertTrue(persistedSettings.getDefaultTestGrouping().equals(defaultTestGrouping + 1));
//
//	}
//
//	@org.junit.Test
//	public void getSystemSettings() throws Exception, ClassNotFoundException, IOException, SQLException {
//		SettingsBean settingsBean = new SettingsBean();
//		settingsBean.getSystemSettings();
//	}
//
//	@org.junit.Test
//	public void createSystemSettings() throws IOException {
//		SettingsBean settingsBean = new SettingsBean();
//		settingsBean.createSystemSettings(new SystemSettings());
//	}
//
//	/**
//	 * Main goal: Verify that it is possible to retrieve the TestSummary
//	 */
//	@org.junit.Test
//	public void testGroupByName() {
//		// Clean Database
//		new AdminUser().cleanDatabase();
//
//		int numOfGroups = 10;
//		new JSystemGUI().publishTestGroupByName(numOfGroups);
//
//		List<TestSummary> testGroupByName = new TestGroupSimulator().getTestGroupByName();
//
//		Assert.assertEquals(numOfGroups, testGroupByName.size());
//
//		TestQuery testQuery = new TestQuery();
//		testQuery.setGroupBy(1);
//		testQuery.setChunk(new Chunk(0, 14));
//		testQuery.setSortingColumn(new SortingColumn("testName", true));
//
//		new TestGroupSimulator().getTestByGroup(testQuery);
//		Assert.assertEquals(numOfGroups, testGroupByName.size());
//	}
//
//	@org.junit.Test
//	public void testGroupWhenInEmptyDatabase() {
//		// Clean Database
//		new AdminUser().cleanDatabase();
//
//		List<TestSummary> testGroupByName = new TestGroupSimulator().getTestGroupByName();
//
//		Assert.assertEquals(0, testGroupByName.size());
//
//		TestQuery testQuery = new TestQuery();
//		testQuery.setGroupBy(0);
//		testQuery.setChunk(new Chunk(0, 14));
//		testQuery.setSortingColumn(new SortingColumn("testName", true));
//
//		Assert.assertEquals(0, testGroupByName.size());
//	}
//
//	/**
//	 * Main goal: Verify that it is possible to retrieve the TestSummary This
//	 * Test will create tests by number of groups for scenarioX.<br>
//	 * It will fetch data to see the correct result.<br>
//	 * Than it will create tests for the same scenario same name of tests to see
//	 * the same number of groups returned.<br>
//	 * It will create the same tests for a DIFFERENT scenario scenarioY same
//	 * tests name,<br>
//	 * It will now fetch once by testName to see same num Of Groups and once by
//	 * ScenarioName + testName to get 2XnumOfGroups.<br>
//	 * It will fetch data with TimeRange and will check if all test retrieved
//	 * are within the given timeRange.<br>
//	 * 
//	 */
//	@org.junit.Test
//	public void testGroupByNameSameScenario() {
//		// Clean Database
//		new AdminUser().cleanDatabase();
//
//		int numOfGroups = 10;
//		new JSystemGUI().publishTestGroupByName(numOfGroups, "Scenario1");
//		// without sorting column
//		List<TestSummary> testGroupByName = new TestGroupSimulator().getTestGroupByNameAndScenario();
//
//		Assert.assertEquals(numOfGroups, testGroupByName.size());
//
//		TestQuery testQuery = new TestQuery();
//		testQuery.setGroupBy(1);
//		testQuery.setChunk(new Chunk(0, 100));
//		testQuery.setSortingColumn(new SortingColumn("testName", true));
//
//		testGroupByName = new TestGroupSimulator().getTestByGroup(testQuery);
//		Assert.assertEquals(numOfGroups, testGroupByName.size());
//
//		// Assert each test has the same name as the testName in testSummary
//		// object. Without a scenario Name.
//		for (TestSummary testSummary : testGroupByName) {
//			String testName = testSummary.getTestName();
//			TestSummaryQuery testSummaryQuery = new TestSummaryQuery(testQuery, testSummary);
//			testSummary.setScenarioName("");
//			testQuery.setGroupBy(0);
//			List<Test> testByTestSummary = new TestGroupSimulator().getTestByTestSummary(testSummaryQuery);
//			for (Test test : testByTestSummary) {
//				Assert.assertEquals(test.getTestDescription().getTestName(), testName);
//			}
//		}
//		testGroupByName = new TestGroupSimulator().getTestGroupByNameAndScenario();
//		// Assert each test has the same name as the testName in testSummary
//		// object. include a scenario Name.
//		for (TestSummary testSummary : testGroupByName) {
//			testQuery.setGroupBy(0);
//			String testName = testSummary.getTestName();
//			String scenarioName = testSummary.getScenarioName();
//			TestSummaryQuery testSummaryQuery = new TestSummaryQuery(testQuery, testSummary);
//			List<Test> testByTestSummary = new TestGroupSimulator().getTestByTestSummary(testSummaryQuery);
//			for (Test test : testByTestSummary) {
//				Assert.assertEquals(test.getTestDescription().getTestName(), testName);
//				Assert.assertEquals(test.getScenario().getScenarioName(), scenarioName);
//			}
//		}
//
//		// These next lines are in order to check that the numOfGroups returned
//		// are the same as all was under same scenario.
//		new JSystemGUI().publishTestGroupByName(numOfGroups, "Scenario1");
//		testGroupByName = new TestGroupSimulator().getTestGroupByNameAndScenario();
//		Assert.assertEquals(numOfGroups, testGroupByName.size());
//
//		// These next lines are in order to check that the numOfGroups returned
//		// are NOT the same as we added a new scenario.
//		new JSystemGUI().publishTestGroupByName(numOfGroups, "Scenario2");
//		testGroupByName = new TestGroupSimulator().getTestGroupByNameAndScenario();
//		Assert.assertEquals(numOfGroups + numOfGroups, testGroupByName.size());
//
//		testGroupByName = new TestGroupSimulator().getTestGroupByNameAndScenario();
//		// Assert each test has the same name as the testName in testSummary
//		// object. include a scenario Name.
//		for (TestSummary testSummary : testGroupByName) {
//			testQuery.setGroupBy(0);
//			String testName = testSummary.getTestName();
//			String scenarioName = testSummary.getScenarioName();
//			TestSummaryQuery testSummaryQuery = new TestSummaryQuery(testQuery, testSummary);
//			List<Test> testByTestSummary = new TestGroupSimulator().getTestByTestSummary(testSummaryQuery);
//			for (Test test : testByTestSummary) {
//				Assert.assertEquals(test.getTestDescription().getTestName(), testName);
//				Assert.assertEquals(test.getScenario().getScenarioName(), scenarioName);
//			}
//		}
//
//		// Get testByTestSummary with TimeRange
//		TimeRange timeRange = new TimeRange(1262296800000L, 1291154400000L);// 1.1.10
//																			// -
//																			// 1.12.10
//		testQuery.setTimeRange(timeRange);
//
//		// Assert each test has the same name as the testName in testSummary
//		// object. include a scenario Name. include TimeRange
//		for (TestSummary testSummary : testGroupByName) {
//			testQuery.setGroupBy(0);
//			String testName = testSummary.getTestName();
//			String scenarioName = testSummary.getScenarioName();
//			TestSummaryQuery testSummaryQuery = new TestSummaryQuery(testQuery, testSummary);
//			List<Test> testByTestSummary = new TestGroupSimulator().getTestByTestSummary(testSummaryQuery);
//			for (Test test : testByTestSummary) {
//				Assert.assertEquals(test.getTestDescription().getTestName(), testName);
//				Assert.assertEquals(test.getScenario().getScenarioName(), scenarioName);
//				Assert.assertTrue(test.getEndTime() < timeRange.getUpBoundDate());
//				Assert.assertTrue(test.getStartTime() > timeRange.getLowBoundDate());
//			}
//		}
//
//		// Assert each test has the same name as the testName in testSummary
//		// object. include TimeRange
//		for (TestSummary testSummary : testGroupByName) {
//			testQuery.setGroupBy(0);
//			String testName = testSummary.getTestName();
//			testSummary.setScenarioName("");
//			TestSummaryQuery testSummaryQuery = new TestSummaryQuery(testQuery, testSummary);
//			List<Test> testByTestSummary = new TestGroupSimulator().getTestByTestSummary(testSummaryQuery);
//			for (Test test : testByTestSummary) {
//				Assert.assertEquals(test.getTestDescription().getTestName(), testName);
//				Assert.assertTrue(test.getEndTime() < timeRange.getUpBoundDate());
//				Assert.assertTrue(test.getStartTime() > timeRange.getLowBoundDate());
//			}
//		}
//	}
//
//	// List<TestSummary> testGroupByNameAndScenario = new
//	// TestGroupSimulator().getTestGroupByNameAndScenario();
//	// List<TestSummary> testGroupByNameAndScenarioAndParams = new
//	// TestGroupSimulator().getTestGroupByNameAndScenarioAndParams();
//}
