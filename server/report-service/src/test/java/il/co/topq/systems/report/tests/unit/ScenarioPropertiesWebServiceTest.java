package il.co.topq.systems.report.tests.unit;

import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.model.Scenario;
import il.co.topq.systems.report.component.jaxbWrappers.PropertyList;
import il.co.topq.systems.report.component.utils.URLParts;
import il.co.topq.systems.report.service.infra.PropertyService;
import il.co.topq.systems.report.service.infra.ScenarioService;
import il.co.topq.systems.report.service.infra.TestService;
import il.co.topq.systems.report.tests.infra.WebserviceBaseTest;
import il.co.topq.systems.report.utils.RandomData;

import java.util.List;

import junit.framework.Assert;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/applicationContext.xml" })
public class ScenarioPropertiesWebServiceTest extends WebserviceBaseTest {

	@Autowired
	private ScenarioService scenarioService;

	@Autowired
	private TestService testService;

	@Autowired
	private PropertyService<ReportProperty> scenarioPropertyService;

	// TESTED
	/**
	 * This method will test the retrieval of unique scenario properties.<br>
	 * <p/>
	 * 1. Creates a scenario with properties.<br>
	 * 2. Retrieve a list of scenario property key.<br>
	 * 3. For each key in DB there should be at least 1 scenario holding the key.<br>
	 * 4. Deletes the scenario from DB<br>
	 * 
	 * @throws Exception
	 */
	@org.junit.Test
	public void getUniquePropertyKeys() throws Exception {

		Scenario scenario = scenarioService.create(RandomData.getScenarioWithTests(3));
		Assert.assertNotNull(scenario);
		List<ReportProperty> uniquePropertiesKeyList = getWebResource(URLParts.SCENARIO_UNIQUE_PROPERTY_KEYS_URL).get(
				PropertyList.class).getProperties();
		Assert.assertNotNull(uniquePropertiesKeyList);
		Assert.assertFalse(uniquePropertiesKeyList.isEmpty());
		scenarioService.delete(scenario.getId());

	}

	// /**
	// * This method will test the retrieval of the highest version scenario
	// * property exist in DB.<br>
	// * <p/>
	// * 1. Creates scenario with version properties<br>
	// * 2. Gets the highest Version property<br>
	// *
	// * @throws Exception
	// */
	// @org.junit.Test
	// public void getScenarioLastVersion() throws Exception {
	//
	// Integer scenarioID;
	// scenarioWebServiceTest.setScenarioPropertiesAsVersionProperties();
	// scenarioID = scenarioWebServiceTest.createScenarioUtil();
	// getScenarioLastVersionUtil();
	// if (scenarioID != null) {
	// scenarioWebServiceTest.deleteScenarioUtil(scenarioID);
	// }
	// }
	// public double getScenarioLastVersionUtil() throws Exception {
	//
	// GenericType<JAXBElement<Double>> genericType = new
	// GenericType<JAXBElement<Double>>() {
	// };
	//
	// Double max;
	// max = getWebResource(URLParts.SCENARIO_LAST_VERSION_PROPERTY_URL)
	// .accept(MediaType.APPLICATION_XML)
	// .type(MediaType.APPLICATION_XML).post(genericType).getValue();
	//
	// Assert.assertNotNull(max);
	//
	// return max;
	// }

}
