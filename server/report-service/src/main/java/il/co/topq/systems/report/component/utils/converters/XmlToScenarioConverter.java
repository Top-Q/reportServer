package il.co.topq.systems.report.component.utils.converters;

import il.co.topq.systems.report.common.model.PackageDescription;
import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.model.Scenario;
import il.co.topq.systems.report.common.model.ScenarioProperty;
import il.co.topq.systems.report.common.model.Test;
import il.co.topq.systems.report.common.model.TestDescription;
import il.co.topq.systems.report.common.model.TestProperty;
import il.co.topq.systems.report.utils.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerException;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XmlToScenarioConverter {

	private XmlToScenarioConverter() {
	}

	/**
	 * This method will create a scenario from a report XML file.
	 * 
	 * @param scenarioXMLFile
	 *            the scenario representation in XML.
	 * @return Scenario
	 * @throws Exception
	 */
	public static Scenario convert(File scenarioXMLFile) throws Exception {
		DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = documentBuilder.parse(scenarioXMLFile);

		return createScenarioFromDoc(doc);
	}

	/**
	 * This method will create a scenario from a report XML file represented as ByteArrayInputStream.
	 * 
	 * @param scenarioXMLFile
	 *            the scenario representation in XML as a stream.
	 * @return Scenario
	 * @throws Exception
	 */
	public static Scenario convert(ByteArrayInputStream byteArrayInputStream) throws Exception {
		DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = documentBuilder.parse(byteArrayInputStream);

		return createScenarioFromDoc(doc);
	}

	private static Scenario createScenarioFromDoc(Document doc) throws Exception {
		Scenario scenario = new Scenario();
		scenario.setScenarioName(((Element) doc.getFirstChild()).getAttribute("Scenario"));
		scenario.setStartTime(Long.parseLong(((Element) doc.getFirstChild()).getAttribute("startTime")));
		scenario.setDescription(((Element) doc.getFirstChild()).getAttribute("Description"));
		scenario.setProperties(createScenarioProperties(doc));
		countNumberOfTestsRunFailedAndPassed(doc, scenario);
		readTests(doc, scenario);

		return scenario;
	}

	/**
	 * This method will get all the "Fixed" Scenario properties and will save them in ScenarioPropertiesSet.
	 * ScenarioProperties may will have an additional properties that will be added later;
	 * 
	 * @throws Exception
	 */
	private static Collection<ReportProperty> createScenarioProperties(Document doc) throws Exception {

		Collection<ReportProperty> scenarioPropertiesSet = new HashSet<ReportProperty>();
		scenarioPropertiesSet.add(new ScenarioProperty("setupName", ((Element) doc.getFirstChild())
				.getAttribute("Setup")));

		scenarioPropertiesSet.add(new ScenarioProperty("station", ((Element) doc.getFirstChild())
				.getAttribute("Station")));

		scenarioPropertiesSet.add(new ScenarioProperty("user", ((Element) doc.getFirstChild()).getAttribute("User")));

		String propertiesString = ((Element) doc.getFirstChild()).getAttribute("properties");

		Properties p = StringUtils.stringToProperties(propertiesString);
		Enumeration<?> keys = p.propertyNames();
		String key;
		while (keys.hasMoreElements()) {
			key = (String) keys.nextElement();
			String value = p.getProperty(key);
			if ((!key.isEmpty()) && (!value.isEmpty())) {
				scenarioPropertiesSet.add(new ScenarioProperty(key, p.getProperty(key)));
			}
		}
		return scenarioPropertiesSet;
	}

	private static void readTests(Document doc, Scenario scenario) throws Exception {

		Long testEndTime = null;

		Collection<Test> scenarioTests = new HashSet<Test>();
		Test test = new Test();

		NodeList elements = (NodeList) XPathAPI.selectNodeList(doc, "/reports/test");
		for (int index = 0; index < elements.getLength(); index++) {
			test = new Test();
			if (elements.item(index) instanceof Element) {
				Element e = (Element) elements.item(index);

				// Test Name
				TestDescription testDescription = new TestDescription();
				testDescription.setTestName(e.getAttribute("name"));
				test.setTestDescription(testDescription);
				// PKG Name;
				PackageDescription packageDescription = new PackageDescription();
				packageDescription.setPackageName(e.getAttribute("package"));
				test.setPackageDescription(packageDescription);

				// Test MESSAGE
				test.setMessage(e.getAttribute("message"));

				// Test STATUS
				String status = e.getAttribute("status");
				Integer integerStatus;
				if (status.equals("true")) {
					integerStatus = 0;
				} else if (status.equals("false")) {
					integerStatus = 1;
				} else {
					integerStatus = 2;
				}
				if (integerStatus != null) {
					test.setStatus(integerStatus.shortValue());
				}
				// Test START TIME
				String startTime = e.getAttribute("startTime");
				if (startTime != null) {
					test.setStartTime(Long.parseLong(startTime));
				}
				// Test ENDTIME
				String endTime = e.getAttribute("endTime");
				if (endTime != null) {
					try {
						if (endTime != "") {
							test.setEndTime(Long.parseLong(endTime));
							testEndTime = test.getEndTime();
						} else {
							test.setEndTime(Long.parseLong("1111111111111L"));
						}
					} catch (Throwable t) {
						// Log.log("no endTime for test: " +
						// test.getTestDescription().getTestName(), t);
					}
				}

				test.setDuration(((double) (test.getEndTime() - test.getStartTime())) / 1000);

				// Test FAILCAUSE
				test.setFailCause(e.getAttribute("failCause"));

				// UNSUPPORTED YET ** will do that in the future
				// info.setSteps(e.getAttribute("steps"));
				// UNSUPPORTED YET ** will do that in the future

				// Test Documantation
				test.setDocumentation(e.getAttribute("documentaion"));

				// Test PARAMS
				test.setParams(e.getAttribute("params"));

				// Test COUNT
				test.setCount(Integer.parseInt(e.getAttribute("count")));

				test.setScenario(scenario);

				// String propertiesString = ((Element)
				// doc.getFirstChild()).getAttribute("properties");
				String propertiesString = e.getAttribute("properties");
				Collection<ReportProperty> testProperties = new TreeSet<ReportProperty>();
				Properties p = StringUtils.stringToProperties(propertiesString);
				Enumeration<?> keys = p.propertyNames();
				String key;
				while (keys.hasMoreElements()) {
					key = (String) keys.nextElement();
					if ((!key.isEmpty()) && (!p.getProperty(key).isEmpty())) {
						testProperties.add(new TestProperty(key, p.getProperty(key)));
					}
				}
				test.setProperties(testProperties);
				scenarioTests.add(test);
				// System.out.println(test.getTestDescription().getTestName());
				// System.out.println(scenarioTests.size());
			}
		}

		scenario.setTests(scenarioTests);

		// For debugging
		// Set<Test> tests = this.scenario.getTests();
		// for (Test test2 : tests) {
		// System.out.println(test2.getTestDescription().getTestName());
		// }

		long startTime = scenario.getStartTime();
		double total = 0;
		if (testEndTime != null) {
			total = ((double) (testEndTime - startTime)) / 1000;
		}

		scenario.setDuration(total);
	}

	/**
	 * This method will sum the tests by status for the scenario.
	 * 
	 * @param doc
	 * @param scenario
	 * @throws TransformerException
	 */
	private static void countNumberOfTestsRunFailedAndPassed(Document doc, Scenario scenario)
			throws TransformerException {

		Integer numberOfTests = 0;
		Integer numberOfTestsPass = 0;
		Integer numberOfTestsFail = 0;
		Integer numberOfTestsWarning = 0;

		NodeList elements = (NodeList) XPathAPI.selectNodeList(doc, "/reports/test");
		for (int index = 0; index < elements.getLength(); index++) {
			if (elements.item(index) instanceof Element) {
				numberOfTests++;

				Element e = (Element) elements.item(index);

				String status = e.getAttribute("status");
				if (status.equals("true")) {
					numberOfTestsPass++;
				} else if (status.equals("false")) {
					numberOfTestsFail++;
				} else {
					numberOfTestsWarning++;
				}
			}
		}

		scenario.setFailTests(numberOfTestsFail);
		scenario.setSuccessTests(numberOfTestsPass);
		scenario.setRunTest(numberOfTests);
		scenario.setWarningTests(numberOfTestsWarning);
	}
}
