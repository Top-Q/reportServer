package il.co.topq.systems.report.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import il.co.topq.systems.report.common.infra.log.ReportLogger;
import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.model.TestProperty;
import il.co.topq.systems.report.service.infra.PropertyService;
import il.co.topq.systems.report.utils.RandomData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestPropertiesServiceBeanTest extends SpringBaseTest {

	Logger log = ReportLogger.getInstance().getLogger(this.getClass());

	@Autowired
	private PropertyService<ReportProperty> testPropertyService;

	String key = RandomData.generateString(5);
	String value = RandomData.generateString(5);

	@After
	public void afterEachTest() {
		testPropertyService.deleteOrphanProperties();
	}

	/**
	 * This method will test the creation of a testProperty.<br>
	 * It checks if a unique key value pair remains in DB, which means the same property should not be able to be
	 * written into DB twice.<br>
	 * Assumption: in case a property with same values exist will not create a duplicate<br>
	 * Deletes the property created prior to termination.<br>
	 * 
	 * @throws Exception
	 */
	@org.junit.Test
	public void createTestProperty() throws Exception {

		Set<Integer> propertyIDList = new HashSet<Integer>();

		try {
			TestProperty testProperty = new TestProperty();
			testProperty.setPropertyKey(key);
			testProperty.setPropertyValue(value);

			Assert.assertFalse("test property already exist",
					testPropertyService.isPersisted(testProperty.getPropertyKey(), testProperty.getPropertyValue()));

			ReportProperty reportProperty = testPropertyService.create(testProperty);
			Assert.assertNotNull(reportProperty);
			Integer id = reportProperty.getIndex1();

			propertyIDList.add(id);

			Assert.assertTrue("Expecting property to be in DB", testPropertyService.isPersisted(key, value));

			assertTrue(propertyIDList.size() == 1);
		} catch (Exception e) {
			log.error(e);
			throw e;
		} finally {
			for (Integer id : propertyIDList) {
				testPropertyService.delete(id);
			}
		}
	}

	/**
	 * This method will test the return of a property test key by its ID!<br>
	 * <p/>
	 * 1. Creates a Test Property and saves it ID;<bR>
	 * 2. Retrieves the property from the DB using the given ID;<br>
	 * 3. Deletes the property from the DB.<br>
	 */
	@Test
	public void getTestPropertyKeyByID() {
		Integer id = null;

		try {

			TestProperty testProperty = new TestProperty();
			testProperty.setPropertyKey(key);
			testProperty.setPropertyValue(value);

			Assert.assertFalse("test property already exist",
					testPropertyService.isPersisted(testProperty.getPropertyKey(), testProperty.getPropertyValue()));

			ReportProperty reportProperty = testPropertyService.create(testProperty);
			Assert.assertNotNull(reportProperty);
			id = reportProperty.getIndex1();
			Assert.assertNotNull(id);
			ReportProperty testPropertyFromDB = testPropertyService.get(id);
			assertEquals(key, testPropertyFromDB.getPropertyKey());
			assertEquals(value, testPropertyFromDB.getPropertyValue());

			String propertyKey = testPropertyService.getPropertyKey(reportProperty.getIndex1());
			Assert.assertEquals("Expecting key to be equal", key, propertyKey);

		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			if (id != null) {
				testPropertyService.delete(id);
			}
		}

	}

	/**
	 * This method will test the retrieval of unique scenario Property Keys from DB.<br>
	 * 
	 * @throws Exception
	 */
	@org.junit.Test
	public void getUniqueTestPropertyKeyList() throws Exception {

		Collection<String> keySet = new HashSet<String>();
		int numOfKeys = 2;
		for (int i = 0; i < numOfKeys; i++) {
			TestProperty testProperty = new TestProperty();
			testProperty.setPropertyKey(key);
			testProperty.setPropertyValue(value + i);

			Assert.assertFalse("test property already exist",
					testPropertyService.isPersisted(testProperty.getPropertyKey(), testProperty.getPropertyValue()));

			ReportProperty reportProperty = testPropertyService.create(testProperty);
			Assert.assertNotNull(reportProperty);
			Integer id = reportProperty.getIndex1();
			Assert.assertNotNull(id);
			keySet.add(testProperty.getPropertyKey());
		}
		try {

			List<String> testPropertyUniqueKeyList = testPropertyService.getUniquePropertyKeyList();
			assertEquals(testPropertyUniqueKeyList.size(), keySet.size());

		} catch (Exception e) {
			log.error(e);
		}

	}

	/**
	 * This method will test the retrieval of all the test property key values in DB.<br>
	 * <p/>
	 * 1. Created an X test property with the same key.<br>
	 * 2. retrieves from DB and check to see if all values returned.<br>
	 * 3. deletes all the test properties created.<br>
	 * 
	 * @throws Exception
	 */
	@org.junit.Test
	public void getUniqueTestPropertyValuesByKey() throws Exception {

		Integer numberOfProperties = 3;
		List<TestProperty> testProperties = new ArrayList<TestProperty>();
		List<Integer> IDs = new ArrayList<Integer>();

		try {
			// Creation of the scenario property;
			for (int i = 0; i < numberOfProperties; i++) {
				testProperties.add(new TestProperty(key, (value + i)));
			}

			// Writing the scenario Property to DB.
			for (ReportProperty testProperty : testProperties) {
				ReportProperty reportProperty = testPropertyService.create(testProperty);
				Assert.assertNotNull(reportProperty);
				IDs.add(reportProperty.getIndex1());
			}

			Set<String> testPropertyValuesByKey = testPropertyService.getPropertyValuesByKey(key);

			assertEquals(numberOfProperties, (Integer) testPropertyValuesByKey.size());

		} finally {
			for (Integer id : IDs) {
				testPropertyService.delete(id);
			}
		}

	}
}
