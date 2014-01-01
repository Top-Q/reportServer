package il.co.topq.systems.report.tests.unit;

import il.co.topq.systems.report.common.jaxbWrappers.PropertyValuesWrapperList;
import il.co.topq.systems.report.common.jaxbWrappers.ScenarioCustomReportList;
import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.model.ScenarioCustomReport;
import il.co.topq.systems.report.common.model.ScenarioProperty;
import il.co.topq.systems.report.common.obj.Chunk;
import il.co.topq.systems.report.common.obj.PropertyValuesWrapper;
import il.co.topq.systems.report.common.obj.ScenarioQuery;
import il.co.topq.systems.report.common.obj.TimeRange;
import il.co.topq.systems.report.component.utils.URLParts;
import il.co.topq.systems.report.service.infra.CustomReportService;
import il.co.topq.systems.report.service.infra.PropertyService;
import il.co.topq.systems.report.tests.infra.WebserviceBaseTest;
import il.co.topq.systems.report.utils.RandomData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/applicationContext.xml" })
public class ScenarioCustomReportWebServiceTest extends WebserviceBaseTest {

	@Autowired
	private CustomReportService<ScenarioCustomReport> scenarioCustomReportService;

	@Autowired
	private PropertyService<ReportProperty> scenarioPropertyService;

	// TESTED
	@Test
	public void createCustomReport() {
		ScenarioCustomReport scenarioCustomReport = new ScenarioCustomReport();
		Integer customReportId = Integer.valueOf(getWebResource(URLParts.CREATE_SCENARIO_CUSTOM_REPORT).post(
				String.class, scenarioCustomReport));
		Assert.assertNotNull(customReportId);
		scenarioCustomReportService.delete(customReportId);
	}

	@Test
	public void createCustomReportWithProperties() {
		ScenarioCustomReport scenarioCustomReport = new ScenarioCustomReport();
		scenarioCustomReport.setProperties(RandomData.getRandomScenarioCustomReportProperties(3));
		Integer customReportId = Integer.valueOf(getWebResource(URLParts.CREATE_SCENARIO_CUSTOM_REPORT).post(
				String.class, scenarioCustomReport));
		Assert.assertNotNull(customReportId);
		scenarioCustomReportService.delete(customReportId);

	}

	// TESTED
	@Test
	public void deleteCustomReport() throws Exception {
		ScenarioCustomReport customReport = scenarioCustomReportService.create(new ScenarioCustomReport());
		Assert.assertNotNull(customReport);
		Boolean deleted = Boolean.valueOf(getWebResource(URLParts.DELETE_SCENARIO_CUSTOM_REPORT + customReport.getId())
				.get(String.class));
		Assert.assertTrue(deleted);
	}

	/**
	 * This method will retrieve a ScenarioCustomReport using its ID;<br>
	 * 1. Creates a scenario<br>
	 * 2. Creates a scenarioCustomReport.<br>
	 * 3. Retrieves the ScenarioCustomReport from DB.<br>
	 * 4. Deletes the scenarioCustomReport from DB.<br>
	 * 
	 * @throws Exception
	 */
	// TESTED
	@org.junit.Test
	public void getScenarioCustomReportByID() throws Exception {

		ScenarioCustomReport customReport = scenarioCustomReportService.create(new ScenarioCustomReport());
		Assert.assertNotNull(customReport);

		ScenarioCustomReport scenarioCustomReport = getWebResource(
				URLParts.GET_SCENARIO_CUSTOM_REPORT + customReport.getId()).get(ScenarioCustomReport.class);
		org.junit.Assert.assertNotNull(scenarioCustomReport);

		scenarioCustomReportService.delete(scenarioCustomReport.getId());
	}

	// TESTED
	@org.junit.Test
	public void getScenarioCustomReportPropertiesValue() throws Exception {

		ReportProperty scenarioProperty = new ScenarioProperty("key", "value");
		scenarioProperty = scenarioPropertyService.create(scenarioProperty);
		ScenarioCustomReport scenarioCustomReport = new ScenarioCustomReport();
		Collection<ReportProperty> properties = new HashSet<ReportProperty>();
		properties.add(scenarioProperty);
		scenarioCustomReport.setProperties(properties);
		scenarioCustomReport = scenarioCustomReportService.create(scenarioCustomReport);
		Assert.assertNotNull(scenarioCustomReport);
		List<PropertyValuesWrapper> scenarioCustomReportPropertyValues = getWebResource(
				URLParts.SCENARIO_CUSTOM_REPORT_VALUES + scenarioCustomReport.getId()).get(
				PropertyValuesWrapperList.class).getPropertyValuesWrappers();

		Assert.assertFalse(scenarioCustomReportPropertyValues.isEmpty());
		Assert.assertEquals("expecting 1 propertyValueWrapper", properties.size(),
				scenarioCustomReportPropertyValues.size());
		Assert.assertEquals("expecting 1 value", 1, scenarioCustomReportPropertyValues.get(0).getPropertyKeyValueList()
				.size());

		scenarioCustomReportService.delete(scenarioCustomReport.getId());
		scenarioPropertyService.delete(scenarioProperty.getIndex1());
	}

	// TESTED
	@org.junit.Test
	public void updateScenarioCustomReport() throws Exception {

		String updatedName = "updatedName";

		ScenarioCustomReport scenarioCustomReport = scenarioCustomReportService.create(new ScenarioCustomReport());
		Assert.assertNotNull(scenarioCustomReport);

		ScenarioCustomReport scenarioCustomReportFromDB = scenarioCustomReportService.get(scenarioCustomReport.getId());
		scenarioCustomReportFromDB.setName(updatedName);

		boolean updated = Boolean.valueOf(getWebResource(URLParts.UPDATE_SCENARIO_CUSTOM_REPORT).post(String.class,
				scenarioCustomReportFromDB));

		Assert.assertTrue("Expecting update to success", updated);

		ScenarioCustomReport scenarioCustomReportAfterUpdate = scenarioCustomReportService
				.get(scenarioCustomReportFromDB.getId());
		Assert.assertEquals("expecting names to match after update", updatedName,
				scenarioCustomReportAfterUpdate.getName());

		scenarioCustomReportService.delete(scenarioCustomReportAfterUpdate.getId());
	}

	// Tested
	@org.junit.Test
	public void getChunkOfScenarioCustomReport() throws Exception {

		List<Integer> customReportIds = new ArrayList<Integer>();
		try {
			Integer numberOfCustomReports = 10;
			Chunk chunk;
			Integer chunkLength = 2;
			customReportIds = new ArrayList<Integer>();
			List<ScenarioCustomReport> customReportRetrieved = new ArrayList<ScenarioCustomReport>();

			// Creating the custom reports
			for (int i = 0; i < numberOfCustomReports; i++) {
				ScenarioCustomReport scenarioCustomReport = new ScenarioCustomReport();
				scenarioCustomReport.setDateOfCreation(System.currentTimeMillis());
				ScenarioCustomReport customReport = scenarioCustomReportService.create(scenarioCustomReport);
				Assert.assertNotNull(customReport);
				customReportIds.add(customReport.getId());
			}
			TimeRange timeRange = RandomData.getTimeRange(2005, 1, 1, 2014, 5, 5);

			// Retrieving by chunk
			for (int i = 0; i <= (numberOfCustomReports / chunkLength); i++) {
				chunk = new Chunk(i * chunkLength, chunkLength);
				ScenarioQuery scenarioQuery = new ScenarioQuery(timeRange, chunk);
				List<ScenarioCustomReport> customReportList = getWebResource(URLParts.SCENARIO_CUSTOM_REPORT_CHUNK)
						.post(ScenarioCustomReportList.class, scenarioQuery).getCustomReports();
				if (customReportList != null) {
					customReportRetrieved.addAll(customReportList);
				}
			}

			Assert.assertEquals(numberOfCustomReports.intValue(), customReportRetrieved.size());

			for (ScenarioCustomReport customReport : customReportRetrieved) {
				Assert.assertFalse("Illegal result retrieved, timeRange is not correct",
						(!((customReport.getDateOfCreation() < timeRange.getUpBoundDate()) && (customReport
								.getDateOfCreation() > timeRange.getLowBoundDate()))));
			}

			// deleting the custom reports created from DB
		} finally {
			for (ScenarioCustomReport scenarioCustomReport : scenarioCustomReportService.getAll()) {
				scenarioCustomReportService.delete(scenarioCustomReport.getId());
			}
		}

	}
}
