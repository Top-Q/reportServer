package il.co.topq.systems.report.component.service;

import il.co.topq.systems.report.common.infra.log.ReportLogger;
import il.co.topq.systems.report.common.model.Scenario;
import il.co.topq.systems.report.component.utils.CheckIp;
import il.co.topq.systems.report.service.infra.ScenarioService;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

/**
 * 
 * @author liel.ran this class was integrated with the spring version of the
 *         report server by Eran.Golan, coding and business logic was made by
 *         Liel.Ran
 * 
 */
@Path("/")
@Controller
public class UploadFileService {

	// TODO: consider putting in const file in case being used more than once.
	private static final String DEFAULT_SERVER_PORT = "8080";
	private boolean uncompressOperation = true;
	private String reportLogsFolder = "results";

	@Autowired
	private ScenarioService scenarioService;

	private String serverPort;

	private String scenarioLogLocation;

	private Logger log = ReportLogger.getInstance().getLogger(this.getClass());

	@Context
	private ServletContext servletContext;

	public UploadFileService() {
		super();
	}

	@GET
	@Path("/isAlive")
	@Produces(MediaType.TEXT_PLAIN)
	public String isAlive() {
		return "alive";
	}

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@QueryParam("scenarioId") Integer scenarioId) throws IOException {

		// AbstractApplicationContext beanFactory = new
		// ClassPathXmlApplicationContext("/applicationContext.xml");
		// scenarioService = (ScenarioService)
		// beanFactory.getBean("scenarioService");

		log.info("----------------Upload Service------------------");

		String uplpadFailed = "Error - The upload action failed(see server logs)";
		Response response = Response.status(500).entity(uplpadFailed).build();
		if (scenarioId == null) {
			String error = "Error - scenarioId must be not Null";
			log.error(error);
			return Response.status(500).entity(error).build();
		}
		if (isUncompressOperation()) {
			if (!fileDetail.getFileName().contains(".zip")) {
				String error = "Error - the incoming file must be zip file";
				log.error(error);
				return Response.status(500).entity(error).build();
			}
		}

		log.info("-----------------------------------------------uploadFile ---------------------------------------------------");

		String fileName = fileDetail.getFileName();

		// TODO: check if the scenario ID is valid
		log.info("Upload file " + fileDetail.getFileName() + ", size="
				+ fileDetail.getSize());
		long start = System.currentTimeMillis() / 1000;
		log.info("time start=" + start);

		String uploadedFileLocation = getUploadFileLocation(fileDetail,
				scenarioId);
		String realPath = servletContext.getRealPath(uploadedFileLocation);

		// set the path of the incoming file
		String zip = realPath + File.separator + fileName;

		log.info("Save file into -" + uploadedFileLocation);

		// save it
		writeToFile(uploadedInputStream, zip);

		if (isUncompressOperation()) {
			ZipFile zipFile = new ZipFile(zip);

			unzipFileIntoDirectory(zipFile, new File(realPath));

			zipFile.close();

			if (new File(zip).delete()) {
				log.info("delete the zip file after the unzip action");
			} else {
				log.warn("can't delete the zip file after the unzip action");
			}
		}

		log.info(uploadedFileLocation);

		long end = System.currentTimeMillis() / 1000;
		log.info("time end =" + end);
		log.info("------------------------------server handle upload time is="
				+ (end - start)
				+ " secs------------------------------------\n\n");

		response = Response.status(HttpStatus.OK.value())
				.entity(uploadedFileLocation).build();

		try {
			log.info("in set scenario log dir web service");
			boolean isLogDirSet = false;
			Scenario scenario = scenarioService.get(scenarioId);
			if (scenario != null) {

				String url = getHtmlLogsStorageUrl();
				String contextPath = servletContext.getContextPath();

				String htmlRelativePath = url + contextPath + "/"
						+ uploadedFileLocation + "/index.html";
				scenario.setHtmlDir(htmlRelativePath);
				scenarioService.update(scenario);

			} else {
				log.warn("the Scenario was not found,please check that the Scenario id is exist before using the upload API");
			}

			isLogDirSet = true;
		} catch (Exception e) {
			log.error(e);
		}

		return response;

	}

	private String getHtmlLogsStorageUrl() {
		// TODO: create support for storage url that is not localhost
		// String url = "http://" + CheckIp.getMyLanIp() + ":8080";
		String url = "http://" + CheckIp.getMyLanIp() + ":" + serverPort;
		try {

			// String wanIp = "http://" + CheckIp.getMyWanIp() + ":8080";
			String wanIp = "http://" + CheckIp.getMyWanIp() + ":" + serverPort;

			WebResource resource = Client.create().resource(
					wanIp + "/report-service/index.html");
			ClientResponse res = resource.accept(MediaType.TEXT_HTML_TYPE).get(
					ClientResponse.class);
			if (res.getStatus() == HttpStatus.NOT_FOUND.value()
					|| res.getStatus() == HttpStatus.BAD_REQUEST.value()
					|| res.getStatus() == HttpStatus.UNSUPPORTED_MEDIA_TYPE
							.value()) {
				// url = "http://" + CheckIp.getMyLanIp() + ":8080";
				url = "http://" + CheckIp.getMyLanIp() + ":" + serverPort;
			} else {
				url = wanIp;
			}

		} catch (Exception e) {
			log.error("error occured while trying to get wan/lan address, using localhost ");
		}

		return url;

	}

	/**
	 * TODO: change this function into thread
	 * 
	 * @param uploadedFileLocation
	 * @throws IOException
	 * @throws ZipException
	 */
	private void uncompressOperation(String workingdir,
			String uploadedFileLocation) {

		try {
			File file = new File(uploadedFileLocation);
			ZipFile zipFile = new ZipFile(file);
			Enumeration entries = zipFile.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) entries.nextElement();

				if (entry.isDirectory()) {
					// Assume directories are stored parents first then
					// children.
					System.err.println("Extracting directory: "
							+ entry.getName());
					// This is not robust, just for demonstration purposes.
					(new File(workingdir + File.separator + entry.getName()))
							.mkdir();
					continue;
				}

				System.err.println("Extracting file: " + entry.getName());
				copyInputStream(zipFile.getInputStream(entry),
						new BufferedOutputStream(new FileOutputStream(
								workingdir + File.separator + entry.getName())));
			}

			zipFile.close();
		} catch (IOException ioe) {
			System.err.println("Unhandled exception:");
			ioe.printStackTrace();
			return;
		}

	}

	/**
	 * @param zipFile
	 * @param jiniHomeParentDir
	 */
	public static void unzipFileIntoDirectory(ZipFile zipFile,
			File jiniHomeParentDir) {
		Enumeration files = zipFile.entries();
		File f = null;
		FileOutputStream fos = null;
		InputStream eis = null;
		while (files.hasMoreElements()) {
			try {
				ZipEntry entry = (ZipEntry) files.nextElement();
				eis = zipFile.getInputStream(entry);
				byte[] buffer = new byte[1024];
				int bytesRead = 0;

				f = new File(jiniHomeParentDir.getAbsolutePath()
						+ File.separator + entry.getName());

				if (entry.isDirectory()) {
					f.mkdirs();
					continue;
				} else {
					f.getParentFile().mkdirs();
					f.createNewFile();
				}

				fos = new FileOutputStream(f);

				while ((bytesRead = eis.read(buffer)) != -1) {
					fos.write(buffer, 0, bytesRead);
				}
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			} finally {
				if (fos != null) {
					try {
						fos.close();

					} catch (IOException e) {
						// ignore
					}
				}
				if (eis != null) {
					try {
						eis.close();
					} catch (IOException e) {
						// ignore
					}
				}
			}
		}
	}

	private final void copyInputStream(InputStream in, OutputStream out)
			throws IOException {
		byte[] buffer = new byte[2024];
		int len;

		while ((len = in.read(buffer)) >= 0)
			out.write(buffer, 0, len);

		in.close();
		out.close();
	}

	private String getUploadFileLocation(
			final FormDataContentDisposition fileDetail,
			final long logFolderName) {

		log = ReportLogger.getInstance().getLogger(this.getClass());
		log.info("create folder for the upload");
		String logFolder = "";
		String reportFolder = getReportLogsFolder();
		String path = " ";
		// TODO:configure this from outside parameter

		path = servletContext.getContextPath();

		// String resultsPath = path + File.separator + reportFolder;
		String resultsPath = reportFolder;

		logFolder = String.valueOf(logFolderName);// +"/"+String.valueOf(System.currentTimeMillis());
		// Don't change the '/' char to File.separator
		// it's for URI Usage and NOT File path Usage
		logFolder = reportFolder + "/" + logFolder;

		String realResultsPath = servletContext.getRealPath(logFolder);
		File resultsDir = new File(realResultsPath);

		if (!resultsDir.exists()) {

			if (!resultsDir.mkdirs()) {
				System.err.println("Failed to create the current upload  dir- "
						+ resultsDir.getAbsolutePath());
			} else {
				log.info("created a folder for the current upload ="
						+ resultsDir);
				// logFolder = servletContext.getContextPath();
			}
		}
		return logFolder;

	}

	/**
	 * TODO: will check the parameter from the DB is unZip/unrar (Uncompress)
	 * feature is on
	 * 
	 * @return
	 */
	private boolean isUncompressOperation() {
		return uncompressOperation;
	}

	/**
	 * 
	 * TODO: change this function to return the root storage Path from the DB
	 * 
	 * @return root storage path
	 */
	private String getReportLogsFolder() {
		return reportLogsFolder;
	}

	public void setUncompressOperation(boolean uncompressOperation) {
		uncompressOperation = uncompressOperation;
	}

	public void setReportLogsFolder(String reportLogsFolder) {
		this.reportLogsFolder = reportLogsFolder;
	}

	/**
	 * TODO : create a thread that will handle the write task
	 * 
	 * @param uploadedInputStream
	 * @param uploadedFileLocation
	 * @throws IOException
	 */
	// save uploaded file to new location
	private void writeToFile(InputStream uploadedInputStream, String zipFullPath)
			throws IOException {
		OutputStream out = null;
		try {
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(zipFullPath));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}

		}

	}

	@Value("${server.port}")
	public void setServerPort(String serverPort) {
		if (serverPort != null && !serverPort.isEmpty()) {
			this.serverPort = serverPort;
		} else {
			this.serverPort = DEFAULT_SERVER_PORT;
		}
	}

	@Value("${scenario.log.location}")
	public void setScenarioLogLocation(String scenarioLogLocation) {
		if (scenarioLogLocation != null && !scenarioLogLocation.isEmpty()) {
			this.scenarioLogLocation = scenarioLogLocation;
		} else {
			this.scenarioLogLocation = getDefaultScenarioLogLocation();
		}
	}

	/**
	 * This method will return the scenario default log folder location to be
	 * saved in case one was not configured by the user in the properties file.
	 * 
	 * @return
	 */
	private String getDefaultScenarioLogLocation() {
		return null;
	}
}
