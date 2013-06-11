package il.co.topq.systems.report.tests.infra;

import il.co.topq.systems.report.component.utils.URLParts;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class WebserviceBaseTest {

	protected WebResource getWebResource(String uri) {
		return Client.create().resource(URLParts.URI + uri);
	}
}
