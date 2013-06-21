package jsystem.publisher.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.JAXBElement;
import javax.xml.parsers.DocumentBuilder;

import jsystem.framework.FrameworkOptions;
import jsystem.framework.JSystemProperties;
import jsystem.framework.report.ListenerstManager;
import jsystem.framework.report.Reporter;
import jsystem.publisher.plugin.model.JsystemHtmlLogsUploader;
import jsystem.runner.agent.publisher.Publisher;
import jsystem.runner.agent.publisher.PublisherException;
import jsystem.utils.UploadRunner;

import org.jfree.util.Log;
import org.w3c.dom.Document;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class NewReportsServerPublisher implements Publisher {

	private static final String REPORT_FILE_NAME = "report_server_summary.pdf";

	private static final Reporter report = ListenerstManager.getInstance();
	/**
	 * DOM object used to parse the xml report file;
	 */
	DocumentBuilder documentBuilder;
	/**
	 * DOM document object
	 */
	Document doc;

	/**
	 * uploads ths log created to report server host.
	 */
	UploadRunner uploader;

	/**
	 * the directory contains the log files.(Not full path)
	 */
	// private String logDir;

	/**
	 * Will save the scenarioID after created in DB.
	 */
	Integer scenarioID;

	// /**
	// * This method will assign the scenario Log Directory to the scenario.
	// *
	// * @assumption Scenario was already published and it ID is available.<br>
	// * logDir is initialized;
	// */
	// private void setScenarioLogDir() {
	//
	// System.out.println("Setting scenario log directory");
	//
	// Client c = Client.create();
	// WebResource r;
	// ClientResponse response;
	// GenericType<JAXBElement<Boolean>> genericType = new GenericType<JAXBElement<Boolean>>() {
	// };
	//
	// r = c.resource(getReportsServerURL() + "/report/scenario/setLogDir/" + "?logDir=" + logDir + "&scenarioID="
	// + scenarioID);
	//
	// response = r.accept(MediaType.APPLICATION_XML).type(MediaType.APPLICATION_XML).get(ClientResponse.class);
	//
	// if (!(response.getEntity(genericType).getValue())) {
	// System.out.println("Failed to set log directory to scenario");
	// }
	//
	// }

	private long getFolderSize(final File... selectedDirectories) {
		long foldersize = 0;
		for (final File item : selectedDirectories) {
			for (final File subItem : item.listFiles()) {
				if (subItem.isDirectory()) {
					foldersize += getFolderSize(subItem);
				} else {
					foldersize += subItem.length();
				}
			}
		}
		return foldersize;
	}

	/**
	 * This method will upload the scenario log to the report server.<br>
	 * TODO: add a progress dialog / busy cursor.
	 * 
	 * @throws Exception
	 */
	private void upload() throws Exception {
		File currentLogFolder;
		try {
			currentLogFolder = getCurrentLogFolder();
			long freeSpace = currentLogFolder.getFreeSpace();
			long logSize = getFolderSize(currentLogFolder);

			showMsgWithTime("Free space =" + freeSpace + ", log folder size=" + logSize);
			if (freeSpace < logSize) {
				System.err.println("Publish error- can't zip the log folder. the partition free space is " + freeSpace
						+ ", and the log folder size is " + logSize
						+ ".there is no enougth space for uplodaing the log folder \n\n");
				return;
			}
		} catch (Exception e) {
			System.err.println(e);
			return;
		}

		// Executor service that runs a maximum of 1 threads at a time:
		ExecutorService executor = Executors.newFixedThreadPool(1);
		List<Future<Boolean>> results = new ArrayList<Future<Boolean>>();

		Callable<Boolean> uploadTask = new JsystemHtmlLogsUploader(getServerUrl(), currentLogFolder,
				String.valueOf(scenarioID));

		showMsgWithTime("Uploading file to reports Server");

		results.add(executor.submit(uploadTask));

		executor.shutdown();

		boolean isDone = executor.awaitTermination(90, TimeUnit.MINUTES);

		if (isDone) {
			Log.info("Upload Job is done.");
			if (results.get(0).get()) {
				Log.info("the Upload was finish successfully!");
			} else {
				Log.error("Error - the Upload Job was not finish successfully.");
			}
		} else {
			Log.error("Error - the Upload Job was not finish after 90 mins.");
		}
	}

	/**
	 * shows a msg with time to the console if on debug mode
	 * 
	 * @param msg
	 *            the message to show
	 */
	public static void showMsgWithTime(String msg) {
		System.out.println(getTime() + " " + msg);
	}

	/**
	 * get current time as a String
	 * 
	 * @return the current time String
	 */
	public static String getTime() {
		Calendar cal = new GregorianCalendar();
		int hour24 = cal.get(Calendar.HOUR_OF_DAY); // 0..23
		int min = cal.get(Calendar.MINUTE); // 0..59
		int sec = cal.get(Calendar.SECOND); // 0..59
		return hour24 + ":" + min + ":" + sec;
	}

	/**
	 * Returns the current log folder. This method is designed for overriding.
	 * 
	 * @return The current log folder as specified in the jsystem properties. The default one is
	 *         c:/jsystem/runner/log/current
	 */
	protected File getCurrentLogFolder() {
		return new File(JSystemProperties.getInstance().getPreference(FrameworkOptions.LOG_FOLDER), "current");
	}

	/**
	 * This method will read the scenario XML file and will initialize: xmlDirectory, xmlReportFile;<br>
	 * 
	 * @throws Exception
	 */
	private void assertXmlFileExists() throws Exception {
		final File xmlReportFile = new File(getCurrentLogFolder(), "reports.0.xml");
		if (!xmlReportFile.exists()) {
			throw new Exception("File not found: " + xmlReportFile.getPath());
		}
	}

	/**
	 * This method will validate report server publisher. it assumes string[0] == host, string[1] == port;
	 * 
	 * @param Object
	 *            object : dbPropertiesListener not used in this pubisher's validate.
	 * @return boolean valid or not;
	 */
	@Override
	public boolean isUp() {
		try {
			Client c = Client.create();
			WebResource r = c.resource(getReportsServerURL() + "/report/scenario/connected");
			ClientResponse response = r.accept(MediaType.APPLICATION_XML).type(MediaType.APPLICATION_XML)
					.get(ClientResponse.class);

			GenericType<JAXBElement<Boolean>> genericType = new GenericType<JAXBElement<Boolean>>() {
			};

			return response.getEntity(genericType).getValue();
		} catch (Exception ex) {
			return false;

		}
	}

	/**
	 * This method is used when the publish event is sent from another JVM. For example from the test JVM.
	 * 
	 * @throws Exception
	 * 
	 */
	@Override
	public Map<String, String> publish(String description, boolean uploadLogs) throws PublisherException {
		return publish(description, uploadLogs, null);

	}

	@Override
	public Map<String, String> publish(String description, boolean uploadLogs, String[] publishOptions)
			throws PublisherException {
		try {
			assertXmlFileExists();
			scenarioID = sendPublishData();// send the report0.xml

			// logDir = String.valueOf(System.currentTimeMillis());
			// Assert.assertNotNull("Failed to assign log dir name", logDir);

			if (uploadLogs) {
				upload();
			}

			scenarioDetailsExport(publishOptions);

		} catch (Exception e) {
			throw new PublisherException("Publishing to the report server server failed.", e);
		}
		return createPublisherReturnMap();

	}

	/**
	 * Gets the server URL according to the parameters specified in the JSystem properties
	 */
	private static String getServerUrl() throws Exception {
		return JSystemProperties.getInstance().getPreferenceOrDefault(FrameworkOptions.REPORTS_PUBLISHER_HOST) + ":"
				+ JSystemProperties.getInstance().getPreferenceOrDefault(FrameworkOptions.REPORTS_PUBLISHER_PORT);
	}

	private Map<String, String> createPublisherReturnMap() {
		Map<String, String> returnMap = new HashMap<String, String>();
		// if (logDir == null) {
		// Log.error("Log dir is null");
		// return null;
		// }
		returnMap.put("HTML Reports", String.format("http://%s:%s/report-service/results/%s/index.html",
				JSystemProperties.getInstance().getPreferenceOrDefault(FrameworkOptions.REPORTS_PUBLISHER_HOST),
				JSystemProperties.getInstance().getPreferenceOrDefault(FrameworkOptions.REPORTS_PUBLISHER_PORT),
				scenarioID));
		return returnMap;
	}

	/**
	 * Pull from the server the PDF with the execution details
	 * 
	 * @param configList
	 */
	private void scenarioDetailsExport(final String[] configList) {
		Client c = Client.create();
		WebResource r;
		ClientResponse response;
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		queryParams.add("scenarioID", String.valueOf(scenarioID));
		if (configList != null) {
			for (String param : configList) {
				queryParams.add("config:" + param, "");
			}
		}

		r = c.resource(getReportsServerURL() + "/export-service/export/scenarioDetailedReport/pdf");
		response = r.accept(MediaType.APPLICATION_OCTET_STREAM).post(ClientResponse.class, queryParams);
		File reportFile = null;
		try {
			reportFile = createReportFile();
		} catch (IOException e1) {
			Log.error("Failed to create report file", e1);
			return;
		}
		InputStream is = response.getEntityInputStream();
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(reportFile);
			byte buf[] = new byte[1024];
			int len;
			while ((len = is.read(buf)) > 0) {
				fos.write(buf, 0, len);
			}

		} catch (Exception e) {
			Log.error("Failed to populate report file", e);
		} finally {
			try {
				if (null != fos) {
					fos.close();
				}
				if (null != is) {
					is.close();
				}

			} catch (Exception e) {
			}

		}
	}

	private File createReportFile() throws IOException {
		final File currentLogFolder = new File(JSystemProperties.getInstance().getPreference(
				FrameworkOptions.LOG_FOLDER), "current");
		if (!currentLogFolder.exists()) {
			throw new IOException("Log folder is not exists. Can't create attachents File");
		}
		final File attachmentFolder = new File(currentLogFolder, "attachments");
		if (!attachmentFolder.exists()) {
			if (!attachmentFolder.mkdir()) {
				throw new IOException("Attachment folder is not exists and failed to create one");
			}
		}
		return new File(attachmentFolder, REPORT_FILE_NAME);
	}

	/**
	 * This method will send the XML file which represents the scenario test tree.<br>
	 * The Report Server will parse the file and will create a scenario object and will write it to DB.<br>
	 * 
	 * @return Integer: Scenario ID;
	 * @assumptions: xmlReportFile already exist;
	 */
	private Integer sendPublishData() throws Exception {

		Client c = Client.create();
		WebResource r = c.resource(getReportsServerURL() + "/report/scenario/reportXML/");

		GenericType<JAXBElement<Integer>> integerGenericType = new GenericType<JAXBElement<Integer>>() {
		};
		final File xmlReportFile = new File(getCurrentLogFolder(), "reports.0.xml");
		// ClientResponse response = r.accept(MediaType.APPLICATION_XML).type(MediaType.APPLICATION_XML)
		// .post(ClientResponse.class, toByteArray(xmlReportFile));

		String result = r.post(String.class, toByteArray(xmlReportFile));
		if (result != null) {
			Log.info("scenario id: " + result);
		} else {
			Log.info("create scenario returned null");
		}

		// if (response.getClientResponseStatus().getStatusCode() != 200) {
		// throw new Exception("Failed to publish results\n returned code "
		// + response.getClientResponseStatus().getStatusCode() + "\n Reason: "
		// + response.getClientResponseStatus().toString());
		// }
		//
		// return response.getEntity(integerGenericType).getValue();
		return Integer.valueOf(result);
	}

	/**
	 * 
	 * @return Reports server URL path. For example: http://localhost:8080/report-service
	 */
	private String getReportsServerURL() {
		return "http://"
				+ JSystemProperties.getInstance().getPreferenceOrDefault(FrameworkOptions.REPORTS_PUBLISHER_HOST) + ":"
				+ JSystemProperties.getInstance().getPreferenceOrDefault(FrameworkOptions.REPORTS_PUBLISHER_PORT)
				+ "/report-service";

	}

	/**
	 * This method will get a File as a parameter and will return a Byte array.
	 * 
	 * @param File
	 *            file
	 * @return byte[]
	 * @throws IOException
	 */
	private static byte[] toByteArray(File file) throws IOException {

		long length = file.length();
		byte[] array = new byte[(int) length];
		InputStream in = new FileInputStream(file);
		long offset = 0;
		try {
			while (offset < length) {
				in.read(array, (int) offset, (int) (length - offset));
				offset += length;
			}
		} finally {
			in.close();
		}
		return array;
	}

	@Override
	public String[] getAllPublishOptions() {
		Client c = Client.create();
		WebResource r = c.resource(getReportsServerURL() + "/report/testProperties/uniqueKeysString");
		String propertiesList = r.get(String.class);

		String[] properties = propertiesList.split(";");

		return properties;
	}

}
