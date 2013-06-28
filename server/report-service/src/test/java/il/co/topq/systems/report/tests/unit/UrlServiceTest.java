package il.co.topq.systems.report.tests.unit;

import javax.ws.rs.core.MediaType;

import il.co.topq.systems.report.common.infra.log.ReportLogger;
import il.co.topq.systems.report.component.service.ApplicationUrlService;
import il.co.topq.systems.report.component.utils.CheckIp;
import il.co.topq.systems.report.tests.infra.WebserviceBaseTest;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/applicationContext.xml" })
public class UrlServiceTest extends WebserviceBaseTest {

	private Logger log = ReportLogger.getInstance().getLogger(this.getClass());

	@Autowired
	ApplicationUrlService urlService;

	@Test
	public void getUrlServiceTest() {
		
		Assert.assertNotNull("the Url Service is null",urlService);
		
		String reportServiceUrl = urlService.getReportServiceUrl();
		
		log.info("the server url is ="+reportServiceUrl);
		
		WebResource resource = Client.create().resource(reportServiceUrl);
		ClientResponse res = resource.accept(MediaType.TEXT_HTML_TYPE).get(	ClientResponse.class);
		
		
		Assert.assertTrue("The report server was not return Http status of 200OK - url = "+reportServiceUrl, res.getStatus() == HttpStatus.OK.value());
		
	
	}


}
