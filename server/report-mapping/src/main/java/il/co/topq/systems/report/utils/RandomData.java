package il.co.topq.systems.report.utils;

import il.co.topq.systems.report.common.model.PackageDescription;
import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.model.Scenario;
import il.co.topq.systems.report.common.model.ScenarioProperty;
import il.co.topq.systems.report.common.model.Test;
import il.co.topq.systems.report.common.model.TestDescription;
import il.co.topq.systems.report.common.model.TestProperty;
import il.co.topq.systems.report.common.model.User;
import il.co.topq.systems.report.common.obj.TimeRange;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomData {
	/**
	 * Generate a file with random content
	 * 
	 * @param lengthInMB
	 *            - the size of the requested file
	 * @param fileName
	 *            - file name
	 * @throws Exception
	 */
	public static File generateRandomFile(int lengthInMB, File file) throws Exception {
		int lengthBytes = lengthInMB * 1024 * 1024;
		RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");

		MappedByteBuffer out = randomAccessFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, lengthBytes);
		for (int i = 0; i < lengthBytes; i++)
			out.put((byte) (Math.round((Math.random()))));

		randomAccessFile.close();

		return file;
	}

	public static String getRandomVersion() {
		Random rand = new Random();
		double d = rand.nextDouble();
		double i = (double) rand.nextInt(7);
		return ((Double) (i * d)).toString();
	}

	public static Scenario getScenarioWithTests(Integer numOfTests) {
		Scenario scenario = getRandomEmptyScenario();

		// Set<Test> randomTests = getRandomTests(numOfTests);
		Collection<Test> randomTests = getRandomTests(numOfTests);

		int totalPass = 0;
		int totalFail = 0;
		int totalWarning = 0;
		for (Test test : randomTests) {
			test.setScenario(scenario);
			switch (test.getStatus()) {
			case 0:
				totalPass++;
				break;
			case 1:
				totalFail++;
				break;
			case 2:
				totalWarning++;
				break;
			}

		}
		scenario.setRunTest(numOfTests);
		scenario.setSuccessTests(totalPass);
		scenario.setFailTests(totalFail);
		scenario.setWarningTests(totalWarning);
		scenario.setTests(randomTests);
		scenario.setProperties(getRandomScenarioPropertyList(3));
		return scenario;
	}

	public static TimeRange getTimeRange(int fromYear, int fromMonth, int fromDay, int toYear, int toMonth, int toDay) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(fromYear, fromMonth, fromDay);
		Date from = calendar.getTime();
		calendar.set(toYear, toMonth, toDay);
		Date to = calendar.getTime();

		return new TimeRange(from.getTime(), to.getTime());
	}

	public static Collection<ReportProperty> getRandomScenarioCustomReportProperties(Integer numberOfProp) {
		Collection<ReportProperty> scenarioPropList = new HashSet<ReportProperty>();

		for (int i = 0; i < numberOfProp; i++) {
			ScenarioProperty scenarioProperty = new ScenarioProperty();
			scenarioProperty.setPropertyKey(generateString(5));
			scenarioProperty.setPropertyValue("");
			scenarioPropList.add(scenarioProperty);
		}
		return scenarioPropList;
	}

	public static Collection<ReportProperty> getRandomTestPropertyList(Integer numberOfProp) {
		Collection<ReportProperty> testPropList = new HashSet<ReportProperty>();
		for (int i = 0; i < numberOfProp; i++) {
			TestProperty testProperty = new TestProperty();
			testProperty.setPropertyKey(generateString(5));
			testProperty.setPropertyValue(generateString(4));
			testPropList.add(testProperty);
		}
		return testPropList;

	}

	public static Collection<ReportProperty> getRandomScenarioPropertyList(Integer numberOfProp) {
		Collection<ReportProperty> scenarioPropList = new HashSet<ReportProperty>();

		for (int i = 0; i < numberOfProp; i++) {
			ScenarioProperty scenarioProperty = new ScenarioProperty();
			scenarioProperty.setPropertyKey(generateString(5));
			scenarioProperty.setPropertyValue(generateString(4));
			scenarioPropList.add(scenarioProperty);
		}
		return scenarioPropList;

	}

	public static Integer getRandomInteger(int n) {
		Random rand = new Random();
		return rand.nextInt(n) + 1;
	}

	public static String getRandomScenarioName() {
		Random rand = new Random();
		return "scenario/" + generateString(rand, 7);
	}

	public static String generateString(int length) {
		Random rnd = new Random();
		return generateString(rnd, length);
	}

	public static String generateString(Random rng, int length) {
		char[] text = new char[length];
		String characters = "a generated string";
		for (int i = 0; i < length; i++) {
			text[i] = characters.charAt(rng.nextInt(characters.length()));
		}
		return new String(text);
	}

	public static Date getRandomStartTime() {
		Calendar cdr = Calendar.getInstance();
		cdr.set(Calendar.YEAR, 2005);
		long val1 = cdr.getTimeInMillis();
		Calendar cdr2 = Calendar.getInstance();
		long val2 = cdr2.getTimeInMillis();
		Random r = new Random();
		long randomTS = (long) (r.nextDouble() * (val2 - val1)) + val1;
		return new Date(randomTS);

	}

	public static String getRandomDescription() {
		Random r = new Random();
		String description = "";
		for (int i = 0; i < 55; i++) {
			description += generateString(r, new Random().nextInt(10)) + " ";
		}
		return description;
	}

	public static String getRandomString(int length) {
		Random r = new Random();
		return generateString(r, length);
	}

	public static Date getRandomDate(Date fromDate, Date toDate) {
		Calendar cdr1 = Calendar.getInstance();
		cdr1.setTime(fromDate);
		long val1 = cdr1.getTimeInMillis();
		Calendar cdr2 = Calendar.getInstance();
		cdr2.setTime(toDate);
		long val2 = cdr2.getTimeInMillis();
		Random r = new Random();
		long randomTS = (long) (r.nextDouble() * (val2 - val1)) + val1;
		return new Date(randomTS);
	}

	public static Scenario getRandomEmptyScenario() {
		Scenario scenario = new Scenario();
		scenario.setScenarioName(RandomData.getRandomScenarioName());
		Date startTimeDate = RandomData.getRandomStartTime();
		scenario.setStartTime(startTimeDate.getTime());
		scenario.setDescription(RandomData.getRandomDescription());
		scenario.setHtmlDir(RandomData.getRandomString(15));
		scenario.setIsDeleted((short) 0);
		scenario.setLastUpdate(RandomData.getRandomDate(startTimeDate, Calendar.getInstance().getTime()));
		return scenario;
	}

	// public static Set<Test> getRandomTests(Integer numberOfTests) {
	public static Collection<Test> getRandomTests(Integer numberOfTests) {

		// HashSet<Test> publishedTests = new HashSet<Test>();
		Collection<Test> publishedTests = new HashSet<Test>();
		for (int i = 0; i < numberOfTests; i++) {
			publishedTests.add(getRandomTest());
		}
		return publishedTests;
	}

	@SuppressWarnings("deprecation")
	public static Test getRandomTest() {
		Test test = new Test();
		test.setCount(1);
		test.setDocumentation(getRandomDescription());
		Date runDate = getRandomStartTime();
		// Date runDate = getTime();
		runDate.setSeconds(runDate.getSeconds() + 20);
		test.setEndTime(runDate.getTime());
		test.setStatus(new Integer(new Random().nextInt(3)).shortValue());
		test.setStartTime(runDate.getTime());
		test.setFailCause(getRandomDescription());
		test.setMessage(getRandomDescription());
		test.setTestDescription(getRandomTestDescription());
		test.setPackageDescription(getRandomPackageDescription());
		// test.setProperties(getRandomTestPropertyList(3));
		test.setProperties(null);
		return test;
	}

	@SuppressWarnings("unused")
	private static Set<TestProperty> getRandomTestProperties() {
		Set<TestProperty> testProperties = new HashSet<TestProperty>();
		TestProperty testProperty = new TestProperty();
		testProperty.setPropertyValue(getRandomVersion());
		testProperties.add(testProperty);
		return testProperties;
	}

	public static TestDescription getRandomTestDescription() {
		TestDescription testName = new TestDescription();
		testName.setTestDescription(getRandomDescription());
		int i = new Random().nextInt(10);
		testName.setTestName(getRandomTestName(i));
		return testName;
	}

	private static String getRandomTestName(int i) {
		switch (i) {
		case 0:
			return "Test.test0";
		case 1:
			return "Test.test1";
		case 2:
			return "Test.test2";
		case 3:
			return "Test.test3";
		case 4:
			return "Test.test4";
		case 5:
			return "Test.test5";
		case 6:
			return "Test.test6";
		case 7:
			return "Test.test7";
		case 8:
			return "Test.test8";
		case 9:
			return "Test.test9";
		default:
			return "Test.test0";
		}
	}

	private static String getRandomTestPackage(int i) {
		switch (i) {
		case 0:
			return "com0.topq0";
		case 1:
			return "com1.topq1";
		case 2:
			return "com2.topq2";
		case 3:
			return "com3.topq3";
		case 4:
			return "com4.topq4";
		case 5:
			return "com5.topq5";
		case 6:
			return "com6.topq6";
		case 7:
			return "com7.topq7";
		case 8:
			return "com8";
		case 9:
			return "com9.topq9.a.b.c.d.e.f.g.h";
		default:
			return "com0.topq0";
		}
	}

	public static PackageDescription getRandomPackageDescription() {
		int i = new Random().nextInt(10);
		String packageName = getRandomTestPackage(i);
		return new PackageDescription(packageName, getRandomDescription(), null);
	}

	public static Set<ScenarioProperty> getScenarioProperties() {
		HashSet<ScenarioProperty> scenarioProperties = new HashSet<ScenarioProperty>();
		scenarioProperties.add(new ScenarioProperty("t_version", "t_1.1"));
		scenarioProperties.add(new ScenarioProperty("t_build", "t_build_11"));
		return scenarioProperties;
	}

	public static void fillPropertiesForScenarioTests(Scenario scenario) {
		List<Test> tests = new ArrayList<Test>(scenario.getTests());
		for (int i = 0; i < tests.size(); i++) {
			tests.get(i).setProperties(getTestProperties(i));
		}
	}

	public static Collection<ReportProperty> getTestProperties(int i) {
		Collection<ReportProperty> testProperties = new HashSet<ReportProperty>();
		testProperties.add(new TestProperty("t_test_prop1", i + "." + i));
		testProperties.add(new TestProperty("t_test_prop2", "prop_" + i * i));
		return testProperties;
	}

	public static Scenario getScenarioWithTestsGroupByName(int numberOfGroup) {
		Scenario scenario = getRandomEmptyScenario();
		// Set<Test> randomTests = createTestGroupByName(numberOfGroup);
		List<Test> randomTests = createTestGroupByName(numberOfGroup);

		int totalPass = 0;
		int totalFail = 0;
		int totalWarning = 0;
		for (Test test : randomTests) {
			test.setScenario(scenario);
			switch (test.getStatus()) {
			case 0:
				totalPass++;
				break;
			case 1:
				totalFail++;
				break;
			case 2:
				totalWarning++;
				break;
			}

		}
		scenario.setRunTest(numberOfGroup);
		scenario.setSuccessTests(totalPass);
		scenario.setFailTests(totalFail);
		scenario.setWarningTests(totalWarning);
		scenario.setTests(randomTests);
		return scenario;
	}

	// private static Set<Test> createTestGroupByName(int numberOfGroup) {
	private static List<Test> createTestGroupByName(int numberOfGroup) {
		// HashSet<Test> tests = new HashSet<Test>();
		List<Test> tests = new ArrayList<Test>();
		for (int i = 0; i < numberOfGroup; i++) {
			tests.add(generateTest("myPackage" + i, "myNameIs" + i));
			tests.add(generateTest("myPackage" + i, "myNameIs" + i));
			tests.add(generateTest("myPackage" + i, "myNameIs" + i));
		}
		return tests;
	}

	@SuppressWarnings("deprecation")
	private static Test generateTest(String packageName, String name) {
		Test test = new Test();
		test.setPackageDescription(new PackageDescription(packageName, getRandomDescription(), null));
		test.setTestDescription(new TestDescription(name, getRandomDescription(), null));

		test.setCount(1);
		test.setDocumentation(getRandomDescription());
		Date runDate = getRandomStartTime();
		// noinspection deprecation
		runDate.setSeconds(runDate.getSeconds() + 20);
		test.setEndTime(runDate.getTime());
		test.setStatus(new Integer(new Random().nextInt(3)).shortValue());
		test.setStartTime(runDate.getTime());
		test.setFailCause(getRandomDescription());
		test.setMessage(getRandomDescription());

		return test;
	}

	public static User getRandomUser() {
		User user = new User();
		user.setUsername(getRandomString(5));
		user.setPassword(getRandomString(5));
		return user;
	}
}
