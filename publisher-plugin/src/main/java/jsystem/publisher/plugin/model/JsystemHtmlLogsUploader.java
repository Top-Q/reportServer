package jsystem.publisher.plugin.model;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

import jsystem.utils.FileUtils;
import junit.framework.Assert;

import org.apache.http.HttpEntity;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * use this class to upload log files to http server (instead of ftp server)
 * Object of this class create zip file from log directory and after that send
 * multipart request to http server
 */
public class JsystemHtmlLogsUploader implements FileUploaderInterface, Callable<Boolean> {

	private String serverUrl = null;

	private String zipFilePath = null;

	private static Logger log = Logger.getLogger(JsystemHtmlLogsUploader.class.getName());

	private String scenarioId;

	private String url;

	private File logDir;


	/**
	 * 
	 * @param logDir
	 *            -log files directory
	 * 
	 */
	public JsystemHtmlLogsUploader(String serverUrl, File logDir, String scenarioId) {
		super();
		this.logDir = logDir;
		this.scenarioId = scenarioId;
		this.serverUrl = "http://" + serverUrl + "/report-service/file/upload/";
	}

	/**
	 * the upload action
	 */
	@Override
	public Boolean call() throws Exception {
		Boolean upload = true;
		try {
			String zipFile = zipLogFolder();
			upload();
			deleteZip(zipFile);
		} catch (Exception e) {
			upload = false;
			log.error(e.getMessage());
		}
		return upload;
	}

	private void deleteZip(String zipFile) {
		log.info("Delete the zip file");
		File zip = new File(zipFile);
		zip.delete();
	}

	private String zipLogFolder() throws IOException {
		
		
		/**
		 * zip log directory
		 */
		zipFilePath = System.getProperty("user.dir") + File.separator + scenarioId + ".zip";

		log.info("Zip folder - "+logDir );
		
		FileUtils.zipDirectory(logDir.getAbsolutePath(), null, zipFilePath);
		
		return zipFilePath;
		
	}

	/**
	 * String url =
	 * "http://localhost:8080/report-service/report/file/upload/?scenarioId=1234"
	 * ;
	 * 
	 * @throws Exception
	 */
	public void upload() throws Exception {

		log.info("Upload zip file action");
		try {
			// Sending the HTTP
			HttpClient client = new DefaultHttpClient();

			client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
			String uploadUrl =this.serverUrl + "?scenarioId=" + scenarioId;
			log.info("Upload url is "+uploadUrl);
			HttpPost post = new HttpPost(uploadUrl);
			MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

			File zipFile = new File(zipFilePath);
			
			// For File parameters
			entity.addPart("file", new FileBody((zipFile), "application/zip"));

			post.setEntity(entity);
			long start = System.currentTimeMillis() / 1000;
			log.info("File size(Zip) =" + zipFile.length() / 1024 + " KB");
			log.info("Start upload =" + start);

			// Here we go!
			HttpEntity entityResult = client.execute(post).getEntity();
			String response = EntityUtils.toString(entityResult, "UTF-8");
			client.getConnectionManager().shutdown();
			Assert.assertTrue(response, !response.contains("Error"));

			long end = System.currentTimeMillis() / 1000;
			log.info("End upload =" + end);
			log.info("UPLOAD TOOK =" + (end - start) + " Secs");
		} catch (Exception e) {
			log.error(e);
			throw new Exception("The Upload action failed", e);
		}

	}

	@Override
	public void setDomain(String domain) {
		serverUrl = domain;

	}

	@Override
	public void setUrl(String url) {
		this.url = url;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public String getFilePath() {
		return zipFilePath;
	}

	public void setFilePath(String filePath) {
		this.zipFilePath = filePath;
	}

	public File getLogDir() {
		return logDir;
	}

	public void setLogDir(File logDir) {
		this.logDir = logDir;
	}

	public String getScenarioId() {
		return scenarioId;
	}

	public void setScenarioId(String scenarioId) {
		this.scenarioId = scenarioId;
	}

}
