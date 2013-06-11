package il.co.topq.systems.report.tests.unit;

import static org.junit.Assert.assertEquals;
import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.model.TestCustomReport;
import il.co.topq.systems.report.common.model.TestProperty;
import il.co.topq.systems.report.common.obj.Chunk;
import il.co.topq.systems.report.common.obj.PropertyValuesWrapper;
import il.co.topq.systems.report.common.obj.TestQuery;
import il.co.topq.systems.report.common.obj.TimeRange;
import il.co.topq.systems.report.component.jaxbWrappers.PropertyValuesWrapperList;
import il.co.topq.systems.report.component.jaxbWrappers.TestCustomReportList;
import il.co.topq.systems.report.component.utils.URLParts;
import il.co.topq.systems.report.service.infra.CustomReportService;
import il.co.topq.systems.report.service.infra.PropertyService;
import il.co.topq.systems.report.tests.infra.WebserviceBaseTest;
import il.co.topq.systems.report.utils.RandomData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

/**
 * @author Eran.Golan & Tomer.Gafner
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/applicationContext.xml" })
public class TestCustomReportWebServiceTest extends WebserviceBaseTest {

	@Autowired
	private CustomReportService<TestCustomReport> testCustomReportService;

	@Autowired
	private PropertyService<ReportProperty> testPropertyService;

	// TESTED
	@Test
	public void createTestCustomReport() {
		TestCustomReport customReport = new TestCustomReport();
		Integer customReportId = Integer.valueOf(getWebResource(URLParts.CREATE_TEST_CUSTOM_REPORT).post(String.class,
				customReport));
		Assert.assertNotNull(customReportId);
		testCustomReportService.delete(customReportId);
	}

	// TESTED
	@Test
	public void getTestCustomReportByID() throws Exception {
		TestCustomReport customReport = testCustomReportService.create(new TestCustomReport());
		Assert.assertNotNull(customReport);
		customReport = getWebResource(URLParts.GET_TEST_CUSTOM_REPORT + customReport.getId()).get(
				TestCustomReport.class);
		testCustomReportService.delete(customReport.getId());
	}

	// TESTED
	@Test
	public void deleteTestCustomReport() throws Exception {
		TestCustomReport customReport = testCustomReportService.create(new TestCustomReport());
		Assert.assertNotNull(customReport);
		Boolean deleted = Boolean.valueOf(getWebResource(URLParts.DELETE_TEST_CUSTOM_REPORT + customReport.getId())
				.get(String.class));
		Assert.assertTrue(deleted);
	}

	// TESTED
	@Test
	public void updateTestCustomReport() throws Exception {

		String updatedName = "updatedName";
		TestCustomReport customReport = testCustomReportService.create(new TestCustomReport());
		Assert.assertNotNull(customReport);
		customReport.setName(updatedName);
		Boolean updated = Boolean.valueOf(getWebResource(URLParts.UPDATE_TEST_CUSTOM_REPORT).post(String.class,
				customReport));
		Assert.assertTrue(updated);
		TestCustomReport retrievedAfterUpdate = testCustomReportService.get(customReport.getId());
		assertEquals("updated name does not match the one in DB.", updatedName, retrievedAfterUpdate.getName());
		testCustomReportService.delete(customReport.getId());
	}

	// TESTED
	@Test
	public void getTestCustomReportPropertiesValue() throws Exception {

		TestCustomReport testCustomReport = new TestCustomReport();
		ReportProperty testProperty = new TestProperty("key", "value");
		testProperty = testPropertyService.create(testProperty);
		Collection<ReportProperty> properties = new HashSet<ReportProperty>();
		properties.add(testProperty);
		testCustomReport.setProperties(properties);
		testCustomReport = testCustomReportService.create(testCustomReport);
		Assert.assertNotNull(testCustomReport);
		List<PropertyValuesWrapper> testCustomReportPropertyValues = getWebResource(
				URLParts.TEST_CUSTOM_REPORT_VALUES + testCustomReport.getId()).get(PropertyValuesWrapperList.class)
				.getPropertyValuesWrappers();

		Assert.assertFalse(testCustomReportPropertyValues.isEmpty());
		Assert.assertEquals("expecting 1 propertyValueWrapper", properties.size(),
				testCustomReportPropertyValues.size());
		Assert.assertEquals("expecting 1 value", 1, testCustomReportPropertyValues.get(0).getPropertyKeyValueList()
				.size());

		testCustomReportService.delete(testCustomReport.getId());
		testPropertyService.delete(testProperty.getIndex1());
	}

	// TESTED
	@org.junit.Test
	public void getChunkOfTestCustomReport() throws Exception {

		Integer chunkLength = 10;
		List<TestCustomReport> testCustomReports = new ArrayList<TestCustomReport>();
		List<Integer> testCustomReportsIDs = new ArrayList<Integer>();
		Integer numberOfTestCustomReports = 45;
		for (int i = 0; i < numberOfTestCustomReports; i++) {
			TestCustomReport customReport = new TestCustomReport();
			customReport.setDateOfCreation(System.currentTimeMillis());
			customReport = testCustomReportService.create(customReport);
			Assert.assertNotNull(customReport);
			testCustomReportsIDs.add(customReport.getId());
		}
		TimeRange timeRange = RandomData.getTimeRange(2005, 1, 1, 2014, 5, 5);
		TestQuery testQuery = new TestQuery();
		testQuery.setTimeRange(timeRange);
		Chunk chunk;

		// Gets the size of this query;
		Integer sizeOfTestCustomReportQuery = Integer.valueOf(getWebResource(URLParts.TEST_CUSTOM_REPORT_QUERY_SIZE)
				.post(String.class, testQuery));

		// Gets the scenarioList
		for (int i = 0; i <= (sizeOfTestCustomReportQuery / chunkLength); i++) {
			chunk = new Chunk(i * chunkLength, chunkLength);
			testQuery.setChunk(chunk);
			List<TestCustomReport> tempList = getWebResource(URLParts.TEST_CUSTOM_REPORT_CHUNK).post(
					TestCustomReportList.class, testQuery).getCustomReports();
			if (tempList != null) {
				testCustomReports.addAll(tempList);
			}
		}

		// Assert timeRange
		for (TestCustomReport testCustomReport : testCustomReports) {
			long testCustomReportStartTime = testCustomReport.getDateOfCreation();
			assertEquals(
					"test custom report date of creation is not in query timeRange.",
					true,
					((testCustomReportStartTime <= timeRange.getUpBoundDate()) && (testCustomReportStartTime >= timeRange
							.getLowBoundDate())));
		}

		// Deletion of all scenario custom reports created.
		for (Integer id : testCustomReportsIDs) {
			testCustomReportService.delete(id);
		}
	}
}
