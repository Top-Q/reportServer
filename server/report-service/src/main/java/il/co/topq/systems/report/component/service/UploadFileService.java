package il.co.topq.systems.report.component.service;

import il.co.topq.systems.report.common.infra.log.ReportLogger;
import il.co.topq.systems.report.common.model.Scenario;
import il.co.topq.systems.report.common.utils.ServerMetadataHolder;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

/**
 * 
 * @author liel.ran this class was integrated with the spring version of the report server by Eran.Golan, coding and
 *         business logic was made by Liel.Ran
 * 
 */
@Path("/")
@Controller
public class UploadFileService {

	private boolean uncompressOperation = true;

	@Autowired
	private ScenarioService scenarioService;

	@Context
	private ServletContext servletContext;
	private String scenarioLogLocation;

	private Logger log = ReportLogger.getInstance().getLogger(this.getClass());

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
	public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail, @QueryParam("scenarioId") Integer scenarioId)
			throws IOException {

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

		log.info("-------------------------------------uploadFile----------------------------------------");

		String fileName = fileDetail.getFileName();

		log.info("Upload file " + fileDetail.getFileName() + ", size=" + fileDetail.getSize());
		long start = System.currentTimeMillis() / 1000;
		log.info("time start=" + start);
		Long scenarioLogFolderName = System.currentTimeMillis();
		String uploadedFileLocation = getUploadFileLocation(fileDetail, scenarioLogFolderName);
		String realPath = uploadedFileLocation;
		String zip = uploadedFileLocation + File.separator + fileName;

		log.info("Save file into -" + uploadedFileLocation);

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
		log.info("------------------------------server handle upload time is=" + (end - start)
				+ " secs------------------------------------\n\n");

		response = Response.status(HttpStatus.OK.value()).entity(uploadedFileLocation).build();

		try {
			log.info("setting scenario's log directory");
			Scenario scenario = scenarioService.get(scenarioId);
			if (scenario != null) {
				String htmlDir = "http://" + ServerMetadataHolder.getServerIP() + ":"
						+ ServerMetadataHolder.getServersPort() + "/" + ServerMetadataHolder.getScenarioLogFolder()
						+ "/" + scenarioLogFolderName + "/index.html";
				log.info(htmlDir);
				scenario.setHtmlDir(htmlDir);
				scenarioService.update(scenario);

			} else {
				log.warn("the Scenario was not found,please check that the Scenario id is exist before using the upload API");
			}

		} catch (Exception e) {
			log.error(e);
		}

		return response;
	}

	// private String getHtmlLogsStorageUrl() {
	// return "http://" + ServerMetadataHolder.getServerIP() + ":" + ServerMetadataHolder.getServersPort();
	// // try {
	// // String lan = "http://" + CheckIp.getMyLanIp() + ":" + ServerMetadataHolder.getServersPort();
	// // String wanIp = "http://" + CheckIp.getMyWanIp() + ":" + ServerMetadataHolder.getServersPort();
	// // WebResource resource = Client.create().resource(
	// // wanIp + ServerMetadataHolder.getApplicationContextPath() + "/index.html");
	// // try {
	// // resource.accept(MediaType.TEXT_HTML_TYPE).get(ClientResponse.class);
	// // return wanIp;
	// // } catch (Exception e) {
	// // resource = Client.create().resource(
	// // lan + ServerMetadataHolder.getApplicationContextPath() + "/index.html");
	// // resource.accept(MediaType.TEXT_HTML_TYPE).get(ClientResponse.class);
	// // return lan;
	// // }
	// //
	// // } catch (Exception e) {
	// // log.error("error occured while trying to get wan/lan address, using localhost ");
	// // return "localhost";
	// // }
	// }

	/**
	 * TODO: change this function into thread
	 * 
	 * @param uploadedFileLocation
	 * @throws IOException
	 * @throws ZipException
	 */
	private void uncompressOperation(String workingdir, String uploadedFileLocation) {

		try {
			File file = new File(uploadedFileLocation);
			ZipFile zipFile = new ZipFile(file);
			Enumeration entries = zipFile.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) entries.nextElement();

				if (entry.isDirectory()) {
					// Assume directories are stored parents first then
					// children.
					System.err.println("Extracting directory: " + entry.getName());
					// This is not robust, just for demonstration purposes.
					(new File(workingdir + File.separator + entry.getName())).mkdir();
					continue;
				}

				System.err.println("Extracting file: " + entry.getName());
				copyInputStream(zipFile.getInputStream(entry), new BufferedOutputStream(new FileOutputStream(workingdir
						+ File.separator + entry.getName())));
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
	public static void unzipFileIntoDirectory(ZipFile zipFile, File jiniHomeParentDir) {
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

				f = new File(jiniHomeParentDir.getAbsolutePath() + File.separator + entry.getName());

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

	private final void copyInputStream(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[2024];
		int len;

		while ((len = in.read(buffer)) >= 0)
			out.write(buffer, 0, len);

		in.close();
		out.close();
	}

	private String getRelativeScenarioLogPath(long scenarioId) {
		return ServerMetadataHolder.getScenarioLogFolder() + "/" + scenarioId;
		// if (this.scenarioLogLocation != null && !this.scenarioLogLocation.isEmpty()) {
		// return this.scenarioLogLocation + "/" + scenarioId;
		// } else {
		// return DEFAULT_REPORT_LOGS_FOLDER + "/" + scenarioId;
		// }
	}

	private String createLogFolder(String logPath) {
		try {
			File resultsDir = new File(logPath);

			if (!resultsDir.exists()) {
				if (!resultsDir.mkdirs()) {
					log.info("Failed to create the current upload directory - " + resultsDir.getAbsolutePath());
				} else {
					log.info("created a folder for the current upload = " + resultsDir);
				}
			}
			return resultsDir.getAbsolutePath();
		} catch (Exception e) {
			log.error("failed creating log folder");
			return null;
		}
	}

	private String getUploadFileLocation(final FormDataContentDisposition fileDetail, final long logFolderName) {

		String tomcatWebappFolder = ServerMetadataHolder.getTomcatWebappsFolderPath();
		// String realPath = servletContext.getRealPath("");
		// log.info(realPath);
		// File f = new File(realPath);
		// String tomcatWebappFolder ;
		// if (f.exists()){
		// tomcatWebappFolder = f.getParent();
		// log.info(tomcatWebappFolder);
		// }else {
		// log.error("error could not get tomcat webapps folder");
		// return null;
		// }
		String logPath = getRelativeScenarioLogPath(logFolderName);

		// TODO: 22.1.14 need to validate this block of code works! custom log location feature not yet been released
		if (this.scenarioLogLocation != null && !this.scenarioLogLocation.isEmpty()) {
			try {
				log.info("using scenario log location set from property file: " + this.scenarioLogLocation);
				log.info("relative log path: " + logPath);
				return createLogFolder(logPath);
			} catch (Exception e) {
				log.error("failed creating log folder");
			}
		}

		log.info("using default log folder: " + ServerMetadataHolder.getScenarioLogFolder()
				+ " will saved in tomcat context");

		// String realResultsPath = servletContext.getRealPath(logPath);

		String realResultsPath = tomcatWebappFolder + "/" + logPath;

		return createLogFolder(realResultsPath);

		// String logFolder = "";
		//
		// logFolder = String.valueOf(logFolderName);
		// // Don't change the '/' char to File.separator
		// // it's for URI Usage and NOT File path Usage
		// logFolder = getReportLogsFolder() + '/' + logFolder;
		//
		// String realResultsPath = servletContext.getRealPath(logFolder);
		// File resultsDir = new File(realResultsPath);
		//
		// if (!resultsDir.exists()) {
		//
		// if (!resultsDir.mkdirs()) {
		// log.info("Failed to create the current upload directory - " +
		// resultsDir.getAbsolutePath());
		// } else {
		// log.info("created a folder for the current upload = " + resultsDir);
		// }
		// }
		// return logFolder;
	}

	/**
	 * TODO: will check the parameter from the DB is unZip/unrar (Uncompress) feature is on
	 * 
	 * @return
	 */
	private boolean isUncompressOperation() {
		return uncompressOperation;
	}

	public void setUncompressOperation(boolean uncompressOperation) {
		this.uncompressOperation = uncompressOperation;
	}

	/**
	 * TODO : create a thread that will handle the write task
	 * 
	 * @param uploadedInputStream
	 * @param uploadedFileLocation
	 * @throws IOException
	 */
	// save uploaded file to new location
	private void writeToFile(InputStream uploadedInputStream, String zipFullPath) throws IOException {
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
}
