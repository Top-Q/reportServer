package il.co.topq.systems.report.component.service;

import il.co.topq.systems.report.common.infra.log.ReportLogger;
import il.co.topq.systems.report.component.utils.CheckIp;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Service("applicationUrlService")
public class ApplicationUrlServiceImpl implements ApplicationUrlService {

	private Logger log = ReportLogger.getInstance().getLogger(this.getClass());

	@Context
	private ServletContext servletContext;

	@Override
	public String getReportServiceContextPath() {
		return servletContext.getContextPath();
	}

	@Override
	public String getReportServiceUrl() {
			return "http://"+ getReportServiceHost()+":"+getReportServicePort()+"/report-service/index.html";
	}

	private String getApplicationPort() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getReportServiceHost() {
		String host =  CheckIp.getMyLanIp();
		try {

			String wanIp = "http://" + CheckIp.getMyWanIp() + ":"+getReportServicePort();

			WebResource resource = Client.create().resource(wanIp + "/report-service/index.html");
			ClientResponse res = resource.accept(MediaType.TEXT_HTML_TYPE).get(	ClientResponse.class);
			
			if (res.getStatus() == HttpStatus.NOT_FOUND.value()
					|| res.getStatus() == HttpStatus.BAD_REQUEST.value()
					|| res.getStatus() == HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()) {
				//host =  CheckIp.getMyLanIp();
			} else {
				host =  CheckIp.getMyWanIp();
			}

		} catch (Exception e) {
			log.info("Error occured while trying to get wan/lan address, using lan address.");
		}

		return host;
	}

	@Override
	public String getReportServicePort() {
		// TODO Auto-generated method stub
		return "8080";
	}

	@Override
	public String getHtmlLogsStorageDirUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHtmlLogsStorageHost() {
		// TODO HTML LOG PORT FROM PROPERTIES- Use Service
		return "8080";
	}

	@Override
	public String getHtmlLogsStoragePort() {
		// TODO Auto-generated method stub
		return "8080";
	}

}
