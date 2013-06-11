package il.co.topq.systems.report.tests;

import il.co.topq.systems.report.common.infra.log.ReportLogger;
import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.model.TestCustomReport;
import il.co.topq.systems.report.service.infra.CustomReportService;
import il.co.topq.systems.report.service.infra.PropertyService;
import il.co.topq.systems.report.service.infra.ScenarioService;
import il.co.topq.systems.report.service.infra.TestService;
import il.co.topq.systems.report.utils.RandomData;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestCustomReportServiceBeanTest extends SpringBaseTest {

	private Logger log = ReportLogger.getInstance().getLogger(this.getClass());

	@Autowired
	private CustomReportService<TestCustomReport> testCustomReportService;

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
	}

	/**
	 * will test the creation of a testCustomReport;<br>
	 * 1. a new testCustomReport will be written to DB.<br>
	 * 2. retrieves from DB the test custom report according to id received upon
	 * creation.<br>
	 * 3. deleted the custom report created;
	 * 
	 * @throws Exception
	 */
	@org.junit.Test
	public void createTestCustomReport() throws Exception {
		TestCustomReport testCustomReport = new TestCustomReport();

		Collection<ReportProperty> properties = RandomData
				.getRandomTestPropertyList(1);
		testCustomReport.setProperties(properties);
		TestCustomReport customReport = testCustomReportService
				.create(testCustomReport);
		Assert.assertNotNull(customReport);
		Integer customReportId = customReport.getId();
		Assert.assertNotNull(customReportId);
		testCustomReportService.delete(customReportId);
	}

	@Test
	public void updateCustomReport() throws Exception {
		try {
			TestCustomReport testCustomReport = new TestCustomReport();
			Collection<ReportProperty> properties = RandomData
					.getRandomTestPropertyList(1);
			testCustomReport.setProperties(properties);
			TestCustomReport customReport = testCustomReportService
					.create(testCustomReport);
			Assert.assertNotNull(customReport);
			Integer customReportId = customReport.getId();
			TestCustomReport testCustomReportFromDB = testCustomReportService
					.get(customReportId);
			Assert.assertNotNull(
					"scenarioCustomReport could not be read from DB",
					testCustomReportFromDB);
			String oldName = testCustomReportFromDB.getName();
			Assert.assertNull(oldName);
			testCustomReportFromDB.setName("newName");
			testCustomReportService.update(testCustomReportFromDB);
			testCustomReportFromDB = testCustomReportService
					.get(customReportId);
			String newName = testCustomReportFromDB.getName();
			Assert.assertNotEquals(
					"Expecting custom report name to be updated", oldName,
					newName);
			testCustomReportService.delete(customReportId);
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

}
