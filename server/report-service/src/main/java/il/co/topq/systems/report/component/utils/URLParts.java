package il.co.topq.systems.report.component.utils;

public class URLParts {

	// context
	// public static final String URI = "http://localhost:9998/report-service";
	public static final String URI = "http://localhost:8080/report-service/report";
	public static final String APPLICATION_CONTEXT = "report-service";

	// Authorization
	public static final String LOGIN_URL = "/authorization/login";

	// User
	public static final String CREATE_DEFAULT_USER = "/user/createDefaultUser";
	public static final String CREATE_NEW_USER = "/user/create";
	public static final String UPDATE_USER = "/user/update";
	public static final String DELETE_USER = "/user/delete/";
	public static final String COUNT_USERS = "/user/count";
	public static final String GET_USER_BY_ID = "/user/";
	public static final String GET_USER_ROLES = "/user/userRoles";
	public static final String GET_ALL_USERS = "/user/allUsers";

	// Scenario
	public static final String CREATE_SCENARIO_URL = "/scenario/create/";
	public static final String GET_SCENARIO_URL = "/scenario/";
	public static final String DELETE_SCENARIO_URL = "/scenario/delete/";
	public static final String SIZE_OF_SCENARIO_QUERY_URL = "/scenario/querySize/";
	public static final String SIZE_OF_SCENARIO_TESTS_URL = "/scenario/scenarioTestsSize/";
	public static final String SCENARIO_TESTS_URL = "/scenario/tests/";
	public static final String CREATE_SCENARIO_FROM_XML = "/scenario/reportXML/";

	// Test
	public static final String GET_TEST_URL = "/test/";
	public static final String COUNT_TESTS_URL = "/test/count/";
	public static final String GET_TEST_SCENARIO = "/test/testScenario/";
	public static final String DELETE_TESTS_URL = "/test/delete/";
	public static final String SIZE_OF_TESTS_QUERY_URL = "/test/querySize/";
	public static final String UPDATE_TEST = "/test/update/";

	// ScenarioProperty
	public static final String SCENARIO_UNIQUE_PROPERTY_KEYS_URL = "/scenarioProperties/uniqueKeys/";
	public static final String SCENARIO_LAST_VERSION_PROPERTY_URL = "/scenarioProperties/lastVersionExecution/";

	// TestProperty
	public static final String TEST_UNIQUE_PROPERTY_KEYS_URL = "/testProperties/uniqueKeys/";

	// ScenarioCustomReport
	public static final String CREATE_SCENARIO_CUSTOM_REPORT = "/scenarioCustomReport/";
	public static final String GET_SCENARIO_CUSTOM_REPORT = "/scenarioCustomReport/";
	public static final String DELETE_SCENARIO_CUSTOM_REPORT = "/scenarioCustomReport/delete/";
	public static final String SCENARIO_CUSTOM_REPORT_VALUES = "/scenarioCustomReport/values/";
	public static final String UPDATE_SCENARIO_CUSTOM_REPORT = "/scenarioCustomReport/update/";
	public static final String SCENARIO_CUSTOM_REPORT_CHUNK = "/scenarioCustomReport/chunk/";
	public static final String SCENARIO_CUSTOM_REPORT_QUERY_SIZE = "/scenarioCustomReport/querySize/";

	// TestCustomReport
	public static final String GET_TEST_CUSTOM_REPORT = "/testCustomReport/";
	public static final String CREATE_TEST_CUSTOM_REPORT = "/testCustomReport/";
	public static final String DELETE_TEST_CUSTOM_REPORT = "/testCustomReport/delete/";
	public static final String TEST_CUSTOM_REPORT_VALUES = "/testCustomReport/values/";
	public static final String TEST_CUSTOM_REPORT_QUERY_SIZE = "/testCustomReport/querySize/";
	public static final String UPDATE_TEST_CUSTOM_REPORT = "/testCustomReport/update/";
	public static final String TEST_CUSTOM_REPORT_CHUNK = "/testCustomReport/chunk/";

	// upload
	public static final String UPLOAD_FILE = "/file/upload/";

}
