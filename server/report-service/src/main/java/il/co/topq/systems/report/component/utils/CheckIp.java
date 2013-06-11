package il.co.topq.systems.report.component.utils;

import il.co.topq.systems.report.common.infra.log.ReportLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

public class CheckIp {

	public static String getMyLanIp() throws Exception {

		String localhost = "127.0.0.1";

		try {
			localhost = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			ReportLogger.getInstance().getLogger(CheckIp.class).info("Failed to get the local host address");
			e.printStackTrace();
		}
		return localhost;

	}

	public static String getMyWanIp() throws Exception {
		URL whatismyip = new URL("http://checkip.amazonaws.com");
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
			String ip = in.readLine();
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