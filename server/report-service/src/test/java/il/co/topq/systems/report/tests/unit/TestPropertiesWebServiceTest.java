package il.co.topq.systems.report.tests.unit;

import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.model.TestProperty;
import il.co.topq.systems.report.component.jaxbWrappers.PropertyList;
import il.co.topq.systems.report.component.utils.URLParts;
import il.co.topq.systems.report.service.infra.PropertyService;
import il.co.topq.systems.report.tests.infra.WebserviceBaseTest;

import java.util.List;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/applicationContext.xml" })
public class TestPropertiesWebServiceTest extends WebserviceBaseTest {

	@Autowired
	private PropertyService<ReportProperty> testPropertyService;

	@org.junit.Test
	public void getUniqueTestPropertyKeys() throws Exception {

		ReportProperty property1 = testPropertyService.create(new TestProperty("key", "value"));
		ReportProperty property2 = testPropertyService.create(new TestProperty("key", "value2"));
		Assert.assertNotNull(property1);
		Assert.assertNotNull(property2);
		List<ReportProperty> uniquePropertiesKeyList = getWebResource(URLParts.TEST_UNIQUE_PROPERTY_KEYS_URL).get(
				PropertyList.class).getProperties();
		Assert.assertEquals("expecting 1 unique key", 1, uniquePropertiesKeyList.size());
		testPropertyService.delete(property1.getIndex1());
		testPropertyService.delete(property2.getIndex1());
	}
}
