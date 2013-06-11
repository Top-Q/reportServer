package jsystem.publisher.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.JAXBElement;
import javax.xml.parsers.DocumentBuilder;

import jsystem.extensions.report.xml.XmlReportHandler;

import jsystem.framework.FrameworkOptions;
import jsystem.framework.JSystemProperties;
import jsystem.framework.report.ListenerstManager;
import jsystem.framework.report.RunnerListenersManager;
import jsystem.framework.report.TestReporter;
import jsystem.runner.BackgroundPublisherWaitDialog;
import jsystem.runner.ErrorLevel;
import jsystem.runner.agent.publisher.Publisher;
import jsystem.runner.agent.tests.PublishTest.ActionType;
import jsystem.runner.remote.Message;
import jsystem.runner.remote.RemoteTestRunner.RemoteMessage;
import jsystem.treeui.client.RunnerEngineManager;
import jsystem.treeui.error.ErrorDialog;
import jsystem.treeui.images.ImageCenter;
import jsystem.utils.StringUtils;
import jsystem.utils.UploadRunner;

import org.w3c.dom.Document;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

//public class BackgroundPublisher implements Publisher {
//
//	/**
//	 * DOM object used to parse the xml report file;
//	 */
//	DocumentBuilder documentBuilder;
//	/**
//	 * DOM document object
//	 */
//	Document doc;
//
//	/**
//	 * uploads the log created to report server host.
//	 */
//	UploadRunner uploader;
//
//	/**
//	 * the directory contains the log files.(Not full path)
//	 */
//	String logDir;
//
//	/**
//	 * Will save the scenarioID after created in DB.
//	 */
//	Integer scenarioID;
//	
//
//	boolean upload = true;// TODO: need to add the option to select weather or
//							// not to upload on publish.
//
//	@Override
//	public void publish() {
//		
//		try {
//			readFile();
//			scenarioID = createScenario();
//			if (upload) {
//				upload();
//				setScenarioLogDir();
//			}
//		} catch (Exception ex) {
//			ErrorDialog dialog = new ErrorDialog("Failed to publish reports ", StringUtils.getStackTrace(ex),
//					ErrorLevel.Error, false);
//			dialog.init();
//			ex.printStackTrace();
//		}
//
//	}
//
//	/**
//	 * This method will assign the scenario Log Directory to the scenario.
//	 * 
//	 * @assumption Scenario was already published and it ID is available.<br>
//	 *             logDir is initialized;
//	 */
//	private void setScenarioLogDir() {
//
//		System.out.println("Setting scenario log directory");
//
//		Client c = Client.create();
//		WebResource r;
//		ClientResponse response;
//		GenericType<JAXBElement<Boolean>> genericType = new GenericType<JAXBElement<Boolean>>() {
//		};
//
//		r = c.resource(getReportsServerURL() + "/report/scenario/setLogDir/" + "?logDir=" + logDir + "&scenarioID="
//				+ scenarioID);
//
//		response = r.accept(MediaType.APPLICATION_XML).type(MediaType.APPLICATION_XML).get(ClientResponse.class);
//
//		if (!(response.getEntity(genericType).getValue())) {
//			System.out.println("Failed to set log directory to scenario");
//		}
//	}
//
//	/**
//	 * This method will upload the scenario log to the report server.<br>
//	 * TODO: add a progress dialog / busy cursor.
//	 */
//	private void upload() {
//		new Thread() {
//			public void run() {
//				File dir = null;
//				logDir = String.valueOf(System.currentTimeMillis());
//				uploader = new UploadRunner(getCurrentLogFolder(), Long.parseLong(logDir));
//				showMsgWithTime("Uploading file to reports Server");
//				BackgroundPublisherWaitDialog.launchWaitDialog("Uploading...");
//				try {
//					try {
//						//Move folder
//						File logFolder = getCurrentLogFolder();
//						File[] files = logFolder.listFiles();
//						
//						//Clean log folder
//						File[] folders = new File(JSystemProperties.getInstance().getPreference(FrameworkOptions.LOG_FOLDER)).listFiles();
//						for(File folder : folders){
//							if(folder.isDirectory() && folder.getName().startsWith("log_"))
//								jsystem.utils.FileUtils.deleteDirectory(folder);
//						}
//						
//						// Copy to Destination directory
//						dir = new File(JSystemProperties.getInstance().getPreference(FrameworkOptions.LOG_FOLDER) + "\\log_" + String.valueOf(System.currentTimeMillis()));
//						if(!dir.mkdir()){
//							System.out.println("Publish error - fail to create logs folder\n");
//							BackgroundPublisherWaitDialog.endWaitDialog();
//							JOptionPane.showOptionDialog(null, "Moving log folder failed", "Publishing failed",
//									JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE, ImageCenter.getInstance().getImage(ImageCenter.ICON_ERROR), new String[]{"OK"}, "OK");
//							return;
//						}
//						
//						for(File file : files){
//							if (!file.renameTo(new File(dir, file.getName()))) {
//								System.out.println("Publish error - Error while moving file\n" + file.getName());
//								BackgroundPublisherWaitDialog.endWaitDialog();
//								JOptionPane.showOptionDialog(null, "Moving log folder failed","Publishing failed",
//										JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE, ImageCenter.getInstance().getImage(ImageCenter.ICON_ERROR), new String[]{"OK"}, "OK");
//								return;
//							}
//						}
//						
//						//Init Reporter
//						RunnerEngineManager.getRunnerEngine().initReporters();
//						
//						//Compress Folder to zip
//						String zipPath = System.getProperty("user.dir") + File.separator + String.valueOf(Long.parseLong(logDir)) + ".zip";
//						jsystem.utils.FileUtils.zipDirectory(dir.getAbsolutePath(), null, zipPath);
//
//						//Move the zip to old directory
//						String logToOld = JSystemProperties.getInstance().getPreference(FrameworkOptions.LOG_FOLDER) + "\\old" + File.separator + String.valueOf(Long.parseLong(logDir))+ ".zip";
//						jsystem.utils.FileUtils.copyFile(zipPath, logToOld);
//
//					} catch (Exception e) {
//						System.out.println("Publish error - fail to zip file for publishing\n"
//								+ "check that there is enough space for zipping\n\n" + StringUtils.getStackTrace(e));
//						BackgroundPublisherWaitDialog.endWaitDialog();
//						JOptionPane.showOptionDialog(null,
//								"Publish Error - fail to zip file for publishing","Publishing failed",
//								JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE, ImageCenter.getInstance().getImage(ImageCenter.ICON_ERROR), new String[]{"OK"}, "OK");
//						return;
//					}
//					
//					//set params to uploader and upload
//					uploader.setFilePath();
//					uploader.upload();
//					BackgroundPublisherWaitDialog.endWaitDialog();
//				} catch (Exception e) {
//					System.err.println("Publish error - fail to upload file to reports server"
//							+ "check that the reports server is running\n\n" + StringUtils.getStackTrace(e));
//					BackgroundPublisherWaitDialog.endWaitDialog();
//					JOptionPane.showOptionDialog(null, "Publish error - fail to upload file to reports server","Publishing failed",
//							JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE, ImageCenter.getInstance().getImage(ImageCenter.ICON_ERROR), new String[]{"OK"}, "OK");
//					showMsgWithTime("uploading failed");
//					return;
//				}
//				
//				System.gc();
//				jsystem.utils.FileUtils.deleteDirectory(dir);
//			}
//		}.start();
//
//	}
//	
//	/**
//	 * shows a msg with time to the console if on debug mode
//	 * 
//	 * @param msg
//	 *            the message to show
//	 */
//	public static void showMsgWithTime(String msg) {
//		System.out.println(getTime() + " " + msg);
//	}
//
//	/**
//	 * get current time as a String
//	 * 
//	 * @return the current time String
//	 */
//	public static String getTime() {
//		Calendar cal = new GregorianCalendar();
//		int hour24 = cal.get(Calendar.HOUR_OF_DAY); // 0..23
//		int min = cal.get(Calendar.MINUTE); // 0..59
//		int sec = cal.get(Calendar.SECOND); // 0..59
//		return hour24 + ":" + min + ":" + sec;
//	}
//
//	/**
//	 * Returns the current log folder. This method is designed for overriding.
//	 * 
//	 * @return The current log folder as specified in the jsystem properties.
//	 *         The default one is c:/jsystem/runner/log/current
//	 */
//	protected File getCurrentLogFolder() {
//		return new File(JSystemProperties.getInstance().getPreference(FrameworkOptions.LOG_FOLDER), "current");
//	}
//
//	/**
//	 * This method will read the scenario XML file and will initialize:
//	 * xmlDirectory, xmlReportFile;<br>
//	 * 
//	 * @throws Exception
//	 */
//	private void readFile() throws Exception {
//		final File xmlReportFile = new File(getCurrentLogFolder(), "reports.0.xml");
//		if (!xmlReportFile.exists()) {
//			throw new Exception("File not found: " + xmlReportFile.getPath());
//		}
//	}
//
//	/**
//	 * This method will validate report server publisher. it assumes string[0]
//	 * == host, string[1] == port;
//	 * 
//	 * @param Object
//	 *            object : dbPropertiesListener not used in this pubisher's
//	 *            validate.
//	 * @return boolean valid or not;
//	 */
//	@Override
//	public boolean validatePublisher(Object object, String... dbSettingParams) {
//
//		boolean connected = false;
//		try {
//
//			Client c = Client.create();
//			WebResource r = c.resource(getReportsServerURL() + "/report/scenario/connected");
//			ClientResponse response = r.accept(MediaType.APPLICATION_XML).type(MediaType.APPLICATION_XML)
//					.get(ClientResponse.class);
//
//			GenericType<JAXBElement<Boolean>> genericType = new GenericType<JAXBElement<Boolean>>() {
//			};
//
//			connected = response.getEntity(genericType).getValue();
//		} catch (Exception ex) {
//			connected = false;
//
//		}
//
//		return connected;
//	}
//
//	/**
//	 * This method is used when the publish event is sent from another JVM. For
//	 * example from the test JVM.
//	 * 
//	 */
//	@Override
//	public Message publish(Map<String, Object> publish_Properties) throws Exception {
//		// We are asking to do flush of the reports because we want the XML file
//		// to be updated
//		if (ListenerstManager.getInstance() instanceof RunnerListenersManager) {
//			ListenerstManager.getInstance().flushReporters();
//		}
//		Message remoteMessage = null;
//		ActionType currentActionType = ActionType.valueOf(publish_Properties.get(DBProperties.ACTION_TYPE).toString());
//
//		if (currentActionType == ActionType.publish || currentActionType == ActionType.publish_and_email) {
//			publish();
//			remoteMessage = generateRunDetailsMessage(true);
//		} else if (currentActionType == ActionType.email || currentActionType == ActionType.upload_log_files) {
//			upload();
//			remoteMessage = generateRunDetailsMessage(false);
//		} else if (currentActionType == ActionType.init_reporters_only) {
//			remoteMessage = generateRunDetailsMessage(false);
//		}
//
//		if (currentActionType == ActionType.publish_and_email || currentActionType == ActionType.email)
//			scenarioDetailsExport((String[]) publish_Properties.get("ConfigProperties"));
//
//		if (currentActionType == ActionType.publish_and_email || currentActionType == ActionType.email)
//			scenarioDetailsExport();// will create the file for the email.
//
//		if (Boolean.parseBoolean(publish_Properties.get(DBProperties.INIT_REPORT).toString())) {
//			((RunnerListenersManager) RunnerListenersManager.getInstance()).initReporters();
//		}
//		return remoteMessage;
//	}
//
//	/**
//	 * generates remote message to deliver to publish test. contains all the
//	 * parameters that relevant for this scenario
//	 * 
//	 * @param isPublished
//	 *            if false - send parameters only for email, if true - send all
//	 *            parameters
//	 * @return remote message
//	 * @throws Exception
//	 */
//	public jsystem.runner.remote.Message generateRunDetailsMessage(boolean isPublished) throws Exception {
//
//		File reportCurrent = new File(JSystemProperties.getInstance().getPreference(FrameworkOptions.LOG_FOLDER),
//				"current");
//		ArrayList<TestReporter> listeners = ((RunnerListenersManager) RunnerListenersManager.getInstance())
//				.getAllReporters();
//		for (TestReporter currentTestReporter : listeners) {
//			if (currentTestReporter.getClass() == XmlReporter.class) {
//				((XmlReporter) currentTestReporter).readElements();
//			}
//		}
//
//		DbPublish reportInfo = new DbPublish(reportCurrent);
//
//		jsystem.runner.remote.Message remoteMessage = new jsystem.runner.remote.Message();
//
//		remoteMessage.setType(RemoteMessage.M_PUBLISH);
//
//		remoteMessage.addField(reportInfo.getSecnarioName());
//		remoteMessage.addField(reportInfo.getSutName());
//		remoteMessage.addField(reportInfo.getVersion());
//		remoteMessage.addField(reportInfo.getBuild());
//		remoteMessage.addField(reportInfo.getUserName());
//		remoteMessage.addField(Long.toString(reportInfo.getStartTime())); // start
//																			// time
//		remoteMessage.addField(Integer.toString(reportInfo.getNumberOfTests())); // run.getRunTests()
//		remoteMessage.addField(Integer.toString(reportInfo.getNumberOfTestsFail())); // run.getFailTests()
//		remoteMessage.addField(Integer.toString(reportInfo.getNumberOfTestsPass())); // run.getSuccessTests()
//		remoteMessage.addField(Integer.toString(reportInfo.getNumberOfTestsWarning())); // run.getWarningTests()
//		remoteMessage.addField(reportInfo.getStation()); // run.getWarningTests()
//		remoteMessage.addField(Boolean.toString(reportInfo.isCheckTables()));
//		remoteMessage.addField(Boolean.toString(reportInfo.isUploadLogs()));
//		remoteMessage.addField(logDir);
//
//		if (isPublished) {
//			remoteMessage.addField("");// get scenario description from server
//			remoteMessage.addField("results/" + this.logDir + "/index.html");
//
//			if (scenarioID != null)
//				remoteMessage.addField(scenarioID.toString());
//			else
//				remoteMessage.addField("unknown");
//		}
//		return remoteMessage;
//	}
//
//	private void scenarioDetailsExport() {
//		Client c = Client.create();
//		WebResource r;
//		ClientResponse response;
//		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
//		queryParams.add("scenarioID", String.valueOf(scenarioID));
//
//		r = c.resource(getReportsServerURL() + "/report/export/scenarioDetailedReport/pdf");
//		response = r.accept(MediaType.APPLICATION_OCTET_STREAM).post(ClientResponse.class, queryParams);
//
//		try {
//
//			InputStream is = response.getEntityInputStream();
//			String dir = JSystemProperties.getInstance().getPreference(FrameworkOptions.LOG_FOLDER) + File.separator
//					+ "current" + File.separator;
//
//			File file = new File(dir + "execution.pdf");
//			FileOutputStream fos;
//
//			fos = new FileOutputStream(file);
//
//			byte buf[] = new byte[1024];
//			int len;
//			while ((len = is.read(buf)) > 0)
//				fos.write(buf, 0, len);
//			fos.close();
//			is.close();
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * This method will send the XML file which represents the scenario test
//	 * tree.<br>
//	 * The Report Server will parse the file and will create a scenario object
//	 * and will write it to DB.<br>
//	 * 
//	 * @return Integer: Scenario ID;
//	 * @assumptions: xmlReportFile already exist;
//	 */
//	private Integer createScenario() throws Exception {
//
//		Client c = Client.create();
//		WebResource r = c.resource(getReportsServerURL() + "/report/scenario/reportXML/");
//
//		GenericType<JAXBElement<Integer>> integerGenericType = new GenericType<JAXBElement<Integer>>() {
//		};
//		final File xmlReportFile = new File(getCurrentLogFolder(), "reports.0.xml");
//		ClientResponse response = r.accept(MediaType.APPLICATION_XML).type(MediaType.APPLICATION_XML)
//				.post(ClientResponse.class, toByteArray(xmlReportFile));
//
//		if (response.getClientResponseStatus().getStatusCode() != 200) {
//			throw new Exception("Failed to publish results\n returned code "
//					+ response.getClientResponseStatus().getStatusCode() + "\n Reason: "
//					+ response.getClientResponseStatus().toString());
//		}
//
//		return response.getEntity(integerGenericType).getValue();
//	}
//
//	/**
//	 * 
//	 * @return Reports server URL path. For example:
//	 *         http://localhost:8080/report-service
//	 */
//	private String getReportsServerURL() {
//		return "http://"
//				+ JSystemProperties.getInstance().getPreferenceOrDefault(FrameworkOptions.REPORTS_PUBLISHER_HOST) + ":"
//				+ JSystemProperties.getInstance().getPreferenceOrDefault(FrameworkOptions.REPORTS_PUBLISHER_PORT)
//				+ "/report-service";
//
//	}
//
//	/**
//	 * This method will get a File as a parameter and will return a Byte array.
//	 * 
//	 * @param File
//	 *            file
//	 * @return byte[]
//	 * @throws IOException
//	 */
//	private static byte[] toByteArray(File file) throws IOException {
//
//		long length = file.length();
//		byte[] array = new byte[(int) length];
//		InputStream in = new FileInputStream(file);
//		long offset = 0;
//		try {
//			while (offset < length) {
//				in.read(array, (int) offset, (int) (length - offset));
//				offset += length;
//			}
//		} finally {
//			in.close();
//		}
//		return array;
//	}
//
//	private void scenarioDetailsExport(String[] configList) {
//		Client c = Client.create();
//		WebResource r;
//		ClientResponse response;
//		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
//		queryParams.add("scenarioID", String.valueOf(scenarioID));
//		if (configList != null)
//			for (String param : configList)
//				queryParams.add("config:" + param, "");
//
//		r = c.resource(getReportsServerURL() + "/report/export/scenarioDetailedReport/pdf");
//		response = r.accept(MediaType.APPLICATION_OCTET_STREAM).post(ClientResponse.class, queryParams);
//
//		try {
//
//			InputStream is = response.getEntityInputStream();
//			String dir = JSystemProperties.getInstance().getPreference(FrameworkOptions.LOG_FOLDER) + File.separator
//					+ "current" + File.separator;
//
//			File file = new File(dir + "execution.pdf");
//			FileOutputStream fos;
//
//			fos = new FileOutputStream(file);
//
//			byte buf[] = new byte[1024];
//			int len;
//			while ((len = is.read(buf)) > 0)
//				fos.write(buf, 0, len);
//			fos.close();
//			is.close();
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public String[] getAllTestProperties() {
//		Client c = Client.create();
//		WebResource r = c.resource(getReportsServerURL() + "/report/testProperties/uniqueKeysString");
//		String propertiesList = r.get(String.class);
//
//		String[] properties = propertiesList.split(";");
//
//		return properties;
//	}
//}
//
