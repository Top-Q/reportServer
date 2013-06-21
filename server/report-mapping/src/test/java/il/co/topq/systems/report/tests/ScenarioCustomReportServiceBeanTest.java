package il.co.topq.systems.report.tests;

import il.co.topq.systems.report.common.infra.log.ReportLogger;
import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.model.ScenarioCustomReport;
import il.co.topq.systems.report.common.obj.Chunk;
import il.co.topq.systems.report.common.obj.PropertyValuesWrapper;
import il.co.topq.systems.report.common.obj.TimeRange;
import il.co.topq.systems.report.service.infra.CustomReportService;
import il.co.topq.systems.report.service.infra.PropertyService;
import il.co.topq.systems.report.utils.RandomData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ScenarioCustomReportServiceBeanTest extends SpringBaseTest {

	@Autowired
	private CustomReportService<ScenarioCustomReport> scenarioCustomReportService;

	@Autowired
	private PropertyService<ReportProperty> scenarioPropertyService;

	private Logger log = ReportLogger.getInstance().getLogger(this.getClass());

	@After
	public void afterEachTest() {
		scenarioPropertyService.deleteOrphanProperties();
	}

	@Test
	public void getAllScenarioCustomReports() {
		List<ScenarioCustomReport> customReports = scenarioCustomReportService.getAll();
		Assert.assertTrue("Expecting no custom reports ", customReports.isEmpty());
	}

	// TODO: Eran - need to impl this.
	public void updateCustomReportWithInvalidProperties() {

	}

	// TODO: Eran - need to impl this.
	public void createCustomReportWithInvalidProperties() {

	}

	// TODO: Eran - need to impl this.
	public void createCustomReport() {

	}

	/**
	 * This test will create a scenarioCustomReport and will update it.<br>
	 */
	@Test
	public void updateCustomReport() throws Exception {
		try {
			ScenarioCustomReport scenarioCustomReport = new ScenarioCustomReport();
			Collection<ReportProperty> properties = RandomData.getRandomScenarioPropertyList(1);
			scenarioCustomReport.setProperties(properties);
			ScenarioCustomReport customReport = scenarioCustomReportService.create(scenarioCustomReport);
			Assert.assertNotNull(customReport);
			Integer customReportId = customReport.getId();
			ScenarioCustomReport scenarioCustomReportFromDB = scenarioCustomReportService.get(customReportId);
			Assert.assertNotNull("scenarioCustomReport could not be read from DB", scenarioCustomReportFromDB);
			String oldName = scenarioCustomReportFromDB.getName();
			Assert.assertNull(oldName);
			scenarioCustomReportFromDB.setName("newName");
			scenarioCustomReportService.update(scenarioCustomReportFromDB);
			scenarioCustomReportFromDB = scenarioCustomReportService.get(customReportId);
			String newName = scenarioCustomReportFromDB.getName();
			Assert.assertNotSame("Expecting custom report name to be updated", oldName, newName);
			scenarioCustomReportService.delete(customReportId);
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	/**
	 * This test will get a chunk of custom reports<br>
	 * 
	 * @throws Exception
	 */
	@Test
	public void getChunkOfScenarioCustomReports() throws Exception {
		Integer numberOfCustomReports = 10;
		Chunk chunk;
		Integer chunkLength = 2;
		Integer numberOfCustomReportsRetrieved = 0;
		Long numberOfCustomReportInDB = scenarioCustomReportService.countAll(null);
		List<Integer> customReportIds = new ArrayList<Integer>();

		// Creating the custom reports
		for (int i = 0; i < numberOfCustomReports; i++) {
			ScenarioCustomReport customReport = scenarioCustomReportService.create(new ScenarioCustomReport());
			Assert.assertNotNull(customReport);
			customReportIds.add(customReport.getId());
		}

		// Retrieving by chunk
		for (int i = 0; i < ((numberOfCustomReportInDB + numberOfCustomReports) / chunkLength); i++) {
			chunk = new Chunk(i * chunkLength, chunkLength);
			List<ScenarioCustomReport> customReportList = scenarioCustomReportService.getChunk(chunk);
			numberOfCustomReportsRetrieved += customReportList.size();
			Assert.assertEquals("Expecting to fetch chunk size of custom reports ", chunkLength.intValue(),
					customReportList.size());
		}

		Assert.assertEquals("should return" + (numberOfCustomReports + numberOfCustomReportInDB) + " custom reports",
				((Long) (numberOfCustomReports + numberOfCustomReportInDB)).longValue(),
				numberOfCustomReportsRetrieved.longValue());

		// deleting the custom reports created from DB
		for (Integer id : customReportIds) {
			scenarioCustomReportService.delete(id);
		}
	}

	/**
	 * This method will get a custom report by its id; it will first create a custom report, retrieve the custom report
	 * from db by its id and than delete it;
	 * 
	 * @throws Exception
	 *             -
	 */
	@org.junit.Test
	public void getCustomReportByID() throws Exception {

		try {
			ScenarioCustomReport scenarioCustomReport = new ScenarioCustomReport();
			Collection<ReportProperty> properties = RandomData.getRandomScenarioPropertyList(1);
			scenarioCustomReport.setProperties(properties);
			Integer customReportId = scenarioCustomReportService.create(scenarioCustomReport).getId();
			ScenarioCustomReport scenarioCustomReportFromDB = scenarioCustomReportService.get(customReportId);
			Assert.assertNotNull("scenarioCustomReport could not be read from DB", scenarioCustomReportFromDB);
			scenarioCustomReportService.delete(customReportId);
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	/**
	 * This method will test the return of all properties values by given scenarioCustomReportID;<br>
	 * it will create a custom report with random property keys.<br>
	 * expected: result of property value list size 0 for each property key wrapper.
	 * 
	 * @throws Exception
	 *             -
	 */
	@org.junit.Test
	public void getCustomReportPropertyValues() throws Exception {

		try {
			Integer numberOfProperties = 3;
			Collection<ReportProperty> scenarioCustomReportProperties = RandomData
					.getRandomScenarioCustomReportProperties(numberOfProperties);
			for (ReportProperty scenarioCustomReportProperty : scenarioCustomReportProperties) {
				scenarioPropertyService.create(scenarioCustomReportProperty);
			}
			ScenarioCustomReport scenarioCustomReport = new ScenarioCustomReport();

			scenarioCustomReport.setProperties(scenarioCustomReportProperties);
			scenarioCustomReport = scenarioCustomReportService.create(scenarioCustomReport);

			Assert.assertNotNull(scenarioCustomReport);

			ScenarioCustomReport scenarioCustomReportFromDB = scenarioCustomReportService.get(scenarioCustomReport
					.getId());

			Assert.assertNotNull(scenarioCustomReportFromDB);

			Collection<ReportProperty> scenarioProperties = scenarioCustomReportFromDB.getProperties();

			Assert.assertEquals("expecting " + numberOfProperties + " properties in custom report",
					numberOfProperties.intValue(), scenarioProperties.size());

			Collection<PropertyValuesWrapper> propertyValuesWrapperList;

			propertyValuesWrapperList = scenarioPropertyService.getAllPropertiesValues(scenarioProperties);
			for (PropertyValuesWrapper propertyValuesWrapper : propertyValuesWrapperList) {
				Assert.assertTrue("Expecting 0 values ", propertyValuesWrapper.getPropertyKeyValueList().isEmpty());
			}

			scenarioCustomReportService.delete(scenarioCustomReport.getId());

		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
	}

	@org.junit.Test
	public void getScenarioCustomReportByChunkAndNullTimeRange() throws Exception {

		List<Integer> customReportIds = new ArrayList<Integer>();
		try {
			Integer numberOfCustomReports = 10;
			Chunk chunk;
			Integer chunkLength = 2;
			Long numberOfCustomReportInDB = scenarioCustomReportService.countAll(null);
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

			// Retrieving by chunk
			for (int i = 0; i < ((numberOfCustomReportInDB + numberOfCustomReports) / chunkLength); i++) {
				chunk = new Chunk(i * chunkLength, chunkLength);
				List<ScenarioCustomReport> customReportList = scenarioCustomReportService.getCustomReport(chunk, null);
				customReportRetrieved.addAll(customReportList);
				Assert.assertEquals("Expecting fetch chunk size of custom reports", chunkLength.intValue(),
						customReportList.size());
			}

			// deleting the custom reports created from DB
		} finally {
			for (Integer id : customReportIds) {
				scenarioCustomReportService.delete(id);
			}
		}
	}

	/**
	 * this method will test the retrieval of scenario Custom Report by chunk and TimeRange<br>
	 * it will create X custom Reports with a current time in Millis<br>
	 * and than it will retrieve its query.<br>
	 * method deletes the custom reports created prior to termination.
	 * <p/>
	 * Expected: retrieved result must be in timeRange else an exception is thrown
	 * 
	 * @throws Exception
	 *             -
	 */
	@org.junit.Test
	public void getScenarioCustomReportByChunkAndTimeRange() throws Exception {

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
				List<ScenarioCustomReport> customReportList = scenarioCustomReportService.getCustomReport(chunk,
						timeRange);
				customReportRetrieved.addAll(customReportList);
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

	@Test
	public void getSizeOfCustomReportsByTimeRange() throws Exception {
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
			TimeRange timeRange = RandomData.getTimeRange(2005, 1, 1, 2014, 1, 1);

			int sizeOfCustomReportByFilter = scenarioCustomReportService.getSizeOfCustomReportByFilter(timeRange);
			Assert.assertEquals("Expecting size of custom reports to be equal", numberOfCustomReports.intValue(),
					sizeOfCustomReportByFilter);

			// Retrieving by chunk
			for (int i = 0; i < ((numberOfCustomReports) / chunkLength); i++) {
				chunk = new Chunk(i * chunkLength, chunkLength);
				List<ScenarioCustomReport> customReportList = scenarioCustomReportService.getCustomReport(chunk,
						timeRange);
				customReportRetrieved.addAll(customReportList);
				Assert.assertEquals("Expecting fetch chunk size of custom reports", chunkLength.intValue(),
						customReportList.size());
			}

			for (ScenarioCustomReport customReport : customReportRetrieved) {
				if (!((customReport.getDateOfCreation() < timeRange.getUpBoundDate()) && (customReport
						.getDateOfCreation() > timeRange.getLowBoundDate()))) {
					throw new Exception("Illegal result retrieved, timeRange is not correct");
				}
			}

			// deleting the custom reports created from DB
		} finally {
			for (Integer id : customReportIds) {
				scenarioCustomReportService.delete(id);
			}
		}
	}

	@Test
	public void getSizeOfCustomReportsByNullTimeRange() throws Exception {
		List<Integer> customReportIds = new ArrayList<Integer>();
		try {
			Integer numberOfCustomReports = 10;
			Chunk chunk;
			Integer chunkLength = 2;
			Long numberOfCustomReportInDB = scenarioCustomReportService.countAll(null);
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
			int sizeOfCustomReportByFilter = scenarioCustomReportService.getSizeOfCustomReportByFilter(null);
			Assert.assertEquals("Expecting size of custom reports to be equal", numberOfCustomReports.intValue(),
					sizeOfCustomReportByFilter);

			// Retrieving by chunk
			for (int i = 0; i < ((numberOfCustomReportInDB + numberOfCustomReports) / chunkLength); i++) {
				chunk = new Chunk(i * chunkLength, chunkLength);
				List<ScenarioCustomReport> customReportList = scenarioCustomReportService.getCustomReport(chunk, null);
				customReportRetrieved.addAll(customReportList);
				Assert.assertEquals("Expecting fetch chunk size of custom reports", chunkLength.intValue(),
						customReportList.size());
			}

			// deleting the custom reports created from DB
		} finally {
			for (Integer id : customReportIds) {
				scenarioCustomReportService.delete(id);
			}
		}
	}
}
