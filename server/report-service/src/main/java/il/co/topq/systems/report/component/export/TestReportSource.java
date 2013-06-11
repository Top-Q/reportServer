package il.co.topq.systems.report.component.export;

import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.model.Test;
import il.co.topq.systems.report.component.settings.ConfigurationService;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class TestReportSource implements JRDataSource {

	public final short PASS = 0;
	public final short FAILED = 1;
	public final short WARNED = 2;

	private List<Test> tests;
	private Integer index = -1; // increments on the first run when stepping in
								// next()
								// before get value.

	public List<Test> getTests() {
		return tests;
	}

	public void setTests(List<Test> tests) {
		this.tests = tests;
	}

	public TestReportSource(List<Test> tests) {
		this.tests = tests;

	}

	private File getStatusIMGPath(String imgName) throws Exception {
		String rootPath = ConfigurationService.getRootPath();
		File webInfFile = new File(rootPath, "WEB-INF");
		File resourcesFile = new File(webInfFile, "resources");
		return new File(resourcesFile, imgName);
	}

	public Object getFieldValue(JRField jrField) throws JRException {

		String fieldName = jrField.getName();
		Test test = tests.get(index);

		if (fieldName.equalsIgnoreCase("duration")) {
			Double duration = test.getDuration();
			if (duration == null) {
				return JasperUtil.formatDuration(0);
			}
			return JasperUtil.formatDuration(duration);
		} else if (fieldName.equalsIgnoreCase("scenarioName")) {
			return test.getScenario().getScenarioName();
		} else if (fieldName.equalsIgnoreCase("testName")) {
			return test.getTestDescription().getTestName();
		} else if (fieldName.equalsIgnoreCase("startTime")) {
			SimpleDateFormat formatter = new SimpleDateFormat(
					"dd/MM/yy HH:mm:ss");
			Date d = new Date(test.getStartTime());
			return formatter.format(d);
		} else if (fieldName.equalsIgnoreCase("status")) {
			Short status = test.getStatus();
			try {
				switch (status) {
				case PASS:
					return new FileInputStream(getStatusIMGPath("pass.png"));
				case FAILED:
					return new FileInputStream(getStatusIMGPath("fail.png"));
				case WARNED:
					return new FileInputStream(getStatusIMGPath("warning.png"));
				default:
					return new FileInputStream(getStatusIMGPath("noStatus.png"));
				}
			} catch (Exception e) {
				return null;
			}
		} else if (fieldName.equalsIgnoreCase("failCause")) {
			return test.getFailCause();
		} else if (fieldName.equalsIgnoreCase("#")) {
			return ((Integer) (index + 1)).toString();
		} else if (fieldName.contains("config")) {
			// Configuration fields - for example configKey:Build;
			String propertyName = fieldName.split(":")[1];
			Iterator<ReportProperty> it = test.getProperties().iterator();
			while (it.hasNext()) {
				ReportProperty property = it.next();
				if (property.getPropertyKey().equals(propertyName))
					return property.getPropertyValue();
			}
		}
		return null;
	}

	public boolean next() throws JRException {

		if (index < (tests.size() - 1)) {
			index++;
			return true;
		}
		return false;
	}

}

// package il.co.topq.systems.report.component.export;
//
// import il.co.topq.systems.report.common.model.Test;
// import il.co.topq.systems.report.component.settings.ConfigurationService;
// import net.sf.jasperreports.engine.JRDataSource;
// import net.sf.jasperreports.engine.JRException;
// import net.sf.jasperreports.engine.JRField;
//
// import java.io.File;
// import java.text.DateFormat;
// import java.util.Date;
// import java.util.List;
//
// public class TestReportSource implements JRDataSource {
//
// public final short PASS = 0;
// public final short FAILED = 1;
// public final short WARNED = 2;
//
// private List<Test> tests;
// private Integer index = -1; // increments on the first run when stepping in
// // next()
//
// // before get value.
//
// public List<Test> getTests() {
// return tests;
// }
//
// public void setTests(List<Test> tests) {
// this.tests = tests;
// }
//
// public TestReportSource(List<Test> tests) {
// this.tests = tests;
//
// }
//
// private String getStatusIMGPath(String imgName) throws Exception {
// String rootPath = ConfigurationService.getRootPath();
// File webInfFile = new File(rootPath, "WEB-INF");
// File resourcesFile = new File(webInfFile, "resources");
// return (new File(resourcesFile, imgName)).toString();
// }
//
// @Override
// public Object getFieldValue(JRField jrField) throws JRException {
//
// String fieldName = jrField.getName();
// Test test = tests.get(index);
//
// if (fieldName.equalsIgnoreCase("duration")) {
// Double duration = test.getDuration();
// if (duration == null) {
// return JasperUtil.formatDuration(0);
// }
// return JasperUtil.formatDuration(duration);
// } else if (fieldName.equalsIgnoreCase("scenarioName")) {
// return test.getScenario().getScenarioName();
// } else if (fieldName.equalsIgnoreCase("testName")) {
// return test.getTestDescription().getTestName();
// } else if (fieldName.equalsIgnoreCase("startTime")) {
// Date d = new Date(test.getStartTime());
// return DateFormat.getInstance().format(d);
// } else if (fieldName.equalsIgnoreCase("status")) {
// Short status = test.getStatus();
// try {
// switch (status) {
// case PASS:
// return getStatusIMGPath("pass.png");
// case FAILED:
// return getStatusIMGPath("fail.png");
// case WARNED:
// return getStatusIMGPath("warning.png");
// }
// } catch (Exception e) {
// return null;
// }
// } else if (fieldName.equalsIgnoreCase("failCause")) {
// return test.getFailCause();
// } else if (fieldName.equalsIgnoreCase("#")) {
// return ((Integer) (index + 1)).toString();
// }
//
// return null;
// }
//
// @Override
// public boolean next() throws JRException {
//
// if (index < (tests.size() - 1)) {
// index++;
// return true;
// }
// return false;
// }
//
// }
