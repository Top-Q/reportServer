package il.co.topq.systems.report.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import il.co.topq.systems.report.common.infra.log.ReportLogger;
import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.model.ScenarioProperty;
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

public class ScenarioPropertiesServiceBeanTest extends SpringBaseTest {

	private Logger log = ReportLogger.getInstance().getLogger(this.getClass());

	private String key = RandomData.generateString(5);
	private String value = RandomData.generateString(5);

	@Autowired
	private PropertyService<ReportProperty> scenarioPropertyService;

	@After
	public void afterEachTest() {
		scenarioPropertyService.deleteOrphanProperties();
	}

	// TODO: Eran - implement this test.
	// @Test
	/**
	 * This test will create a scenario with properties.<br>
	 * will delete the scenario.<br>
	 * will validate properties still exists as it is not cascaded.<br>
	 * will call delete orphan properties.<br>
	 * will validate properties do not exist<br>
	 * 
	 */
	public void deleteOrphanPropertiesTest() {

	}

	@SuppressWarnings("unused")
	@Test
	public void createScenarioProperty2() throws Exception {
		ScenarioProperty scenarioProperty = new ScenarioProperty("tgeg", "val1");
		ScenarioProperty scenarioProperty2 = new ScenarioProperty("tgeg", "val2");
		ScenarioProperty scenarioProperty3 = new ScenarioProperty("tgeg", "val3");

		ReportProperty S = scenarioPropertyService.create(scenarioProperty);
		ReportProperty reportProperty2 = scenarioPropertyService.create(scenarioProperty2);
		ReportProperty reportProperty3 = scenarioPropertyService.create(scenarioProperty3);

	}

	/**
	 * This method will test the creation of a ScenarioProperty.<br>
	 * It checks if a unique key value pair remains in DB, which means the same property should not be able to be
	 * written into DB twice.<br>
	 * Assumption: in case a property with same values exist will not create a duplicate<br>
	 * Deletes the property created prior to termination.<br>
	 * 
	 * @throws Exception
	 *             in case a property with the same value as the one trying to create already exist in DB
	 */
	@Test
	public void createScenarioProperty() throws Exception {

		Set<Integer> propertyIDList = new HashSet<Integer>();

		try {
			ScenarioProperty scenarioProperty = new ScenarioProperty();
			scenarioProperty.setPropertyKey(key);
			scenarioProperty.setPropertyValue(value);

			if (scenarioPropertyService.isPersisted(scenarioProperty.getPropertyKey(),
					scenarioProperty.getPropertyValue())) {
				throw new Exception("Scenario property already exist");
			}

			ReportProperty reportProperty = scenarioPropertyService.create(scenarioProperty);
			Assert.assertNotNull(reportProperty);
			Integer id = reportProperty.getIndex1();

			propertyIDList.add(id);

			Assert.assertTrue("Expecting property to be in DB", scenarioPropertyService.isPersisted(key, value));

			assertTrue(propertyIDList.size() == 1);
		} catch (Exception e) {
			log.error(e);
			throw e;
		} finally {
			for (Integer id : propertyIDList) {
				scenarioPropertyService.delete(id);
			}
		}
	}

	/**
	 * This method will test the return of a property scenario key by its ID!<br>
	 * <p/>
	 * 1. Creates a scenario Property and saves it ID;<bR>
	 * 2. Retrieves the property from the DB using the given ID;<br>
	 * 3. Deletes the property from the DB.<br>
	 */
	@Test
	public void getScenarioPropertyKeyByID() {

		Integer id = null;

		try {

			ScenarioProperty scenarioProperty = new ScenarioProperty();
			scenarioProperty.setPropertyKey(key);
			scenarioProperty.setPropertyValue(value);

			if (scenarioPropertyService.isPersisted(scenarioProperty.getPropertyKey(),
					scenarioProperty.getPropertyValue())) {
				throw new Exception("Scenario property already exist");
			}

			ReportProperty reportProperty = scenarioPropertyService.create(scenarioProperty);
			Assert.assertNotNull(reportProperty);
			id = reportProperty.getIndex1();
			Assert.assertNotNull(id);
			ReportProperty scenarioPropertyFromDB = scenarioPropertyService.get(id);
			assertEquals(key, scenarioPropertyFromDB.getPropertyKey());
			assertEquals(value, scenarioPropertyFromDB.getPropertyValue());

			String propertyKey = scenarioPropertyService.getPropertyKey(reportProperty.getIndex1());
			Assert.assertEquals("Expecting key to be equal", key, propertyKey);

		} catch (Exception e) {
			log.error(e);
		} finally {
			if (id != null) {
				scenarioPropertyService.delete(id);
			}
		}

	}

	/**
	 * This method will test the retrieval of unique scenario Property Keys from DB.<br>
	 */
	@Test
	public void getUniqueTestPropertyKeyList() throws Exception {

		Collection<String> keySet = new HashSet<String>();
		int numOfKeys = 2;
		for (int i = 0; i < numOfKeys; i++) {
			ScenarioProperty scenarioProperty = new ScenarioProperty();
			scenarioProperty.setPropertyKey(key);
			scenarioProperty.setPropertyValue(value + i);

			if (scenarioPropertyService.isPersisted(scenarioProperty.getPropertyKey(),
					scenarioProperty.getPropertyValue())) {
				throw new Exception("Scenario property already exist");
			}

			ReportProperty reportProperty = scenarioPropertyService.create(scenarioProperty);
			Assert.assertNotNull(reportProperty);
			Integer id = reportProperty.getIndex1();
			Assert.assertNotNull(id);
			keySet.add(scenarioProperty.getPropertyKey());
		}
		try {

			List<String> scenarioPropertyUniqueKeyList = scenarioPropertyService.getUniquePropertyKeyList();
			assertEquals(scenarioPropertyUniqueKeyList.size(), keySet.size());

		} catch (Exception e) {
			log.error(e);
		}

	}

	/**
	 * This method will test the retrieval of all the scenario property key values in DB.<br>
	 * <p/>
	 * 1. Created an X scenario property with the same key.<br>
	 * 2. retrieves from DB and check to see if all values returned.<br>
	 * 3. deletes all the scenario properties created.<br>
	 * 
	 * @throws Exception
	 */
	@Test
	public void getUniqueScenarioPropertyValuesByKey() throws Exception {

		Integer numberOfProperties = 3;
		List<ScenarioProperty> scenarioProperties = new ArrayList<ScenarioProperty>();
		List<Integer> IDs = new ArrayList<Integer>();

		try {
			// Creation of the scenario property;
			for (int i = 0; i < numberOfProperties; i++) {
				scenarioProperties.add(new ScenarioProperty(key, (value + i)));
			}

			// Writing the scenario Property to DB.
			for (ReportProperty scenarioProperty : scenarioProperties) {
				ReportProperty reportProperty = scenarioPropertyService.create(scenarioProperty);
				Assert.assertNotNull(reportProperty);
				IDs.add(reportProperty.getIndex1());
			}

			Set<String> scenarioPropertyValuesByKey = scenarioPropertyService.getPropertyValuesByKey(key);

			assertEquals(numberOfProperties, (Integer) scenarioPropertyValuesByKey.size());

		} finally {
			for (Integer id : IDs) {
				scenarioPropertyService.delete(id);
			}
		}

	}

	@Test
	public void getScenarioProperty() {
		ReportProperty reportProperty = scenarioPropertyService.get(44479);
		if (reportProperty instanceof ScenarioProperty) {
			log.info("scenarioProperty");
		}
		log.info("not scenarioProperty");
	}
}
