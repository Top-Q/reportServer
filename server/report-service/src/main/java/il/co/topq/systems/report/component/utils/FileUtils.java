package il.co.topq.systems.report.component.utils;

import il.co.topq.systems.report.common.infra.log.ReportLogger;

import java.io.File;

import org.apache.log4j.Logger;

public class FileUtils {
	
	private static Logger log = ReportLogger.getInstance().getLogger(FileUtils.class);

	/**
	 * This method will delete the log directory associated with the scenario.<br>
	 * Method deletes the root directory folder and all of its sub folders and files recursively<br>
	 * 
	 * @param directory
	 *            -
	 * @return boolean to indicate success of the operation.
	 */
	public static boolean removeDirectory(File directory) throws Exception {

		if (directory != null) {
			try {
				if (!directory.exists())
					return true;
				if (!directory.isDirectory())
					return false;

				String[] list = directory.list();

				if (list != null) {
					for (String aList : list) {
						File entry = new File(directory, aList);

						if (entry.isDirectory()) {
							if (!removeDirectory(entry))
								return false;
						} else {
							if (!entry.delete())
								return false;
						}
					}
				}

				return directory.delete();
			} catch (Exception e) {
				log.error(e);
				throw e;
			}
		} else {
			return false;
		}
	}
}
