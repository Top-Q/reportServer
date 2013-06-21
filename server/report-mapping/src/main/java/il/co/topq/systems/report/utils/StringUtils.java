package il.co.topq.systems.report.utils;

import java.util.Properties;

public class StringUtils {
	
	private final static String separator = "/SEP/";
	private final static int separatorLength = separator.length();
	/**
	 * convert a converted String back to properties (only if it was initially
	 * converted by this class)
	 * 
	 * @param propString
	 *            the converted String
	 * @return the original properties
	 */
	public static Properties stringToProperties(String propString) {
		if (propString == null)
			return null;
		Properties p = new Properties();
		String key, value;
		int equallInd, separatorInd;
		while (!propString.equals("")) {
			separatorInd = propString.indexOf(separator);
			equallInd = propString.indexOf("=");
			key = propString.substring(0, equallInd);
			if (separatorInd > -1) {
				value = propString.substring(equallInd + 1, separatorInd);
				propString = propString.substring(separatorInd
						+ separatorLength);
			} else {
				value = propString
						.substring(equallInd + 1, propString.length());
				propString = "";
			}
			p.setProperty(key, value);
		}
		return p;
	}
}
