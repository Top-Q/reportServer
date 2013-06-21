package il.co.topq.systems.report.component.export;

import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.model.Scenario;
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

public class ScenarioDetailedReportSource implements JRDataSource {

	public final short PASS = 0;
	public final short FAILED = 1;
	public final short WARNED = 2;

	Integer index = -1; // increments on the first run when stepping in next()
	// before get value.

	Scenario scenario;
	List<Test> tests;

	public ScenarioDetailedReportSource(Scenario scenario, List<Test> tests) {
		this.scenario = scenario;
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
			double duration = test.getDuration();
			return JasperUtil.formatDuration(duration);
		} else if (fieldName.equalsIgnoreCase("description")) {
			return scenario.getDescription();
		} else if (fieldName.equalsIgnoreCase("total")) {
			return scenario.getRunTest().toString();
		} else if (fieldName.equalsIgnoreCase("passed")) {
			return scenario.getSuccessTests().toString();
		} else if (fieldName.equalsIgnoreCase("failed")) {
			return scenario.getFailTests().toString();
		} else if (fieldName.equalsIgnoreCase("warned")) {
			return scenario.getWarningTests().toString();
		} else if (fieldName.equalsIgnoreCase("scenarioDuration")) {
			double duration = scenario.getDuration();
			return JasperUtil.formatDuration(duration);
		} else if (fieldName.equalsIgnoreCase("scenarioStartTime")) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
			Date d = new Date(scenario.getStartTime());
			return formatter.format(d);
		} else if (fieldName.equalsIgnoreCase("scenarioName")) {
			return scenario.getScenarioName();
		} else if (fieldName.equalsIgnoreCase("testName")) {
			return test.getTestDescription().getTestName();
		} else if (fieldName.equalsIgnoreCase("startTime")) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
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

	// private TestQuery getTestQuery() {
	// TestQuery testQuery = new TestQuery();
	// SortingColumn sortingColumn = new SortingColumn("startTime", true);
	// testQuery.setSortingColumn(sortingColumn);
	// return testQuery;
	// }

}

// package il.co.topq.systems.report.component.export;
//
// import il.co.topq.systems.report.common.model.Scenario;
// import il.co.topq.systems.report.common.model.ScenarioProperty;
// import il.co.topq.systems.report.common.model.Test;
// import il.co.topq.systems.report.component.settings.ConfigurationService;
// import net.sf.jasperreports.engine.JRDataSource;
// import net.sf.jasperreports.engine.JRException;
// import net.sf.jasperreports.engine.JRField;
//
// import java.io.File;
// import java.text.DateFormat;
// import java.util.ArrayList;
// import java.util.Date;
// import java.util.List;
// import java.util.Set;
//
// public class ScenarioDetailedReportSource implements JRDataSource {
//
// public final short PASS = 0;
// public final short FAILED = 1;
// public final short WARNED = 2;
//
// Integer index = -1; // increments on the first run when stepping in next()
// // before get value.
//
// Scenario scenario;
// List<Test> tests;
//
// public ScenarioDetailedReportSource(Scenario scenario) {
//
// this.scenario = scenario;
// this.tests = new ArrayList<Test>(scenario.getTests());
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
// String fieldName = jrField.getName();
// Test test = tests.get(index);
//
// if (fieldName.equalsIgnoreCase("duration")) {
// double duration = test.getDuration();
// return JasperUtil.formatDuration(duration);
// } else if (fieldName.equalsIgnoreCase("description")) {
// return scenario.getDescription();
// } else if (fieldName.equalsIgnoreCase("total")) {
// return scenario.getRunTest().toString();
// } else if (fieldName.equalsIgnoreCase("passed")) {
// return scenario.getSuccessTests().toString();
// } else if (fieldName.equalsIgnoreCase("failed")) {
// return scenario.getFailTests().toString();
// } else if (fieldName.equalsIgnoreCase("warned")) {
// return scenario.getWarningTests().toString();
// } else if (fieldName.equalsIgnoreCase("scenarioDuration")) {
// double duration = scenario.getDuration();
// return JasperUtil.formatDuration(duration);
// } else if (fieldName.equalsIgnoreCase("scenarioStartTime")) {
// Date d = new Date(scenario.getStartTime());
// return DateFormat.getInstance().format(d);
// } else if (fieldName.equalsIgnoreCase("scenarioName")) {
// return scenario.getScenarioName();
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
//
// } else if (fieldName.equalsIgnoreCase("failCause")) {
// return test.getFailCause();
// } else if (fieldName.equalsIgnoreCase("#")) {
// return ((Integer) (index + 1)).toString();
// } else if (fieldName.contains("properties")) {
// Set<ScenarioProperty> scenarioProperties = scenario.getScenarioProperties();
// String result = "";
// for (ScenarioProperty scenarioProperty : scenarioProperties) {
//
// result += scenarioProperty.getPropertyKey() + "=" +
// scenarioProperty.getPropertyValue() + ";  ";
// }
// return result;
//
// }
//
// return null;
//
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
