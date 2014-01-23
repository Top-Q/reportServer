package il.co.topq.systems.report.common.utils;

import il.co.topq.systems.report.common.infra.log.ReportLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class IPResolver {

	private static Logger logger = ReportLogger.getInstance().getLogger(IPResolver.class);

	public static String getServerIP() {
		try {
			String lanIp = getMyLanIp();
			String lanHostAndPort = "http://" + lanIp + ":" + ServerMetadataHolder.getServersPort();
			String wanIp = getMyWanIp();
			String wanHostAndPort = "http://" + wanIp + ":" + ServerMetadataHolder.getServersPort();
			WebResource resource = Client.create().resource(
					wanHostAndPort + ServerMetadataHolder.getApplicationContextPath() + "/index.html");
			try {
				resource.accept(MediaType.TEXT_HTML_TYPE).get(ClientResponse.class);
				return wanIp;
			} catch (Exception e) {
				resource = Client.create().resource(
						lanHostAndPort + ServerMetadataHolder.getApplicationContextPath() + "/index.html");
				resource.accept(MediaType.TEXT_HTML_TYPE).get(ClientResponse.class);
				return lanIp;
			}

		} catch (Exception e) {
			logger.error("error occured while trying to get wan/lan address, using localhost ");
			return "localhost";
		}
	}

	public static String getMyLanIp() {
		logger.info("getting Lan ip");
		String localhost = "127.0.0.1";
		try {
			localhost = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			logger.info("Failed to get the local host address");
			e.printStackTrace();
		}
		logger.info("Lan ip retrieved: " + localhost);
		return localhost;

	}

	public static String getMyWanIp() throws Exception {
		logger.info("getting Wan ip");

		URL whatismyip = new URL("http://checkip.amazonaws.com");
		BufferedReader in = null;
		try {
			try {
				in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
			} catch (Exception e) {
				e.printStackTrace();
			}

			in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
			String ip = in.readLine();
			logger.info("Wan ip retrieved: " + ip);
			return ip;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}