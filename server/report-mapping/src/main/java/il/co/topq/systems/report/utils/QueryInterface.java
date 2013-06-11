package il.co.topq.systems.report.utils;

public interface QueryInterface {

	enum TIME_FIELD {
		// TEST_TIME_FIELD("published_test_01.startTime");
		TEST_TIME_FIELD("startTime");

		private String value;

		TIME_FIELD(String startTime) {
			this.value = startTime;
		}

		public String valueOf() {
			return value;
		}
	}

	String TEST_TABLE_NAME = "published_test_01 ";
	String TEST_NAME_FIELD = "testName ";
	String SCENARIO_TABLE_NAME = "published_runs_01 ";

	/**
	 * this will be used to construct a query for get Tests by filters.
	 */
	String CREATE_TESTS_BY_FILTER_QUERY = "select published_test_01.testIndex "
			+ "from published_test_01 join test_properties join test_properties_has_published_test_01 "
			+ "on published_test_01.testIndex = test_properties_has_published_test_01.published_test_01_testIndex "
			+ "where " + "("
			+ "(test_properties.index1 = test_properties_has_published_test_01.test_properties_index1)and"
			+ "(published_test_01.testIndex = test_properties_has_published_test_01.published_test_01_testIndex)";

	/**
	 * this will be used to construct a query for get Scenarios by filters.
	 */
	String CREATE_SCENARIOS_BY_FILTER_QUERY = "select published_runs_01.runIndex "
			+ "from published_runs_01 join scenario_properties join scenario_properties_has_published_runs_01 "
			+ "on published_runs_01.runIndex = scenario_properties_has_published_runs_01.published_runs_01_runIndex "
			+ "where " + "("
			+ "(scenario_properties.index1 = scenario_properties_has_published_runs_01.scenario_properties_index1)and"
			+ "(published_runs_01.runIndex = scenario_properties_has_published_runs_01.published_runs_01_runIndex)";

	// Joining the 3 table of test (package, description(name) and the main one which is published_test_01)
	String FROM_JOIN_TEST_WITH_PACKAGE_AND_NAME = "published_runs_01, test_description inner join published_test_01 on testId = testIndex inner join package_description on packageId = testPackage";

	// Select field for creating TestSummary. Mapping selections to TestSummary fields)
	// Require joining all 3 test main (package, description(name) and the main one which is published_test_01)
	String TEST_SUMMARY_FIELDS = "test_description.testName as testName, "
			+ "packageName, published_runs_01.scenarioName, " + "count(published_test_01.testName) as total, "
			+ "published_test_01.params as params," + "sum(status=0) as pass, " + "sum(status=1) as fail, "
			+ "sum(status=2) as warning ";
}
