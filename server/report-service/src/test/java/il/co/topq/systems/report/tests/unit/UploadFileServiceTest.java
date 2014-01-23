package il.co.topq.systems.report.tests.unit;

import il.co.topq.systems.report.common.infra.log.ReportLogger;
import il.co.topq.systems.report.utils.RandomData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import junit.framework.Assert;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * this class was integrated with the spring version of the report server by Eran.Golan, coding and business logic was
 * made by Liel.Ran
 * 
 * @author liel.ran
 * 
 */
public class UploadFileServiceTest {

	private Logger log = ReportLogger.getInstance().getLogger(this.getClass());

	/**
	 * 
	 * this function was copy from FileUtils
	 * 
	 * 
	 * Zip given directory
	 * 
	 * @param directory
	 *            String
	 * @param fileExtention
	 *            String
	 * @param destinationFile
	 *            String
	 * @throws IOException
	 *             If Failed to create new zip file
	 */
	public static void zipDirectory(final String directory, File destinationFile) throws IOException {

		File zipFile = destinationFile;
		File srcFolder = new File(directory);
		if (srcFolder != null && srcFolder.isDirectory()) {
			Iterator<File> i = org.apache.commons.io.FileUtils.iterateFiles(srcFolder, null, true);
			// Iterator<File> i = FileUtils.iterateFiles(srcFolder, new String
			// []{"xcf"}, true);
			/*
			 * public static Iterator<File> iterateFiles(File directory, String[] extensions, boolean recursive)
			 * directory - the directory to search in extensions - an array of extensions, ex. {"java","xml"}. If this
			 * parameter is null, all files are returned. recursive - if true all subdirectories are searched as well
			 */

			zipFile.createNewFile();

			OutputStream outputStream = null;
			ArchiveOutputStream zipOutputStream = null;

			try {
				outputStream = new FileOutputStream(zipFile);
				zipOutputStream = new ZipArchiveOutputStream(outputStream);
				int srcFolderLength = srcFolder.getAbsolutePath().length() + 1; // +1
																				// to
																				// remove
																				// the
																				// last
																				// file
																				// separator
				while (i.hasNext()) {
					File file = i.next();
					String relativePath = file.getAbsolutePath().substring(srcFolderLength);
					ArchiveEntry zipArchiveEntry = new ZipArchiveEntry(relativePath);
					zipOutputStream.putArchiveEntry(zipArchiveEntry);
					FileInputStream fis = null;
					try {
						fis = new FileInputStream(file);
						IOUtils.copy(fis, zipOutputStream);
					} finally {
						fis.close();
					}
					zipOutputStream.closeArchiveEntry();
				}

			} finally {
				zipOutputStream.flush();
				zipOutputStream.finish();
				zipOutputStream.close();
			}
		}

	}

	@SuppressWarnings("deprecation")
	@org.junit.Test
	public void uploadServiceTest() throws Exception {

		final String uploadFileName = "upload.zip";
		final String logFolderName = "Current";
		long currentTimeMillis = System.currentTimeMillis();
		final String dirName = "tempDir" + currentTimeMillis;
		final String htmlName = "tempHtmlLogFile.html";
		final int randomSize = (int) (5 + (Math.random() * (15 - 5)));
		String url = "http://localhost:8080/report-server/file/upload/?scenarioId=" + currentTimeMillis / 1000;
		File dirToDelete = null;

		System.out.println(url);
		try {
			// create the main directory
			File dir = new File(dirName);
			boolean created = dir.mkdirs();
			Assert.assertTrue("the temp folder was not created - test failed", created);
			dirToDelete = dir;

			File logFolder = new File(dir, logFolderName);
			created = logFolder.mkdirs();
			Assert.assertTrue("the temp folder was not created - test failed", created);

			// Create Temp empty File inside the temp Dir
			File html = new File(logFolder, htmlName);
			try {
				created = html.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Assert.assertTrue("the temp file was not created - test failed", created);

			// TODO: improve impl
			// this function take about 2-3 mins for 25 mb
			log.info("The Log html file with random size =" + randomSize);

			html = RandomData.generateRandomFile(randomSize, html);
			Assert.assertTrue("the HTML file(inside the log folder) was not created with data - test failed",
					html.exists());

			long htmlFileLength = html.length();

			File zipFile = new File(dir, uploadFileName);
			zipDirectory(dir.getAbsolutePath(), zipFile);
			Assert.assertTrue("the zip file(of the log folder) was not created - test failed", zipFile.exists());

			// Sending the HTTP
			HttpClient client = new DefaultHttpClient();

			client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

			HttpPost post = new HttpPost(url);
			MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

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
			log.info(response);

			url = "http://localhost:8080/report-server/" + response + "/" + logFolderName + "/" + htmlName;
			client = new DefaultHttpClient();
			client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

			HttpGet get = new HttpGet(url);

			log.info("trying to get the uploaded file from the server ");
			log.info(url);

			// Here we go again!
			HttpResponse getResponse = client.execute(get);

			log.info(response);

			response = EntityUtils.toString(getResponse.getEntity(), "UTF-8");

			client.getConnectionManager().shutdown();
			StatusLine statusLine = getResponse.getStatusLine();
			Assert.assertTrue("the file(upload html after unzip the uploaded file) was not found on the server",
					statusLine.getStatusCode() == 200);

			long contentLength = getResponse.getEntity().getContentLength();
			Assert.assertEquals("the created html len=" + htmlFileLength + ", and the uploaded html on the server len="
					+ contentLength + ",Test failed!", htmlFileLength, contentLength);
			log.info("the Html Files are the same sizes");

		} catch (Exception e) {
			log.error("Exception in upload test - " + e.getMessage());
		} finally {

			// Delete//
			deleteFiles(dirToDelete);

			Assert.assertFalse("The Temp Dir folder was not deleted after the delete temp folder funcation",
					dirToDelete.exists());
		}
	}

	private void deleteFiles(File dir) {

		try {
			if (dir != null) {

				if (dir.isDirectory()) {
					File[] listFiles = dir.listFiles();
					for (File file : listFiles) {
						deleteFiles(file);
					}
				}
				dir.delete();
			}
		} catch (Exception e) {
			log.error("can't delete file -" + dir.getAbsolutePath());
		}

	}

}
