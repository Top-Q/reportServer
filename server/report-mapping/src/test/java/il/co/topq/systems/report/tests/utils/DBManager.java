//package il.co.topq.systems.report.tests.utils;
//
//import il.co.topq.systems.report.beans.ScenarioBean;
//import il.co.topq.systems.report.common.infra.ReportLogger;
//import il.co.topq.systems.report.common.interfaces.ScenarioService;
//import il.co.topq.systems.report.common.model.PackageDescription;
//import il.co.topq.systems.report.common.model.Scenario;
//import il.co.topq.systems.report.common.model.ScenarioProperty;
//import il.co.topq.systems.report.common.model.Test;
//import il.co.topq.systems.report.common.model.TestDescription;
//import il.co.topq.systems.report.common.model.TestProperty;
//import il.co.topq.systems.report.utils.RandomData;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Random;
//import java.util.Set;
//
//import org.apache.log4j.Logger;
//
//public class DBManager {
//	private Logger log = ReportLogger.getInstance().getLogger(this.getClass());
//	// Scenario Property TYPE: Sanity, Nightly, Regression
//	ScenarioProperty typeSanity = new ScenarioProperty("Type", "Sanity");
//	ScenarioProperty typeNightly = new ScenarioProperty("Type", "Nightly");
//	ScenarioProperty typeRegression = new ScenarioProperty("Type", "Regression");
//
//	// Scenario Property TYPE: Sanity, Nightly, Regression
//
//	// Scenario Property Version: 1.1.2, 1.1.3, 1.1.4
//	ScenarioProperty version1_1_2 = new ScenarioProperty("Version", "1.1.2");
//	ScenarioProperty version1_1_3 = new ScenarioProperty("Version", "1.1.3");
//	ScenarioProperty version1_1_4 = new ScenarioProperty("Version", "1.1.4");
//	// Scenario Property Version: 1.1.2, 1.1.3, 1.1.4
//
//	// Scenario Property Station
//	ScenarioProperty station1 = new ScenarioProperty("Station", "192.168.20.3");
//	ScenarioProperty station2 = new ScenarioProperty("Station", "168.20.1.74");
//	ScenarioProperty station3 = new ScenarioProperty("Station", "192.16.23.12");
//	ScenarioProperty station4 = new ScenarioProperty("Station", "172.25.12.9");
//	ScenarioProperty station5 = new ScenarioProperty("Station", "168.23.17.2");
//	// Scenario Property Station
//
//	// Scenario Property User
//	ScenarioProperty userEran = new ScenarioProperty("User", "Eran");
//	ScenarioProperty userTomer = new ScenarioProperty("User", "Tomer");
//	ScenarioProperty userLimor = new ScenarioProperty("User", "Limor");
//	ScenarioProperty userItai = new ScenarioProperty("User", "Itai");
//	ScenarioProperty userMoshi = new ScenarioProperty("User", "Moshi");
//	// Scenario Property User
//
//	// Scenario Property Build
//	ScenarioProperty build1 = new ScenarioProperty("Build", "1");
//	ScenarioProperty build2 = new ScenarioProperty("Build", "2");
//	ScenarioProperty build3 = new ScenarioProperty("Build", "3");
//	ScenarioProperty build4 = new ScenarioProperty("Build", "4");
//	ScenarioProperty build5 = new ScenarioProperty("Build", "5");
//	// Scenario Property Build
//
//	// Scenario Property Platform
//	ScenarioProperty PlatformWin = new ScenarioProperty("Platform", "win32");
//	ScenarioProperty PlatformLinux = new ScenarioProperty("Platform", "Linux");
//	// Scenario Property Platform
//
//	// Scenario Property Product
//	ScenarioProperty product1 = new ScenarioProperty("Product", "P1");
//	ScenarioProperty product2 = new ScenarioProperty("Product", "P2");
//	ScenarioProperty product3 = new ScenarioProperty("Product", "P3");
//	ScenarioProperty product4 = new ScenarioProperty("Product", "P4");
//	ScenarioProperty product5 = new ScenarioProperty("Product", "P5");
//	// Scenario Property Product
//
//	// Test Property Level
//	TestProperty levelCritical = new TestProperty("Level", "Critical");
//	TestProperty levelRegular = new TestProperty("Level", "Regular");
//	// Test Property Level
//
//	// Test Property Bug
//	TestProperty bug1 = new TestProperty("Bug", "0001");
//	TestProperty bug2 = new TestProperty("Bug", "0002");
//	// Test Property Bug
//
//	// Test Property: Automation Error
//	TestProperty automationErrorTrue = new TestProperty("Automation Error", "True");
//	TestProperty automationErrorFalse = new TestProperty("Automation Error", "False");
//	// Test Property: Automation Error
//
//	// Test Property Build
//	TestProperty testBuild1 = new TestProperty("Build", "1");
//	TestProperty testBuild2 = new TestProperty("Build", "2");
//	TestProperty testBuild3 = new TestProperty("Build", "3");
//	TestProperty testBuild4 = new TestProperty("Build", "4");
//	TestProperty testBuild5 = new TestProperty("Build", "5");
//	// Test Property Build
//
//	// Scenario Property Version: 1.1.2, 1.1.3, 1.1.4
//	TestProperty testVersion1_1_2 = new TestProperty("Version", "1.1.2");
//	TestProperty testVersion1_1_3 = new TestProperty("Version", "1.1.3");
//	TestProperty testVersion1_1_4 = new TestProperty("Version", "1.1.4");
//	// Scenario Property Version: 1.1.2, 1.1.3, 1.1.4
//
//	ScenarioService scenarioServiceBean = new ScenarioBean();
//
//	@org.junit.Test
//    public void createDBLoop(){
//    	try{
//	    	for (int i=0; i<20; i++){
//	    		createDB();
//	    	}
//    	}catch (Exception e) {
//    		log.error(e);
//    		e.printStackTrace();
//		}
//    }
//
//	/**
//	 * Scenario Properties:<br>
//	 * Type, Build, Version, Platform, Station, Product, User
//	 * <p/>
//	 * Test Properties:<br>
//	 * Level, Bug, AutomationError, Version, Build
//	 */
//	@org.junit.Test
//	public void createDB() {
//
//		// new AdminUser().cleanDatabase();
//		Scenario sanity1 = RandomData.getRandomEmptyScenario();
//		Scenario sanity2 = RandomData.getRandomEmptyScenario();
//		Scenario sanity3 = RandomData.getRandomEmptyScenario();
//		Scenario sanity4 = RandomData.getRandomEmptyScenario();
//		Scenario sanity5 = RandomData.getRandomEmptyScenario();
//
//		Scenario nightly1 = RandomData.getRandomEmptyScenario();
//		Scenario nightly2 = RandomData.getRandomEmptyScenario();
//		Scenario nightly3 = RandomData.getRandomEmptyScenario();
//		Scenario nightly4 = RandomData.getRandomEmptyScenario();
//		Scenario nightly5 = RandomData.getRandomEmptyScenario();
//
//		Scenario regression1 = RandomData.getRandomEmptyScenario();
//		Scenario regression2 = RandomData.getRandomEmptyScenario();
//		Scenario regression3 = RandomData.getRandomEmptyScenario();
//		Scenario regression4 = RandomData.getRandomEmptyScenario();
//		Scenario regression5 = RandomData.getRandomEmptyScenario();
//
//		// Build Scenario1
//		Set<ScenarioProperty> scenarioProperties1 = new HashSet<ScenarioProperty>();
//		scenarioProperties1.add(typeSanity);
//		scenarioProperties1.add(version1_1_2);
//		sanity1.setScenarioProperties(scenarioProperties1);
//		sanity1.setScenarioName("Sanity");
//		sanity1.setRunTest(5);
//		sanity1.setFailTests(0);
//		sanity1.setWarningTests(2);
//		sanity1.setSuccessTests(3);
//		sanity1.setDescription("This is a Sanity scenario");
//		sanity1.setDuration(34.12);
//		setHTMLDir(sanity1);
//
//		sanity1.setTests(getSetOfTestsForSanityAndNightly(sanity1));
//		createScenario(sanity1);
//
//		// Build Scenario2
//		Set<ScenarioProperty> scenarioProperties2 = new HashSet<ScenarioProperty>();
//		scenarioProperties1.add(typeSanity);
//		scenarioProperties1.add(version1_1_3);
//		sanity2.setScenarioProperties(scenarioProperties2);
//		sanity2.setScenarioName("Sanity");
//		sanity2.setRunTest(5);
//		sanity2.setFailTests(1);
//		sanity2.setWarningTests(2);
//		sanity2.setSuccessTests(2);
//		sanity2.setDescription("This is a Sanity scenario");
//		sanity2.setDuration(28.23);
//		setHTMLDir(sanity2);
//		sanity2.setTests(getSetOfTestsForSanityAndNightly(sanity2));
//		createScenario(sanity2);
//
//		// Build Scenario3
//		Set<ScenarioProperty> scenarioProperties3 = new HashSet<ScenarioProperty>();
//		scenarioProperties3.add(typeSanity);
//		scenarioProperties3.add(version1_1_3);
//		sanity3.setScenarioProperties(scenarioProperties3);
//		sanity3.setScenarioName("Sanity");
//		sanity3.setRunTest(5);
//		sanity3.setFailTests(3);
//		sanity3.setWarningTests(2);
//		sanity3.setSuccessTests(0);
//		sanity3.setDescription("This is a Sanity scenario");
//		sanity3.setDuration(27.324);
//		setHTMLDir(sanity3);
//		sanity3.setTests(getSetOfTestsForSanityAndNightly(sanity3));
//		createScenario(sanity3);
//
//		// Build Scenario4
//		Set<ScenarioProperty> scenarioProperties4 = new HashSet<ScenarioProperty>();
//		scenarioProperties4.add(typeSanity);
//		scenarioProperties4.add(version1_1_4);
//		sanity4.setScenarioProperties(scenarioProperties4);
//		sanity4.setScenarioName("Sanity");
//		sanity4.setRunTest(5);
//		sanity4.setFailTests(1);
//		sanity4.setWarningTests(1);
//		sanity4.setSuccessTests(3);
//		sanity4.setDescription("This is a Sanity scenario");
//		sanity4.setDuration(25.25);
//		setHTMLDir(sanity4);
//		sanity4.setTests(getSetOfTestsForSanityAndNightly(sanity4));
//		createScenario(sanity4);
//
//		// Build Scenario5
//		Set<ScenarioProperty> scenarioProperties5 = new HashSet<ScenarioProperty>();
//		scenarioProperties5.add(typeSanity);
//		scenarioProperties5.add(version1_1_2);
//		sanity5.setScenarioProperties(scenarioProperties5);
//		sanity5.setScenarioName("Sanity");
//		sanity5.setRunTest(5);
//		sanity5.setFailTests(0);
//		sanity5.setWarningTests(0);
//		sanity5.setSuccessTests(5);
//		sanity5.setDescription("This is a Sanity scenario");
//		sanity5.setDuration(35.535);
//		setHTMLDir(sanity5);
//		sanity5.setTests(getSetOfTestsForSanityAndNightly(sanity5));
//		createScenario(sanity5);
//
//		// Build Scenario6
//		Set<ScenarioProperty> scenarioProperties6 = new HashSet<ScenarioProperty>();
//		scenarioProperties6.add(typeNightly);
//		scenarioProperties6.add(version1_1_2);
//		nightly1.setScenarioProperties(scenarioProperties6);
//		nightly1.setScenarioName("Nightly");
//		nightly1.setRunTest(5);
//		nightly1.setFailTests(5);
//		nightly1.setWarningTests(0);
//		nightly1.setSuccessTests(0);
//		nightly1.setDescription("This is a Nightly scenario");
//		nightly1.setDuration(38.125);
//		setHTMLDir(nightly1);
//		nightly1.setTests(getSetOfTestsForSanityAndNightly(nightly1));
//		createScenario(nightly1);
//
//		// Build Scenario7
//		Set<ScenarioProperty> scenarioProperties7 = new HashSet<ScenarioProperty>();
//		scenarioProperties7.add(typeNightly);
//		scenarioProperties7.add(version1_1_4);
//		nightly2.setScenarioProperties(scenarioProperties7);
//		nightly2.setScenarioName("Nightly");
//		nightly2.setRunTest(5);
//		nightly2.setFailTests(2);
//		nightly2.setWarningTests(2);
//		nightly2.setSuccessTests(1);
//		nightly2.setDescription("This is a Nightly scenario");
//		nightly2.setDuration(55.12);
//		setHTMLDir(nightly2);
//		nightly2.setTests(getSetOfTestsForSanityAndNightly(nightly2));
//		createScenario(nightly2);
//
//		// Build Scenario8
//		Set<ScenarioProperty> scenarioProperties8 = new HashSet<ScenarioProperty>();
//		scenarioProperties8.add(typeNightly);
//		scenarioProperties8.add(version1_1_4);
//		nightly3.setScenarioProperties(scenarioProperties8);
//		nightly3.setScenarioName("Nightly");
//		nightly3.setRunTest(5);
//		nightly3.setFailTests(2);
//		nightly3.setWarningTests(3);
//		nightly3.setSuccessTests(0);
//		nightly3.setDescription("This is a Nightly scenario");
//		nightly3.setDuration(48.902);
//		setHTMLDir(nightly3);
//		nightly3.setTests(getSetOfTestsForSanityAndNightly(nightly3));
//		createScenario(nightly3);
//
//		// Build Scenario9
//		Set<ScenarioProperty> scenarioProperties9 = new HashSet<ScenarioProperty>();
//		scenarioProperties9.add(typeNightly);
//		scenarioProperties9.add(version1_1_3);
//		nightly4.setScenarioProperties(scenarioProperties9);
//		nightly4.setScenarioName("Nightly");
//		nightly4.setRunTest(5);
//		nightly4.setFailTests(1);
//		nightly4.setWarningTests(4);
//		nightly4.setSuccessTests(0);
//		nightly4.setDescription("This is a Nightly scenario");
//		nightly4.setDuration(51.63);
//		setHTMLDir(nightly4);
//		nightly4.setTests(getSetOfTestsForSanityAndNightly(nightly4));
//		createScenario(nightly4);
//
//		// Build Scenario10
//		Set<ScenarioProperty> scenarioProperties10 = new HashSet<ScenarioProperty>();
//		scenarioProperties10.add(typeNightly);
//		scenarioProperties10.add(version1_1_3);
//		nightly5.setScenarioProperties(scenarioProperties10);
//		nightly5.setScenarioName("Nightly");
//		nightly5.setRunTest(5);
//		nightly5.setFailTests(2);
//		nightly5.setWarningTests(0);
//		nightly5.setSuccessTests(3);
//		nightly5.setDescription("This is a Nightly scenario");
//		nightly5.setDuration(49.03);
//		setHTMLDir(nightly5);
//		nightly5.setTests(getSetOfTestsForSanityAndNightly(nightly5));
//		createScenario(nightly5);
//
//		// Build Scenario11
//		regression1.setScenarioProperties(getScenarioProperties(typeRegression, build1, version1_1_2));
//		regression1.setScenarioName("Regression");
//		regression1.setRunTest(5);
//		regression1.setFailTests(1);
//		regression1.setWarningTests(1);
//		regression1.setSuccessTests(3);
//		regression1.setDescription("This is a Regression scenario");
//		regression1.setDuration(49.03);
//		setHTMLDir(regression1);
//		regression1.setTests(getScenarioTests(regression1, testVersion1_1_2, testBuild1));
//		createScenario(regression1);
//
//		// Build Scenario12
//		regression2.setScenarioProperties(getScenarioProperties(typeRegression, build2, version1_1_2));
//		regression2.setScenarioName("Regression");
//		regression2.setRunTest(5);
//		regression2.setFailTests(1);
//		regression2.setWarningTests(1);
//		regression2.setSuccessTests(3);
//		regression2.setDescription("This is a Regression scenario");
//		regression2.setDuration(14.61);
//		setHTMLDir(regression2);
//		regression2.setTests(getScenarioTests(regression2, testVersion1_1_2, testBuild2));
//		createScenario(regression2);
//
//		// Build Scenario13
//		regression3.setScenarioProperties(getScenarioProperties(typeRegression, build3, version1_1_3));
//		regression3.setScenarioName("Regression");
//		regression3.setRunTest(5);
//		regression3.setFailTests(5);
//		regression3.setWarningTests(0);
//		regression3.setSuccessTests(0);
//		regression3.setDescription("This is a Regression scenario");
//		regression3.setDuration(19.03);
//		setHTMLDir(regression3);
//		regression3.setTests(getScenarioTests(regression3, testVersion1_1_3, testBuild3));
//		createScenario(regression3);
//
//		// Build Scenario14
//		regression4.setScenarioProperties(getScenarioProperties(typeRegression, build4, version1_1_4));
//		regression4.setScenarioName("Regression");
//		regression4.setRunTest(5);
//		regression4.setFailTests(0);
//		regression4.setWarningTests(0);
//		regression4.setSuccessTests(5);
//		regression4.setDescription("This is a Regression scenario");
//		regression4.setDuration(51.58);
//		setHTMLDir(regression4);
//		regression4.setTests(getScenarioTests(regression4, testVersion1_1_4, testBuild4));
//		createScenario(regression4);
//
//		// Build Scenario15
//		regression5.setScenarioProperties(getScenarioProperties(typeRegression, build5, version1_1_4));
//		regression5.setScenarioName("Regression");
//		regression5.setRunTest(5);
//		regression5.setFailTests(2);
//		regression5.setWarningTests(1);
//		regression5.setSuccessTests(2);
//		regression5.setDescription("This is a Regression scenario");
//		regression5.setDuration(33.03);
//		setHTMLDir(regression5);
//		regression5.setTests(getScenarioTests(regression5, testVersion1_1_4, testBuild5));
//		createScenario(regression5);
//
//		//
//		// // Build Scenario16
//		// regression5.setScenarioProperties(getScenarioProperties(typeRegression,
//		// build5, version1_1_4));
//		// regression5.setScenarioName("Regression");
//		// regression5.setRunTest(5);
//		// regression5.setFailTests(2);
//		// regression5.setWarningTests(1);
//		// regression5.setSuccessTests(2);
//		// regression5.setDescription("This is a Regression scenario");
//		// regression5.setDuration(33.03);
//		// setHTMLDir(regression5);
//		// Set<Test> randomTests = RandomData.getRandomTests(3);
//		// Set<TestProperty> s = new HashSet<TestProperty>();
//		// s.add(new TestProperty("Build","3"));
//		// for (Test test : randomTests) {
//		// test.setTestProperties(s);
//		// }
//		// regression5.setTests(randomTests);
//		// createScenario(regression5);
//
//	}
//
//	private Set<Test> getScenarioTests(Scenario scenario, TestProperty version, TestProperty build) {
//
//		Set<Test> tests = new HashSet<Test>();
//		Integer total = scenario.getRunTest();
//		Integer failTests = scenario.getFailTests();
//		Integer warningTests = scenario.getWarningTests();
//		Integer successTests = scenario.getSuccessTests();
//
//		for (int i = 0; i < failTests; i++) {
//			tests.add(getFailedTest(scenario, "Test" + total, version, build));
//			total--;
//		}
//		for (int i = 0; i < warningTests; i++) {
//			tests.add(getWarnedTest(scenario, "Test" + total, version, build));
//			total--;
//		}
//		for (int i = 0; i < successTests; i++) {
//			tests.add(getSucceedTest(scenario, "Test" + total, version, build));
//			total--;
//		}
//
//		return tests;
//	}
//
//	private String getTestDocumentation() {
//
//		return "This is a test documentation";
//	}
//
//	private String getTestMessage() {
//		return "This is a test message";
//	}
//
//	public void setHTMLDir(Scenario scenario) {
//		scenario.setHtmlDir(Long.toString(System.currentTimeMillis()));
//
//	}
//
//	public void createScenario(Scenario scenario) {
//		scenarioServiceBean.createScenario(scenario);
//	}
//
//	@org.junit.Test
//	public void createScenarios() {
//		for (int i = 0; i < 15; i++) {
//			createRandomEmptyScenario();
//		}
//	}
//
//	@org.junit.Test
//	public void createRandomEmptyScenario() {
//
//		scenarioServiceBean.createScenario(RandomData.getRandomEmptyScenario());
//	}
//
//	public String getFailCause() {
//
//		return "This is a Fail cause";
//	}
//
//	public TestDescription createTestDescription(String testName) {
//
//		TestDescription testDescription = new TestDescription();
//		testDescription.setTestDescription(testName + " Description");
//		testDescription.setTestName(testName);
//
//		return testDescription;
//	}
//
//	public PackageDescription getPackageDescription(Scenario scenario) {
//
//		PackageDescription packageDescription = new PackageDescription();
//		packageDescription.setPackageName("package");
//		packageDescription.setPackageDescription(scenario.getScenarioName() + " Description");
//		return packageDescription;
//	}
//
//	public Set<Test> getSetOfTestsForSanityAndNightly(Scenario scenario) {
//
//		// Test 1
//		Test test1 = RandomData.getRandomTest();
//		test1.setMessage(getTestMessage());
//		test1.setDocumentation(getTestDocumentation());
//		test1.setFailCause(getFailCause());
//		test1.setPackageDescription(getPackageDescription(scenario));
//		test1.setTestDescription(createTestDescription("Test1"));
//		test1.setScenario(scenario);
//		test1.setDuration(13.23);
//		test1.setStatus((short) (0));
//		Set<TestProperty> testProperties1 = new HashSet<TestProperty>();
//		testProperties1.add(levelCritical);
//		testProperties1.add(bug1);
//		testProperties1.add(automationErrorFalse);
//		testProperties1.add(testVersion1_1_2);
//		testProperties1.add(testBuild1);
//		test1.setTestProperties(testProperties1);
//
//		// Test 2
//		Test test2 = RandomData.getRandomTest();
//		test2.setMessage(getTestMessage());
//		test2.setDocumentation(getTestDocumentation());
//		test2.setFailCause(getFailCause());
//		test2.setPackageDescription(getPackageDescription(scenario));
//		test2.setTestDescription(createTestDescription("Test2"));
//		test2.setScenario(scenario);
//		test2.setDuration(19.81);
//		test2.setStatus((short) (0));
//		Set<TestProperty> testProperties2 = new HashSet<TestProperty>();
//		testProperties2.add(levelRegular);
//		testProperties2.add(bug2);
//		testProperties2.add(automationErrorTrue);
//		testProperties2.add(testVersion1_1_2);
//		testProperties2.add(testBuild2);
//		test2.setTestProperties(testProperties2);
//
//		// Test3
//		Test test3 = RandomData.getRandomTest();
//		test3.setMessage(getTestMessage());
//		test3.setDocumentation(getTestDocumentation());
//		test3.setFailCause(getFailCause());
//		test3.setPackageDescription(getPackageDescription(scenario));
//		test3.setTestDescription(createTestDescription("Test3"));
//		test3.setScenario(scenario);
//		test3.setDuration(23.45);
//		test3.setStatus((short) (0));
//		Set<TestProperty> testProperties3 = new HashSet<TestProperty>();
//		testProperties3.add(levelRegular);
//		testProperties3.add(automationErrorTrue);
//		testProperties3.add(testVersion1_1_3);
//		testProperties3.add(testBuild3);
//		test3.setTestProperties(testProperties3);
//
//		// Test4
//		Test test4 = RandomData.getRandomTest();
//		test4.setMessage(getTestMessage());
//		test4.setDocumentation(getTestDocumentation());
//		test4.setFailCause(getFailCause());
//		test4.setPackageDescription(getPackageDescription(scenario));
//		test4.setTestDescription(createTestDescription("Test4"));
//		test4.setScenario(scenario);
//		test4.setDuration(15.41);
//		test4.setStatus((short) (2));
//		Set<TestProperty> testProperties4 = new HashSet<TestProperty>();
//		testProperties4.add(levelRegular);
//		testProperties4.add(automationErrorFalse);
//		testProperties4.add(testVersion1_1_4);
//		testProperties4.add(testBuild4);
//		test4.setTestProperties(testProperties4);
//
//		// Test5
//		Test test5 = RandomData.getRandomTest();
//		test5.setMessage(getTestMessage());
//		test5.setDocumentation(getTestDocumentation());
//		test5.setFailCause(getFailCause());
//		test5.setPackageDescription(getPackageDescription(scenario));
//		test5.setTestDescription(createTestDescription("Test5"));
//		test5.setScenario(scenario);
//		test5.setDuration(32.67);
//		test5.setStatus((short) (2));
//		Set<TestProperty> testProperties5 = new HashSet<TestProperty>();
//		testProperties5.add(levelCritical);
//		testProperties5.add(automationErrorFalse);
//		testProperties5.add(testVersion1_1_4);
//		testProperties5.add(testBuild5);
//		test5.setTestProperties(testProperties5);
//
//		Set<Test> tests1 = new HashSet<Test>();
//		tests1.add(test1);
//		tests1.add(test2);
//		tests1.add(test3);
//		tests1.add(test4);
//		tests1.add(test5);
//
//		return tests1;
//
//	}
//
//	public Test getFailedTest(Scenario scenario, String testName, TestProperty version, TestProperty build) {
//		Test test1 = RandomData.getRandomTest();
//		test1.setMessage("Test Failed");
//		test1.setDocumentation(getTestDocumentation());
//		test1.setFailCause(getFailCause());
//		test1.setPackageDescription(getPackageDescription(scenario));
//		test1.setTestDescription(createTestDescription(testName));
//		test1.setScenario(scenario);
//		test1.setDuration(13.23);
//		test1.setStatus((short) (1));
//		Set<TestProperty> testProperties1 = new HashSet<TestProperty>();
//		testProperties1.add(levelCritical);
//		testProperties1.add(bug1);
//		testProperties1.add(automationErrorFalse);
//		testProperties1.add(version);
//		testProperties1.add(build);
//		test1.setTestProperties(testProperties1);
//
//		return test1;
//	}
//
//	public Test getSucceedTest(Scenario scenario, String testName, TestProperty version, TestProperty build) {
//		Test test1 = RandomData.getRandomTest();
//		test1.setMessage("Test Succeeded");
//		test1.setDocumentation(getTestDocumentation());
//		test1.setFailCause("");
//		test1.setPackageDescription(getPackageDescription(scenario));
//		test1.setTestDescription(createTestDescription(testName));
//		test1.setScenario(scenario);
//		test1.setDuration(13.23);
//		test1.setStatus((short) (0));
//		Set<TestProperty> testProperties1 = new HashSet<TestProperty>();
//		testProperties1.add(levelCritical);
//		testProperties1.add(version);
//		testProperties1.add(build);
//		test1.setTestProperties(testProperties1);
//
//		return test1;
//	}
//
//	public Test getWarnedTest(Scenario scenario, String testName, TestProperty version, TestProperty build) {
//		Test test1 = RandomData.getRandomTest();
//		test1.setMessage("Test Warned");
//		test1.setDocumentation(getTestDocumentation());
//		test1.setFailCause("");
//
//		test1.setPackageDescription(getPackageDescription(scenario));
//		test1.setTestDescription(createTestDescription(testName));
//		test1.setScenario(scenario);
//		test1.setDuration(13.23);
//		test1.setStatus((short) (2));
//		Set<TestProperty> testProperties1 = new HashSet<TestProperty>();
//		testProperties1.add(levelRegular);
//		testProperties1.add(automationErrorTrue);
//		testProperties1.add(version);
//		testProperties1.add(build);
//		test1.setTestProperties(testProperties1);
//
//		return test1;
//	}
//
//	public Set<ScenarioProperty> getScenarioProperties(ScenarioProperty type, ScenarioProperty build,
//			ScenarioProperty version) {
//
//		Set<ScenarioProperty> scenarioProperties = new HashSet<ScenarioProperty>();
//		scenarioProperties.add(type);
//		scenarioProperties.add(build);
//		scenarioProperties.add(version);
//
//		scenarioProperties.add(getRandomScenarioProperty(getPlatformList()));
//		scenarioProperties.add(getRandomScenarioProperty(getProductList()));
//		scenarioProperties.add(getRandomScenarioProperty(getUserList()));
//		scenarioProperties.add(getRandomScenarioProperty(getStationList()));
//
//		return scenarioProperties;
//
//	}
//
//	public ScenarioProperty getRandomScenarioProperty(List<ScenarioProperty> scenarioProperties) {
//
//		Random rand = new Random();
//		return scenarioProperties.get(rand.nextInt(scenarioProperties.size()));
//	}
//
//	public List<ScenarioProperty> getUserList() {
//
//		List<ScenarioProperty> typeList = new ArrayList<ScenarioProperty>();
//
//		typeList.add(userEran);
//		typeList.add(userItai);
//		typeList.add(userLimor);
//		typeList.add(userMoshi);
//		typeList.add(userTomer);
//		return typeList;
//
//	}
//
//	public List<ScenarioProperty> getStationList() {
//
//		List<ScenarioProperty> typeList = new ArrayList<ScenarioProperty>();
//
//		typeList.add(station1);
//		typeList.add(station2);
//		typeList.add(station3);
//		typeList.add(station4);
//		typeList.add(station5);
//		return typeList;
//
//	}
//
//	public List<ScenarioProperty> getProductList() {
//
//		List<ScenarioProperty> typeList = new ArrayList<ScenarioProperty>();
//
//		typeList.add(product1);
//		typeList.add(product2);
//		typeList.add(product3);
//		typeList.add(product4);
//		typeList.add(product5);
//		return typeList;
//
//	}
//
//	public List<ScenarioProperty> getPlatformList() {
//
//		List<ScenarioProperty> typeList = new ArrayList<ScenarioProperty>();
//
//		typeList.add(PlatformLinux);
//		typeList.add(PlatformWin);
//		return typeList;
//
//	}
//}
