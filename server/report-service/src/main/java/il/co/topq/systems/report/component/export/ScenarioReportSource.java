package il.co.topq.systems.report.component.export;

import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.model.Scenario;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class ScenarioReportSource implements JRDataSource {

	private Integer index = -1; // increments on the first run when stepping in
								// next()
	// before get value.
	private List<Scenario> scenarios;

	ScenarioReportSource(List<Scenario> scenarios) {
		this.scenarios = scenarios;
	}

	public Object getFieldValue(JRField jrField) throws JRException {

		String fieldName = jrField.getName();
		Scenario scenario = scenarios.get(index);

		if (fieldName.equalsIgnoreCase("duration")) {
			Double duration = scenario.getDuration();
			if (duration == null) {
				return JasperUtil.formatDuration(0);
			}
			return JasperUtil.formatDuration(duration);
		} else if (fieldName.equalsIgnoreCase("scenarioName")) {
			return scenario.getScenarioName();
		} else if (fieldName.equalsIgnoreCase("total")) {
			return scenario.getRunTest().toString();
		} else if (fieldName.equalsIgnoreCase("failed")) {
			return scenario.getFailTests().toString();
		} else if (fieldName.equalsIgnoreCase("warning")) {
			return scenario.getWarningTests().toString();
		} else if (fieldName.equalsIgnoreCase("pass")) {
			return scenario.getSuccessTests().toString();
		} else if (fieldName.equalsIgnoreCase("startTime")) {
			SimpleDateFormat formatter = new SimpleDateFormat(
					"dd/MM/yy HH:mm:ss");
			Date d = new Date(scenario.getStartTime());
			return formatter.format(d);
		} else if (fieldName.equalsIgnoreCase("#")) {
			return ((Integer) (index + 1)).toString();
		} else if (fieldName.contains("config")) {
			// Configuration fields - for example configKey:Build;
			String propertyName = fieldName.split(":")[1];
			Iterator<ReportProperty> it = scenario.getProperties().iterator();
			while (it.hasNext()) {
				ReportProperty property = it.next();
				if (property.getPropertyKey().equals(propertyName))
					return property.getPropertyValue();
			}
		}
		return null;
	}

	public boolean next() throws JRException {
		if (index < (scenarios.size() - 1)) {
			index++;
			return true;
		}
		return false;
	}

}

// package il.co.topq.systems.report.component.export;
//
// import il.co.topq.systems.report.common.model.Scenario;
// import net.sf.jasperreports.engine.JRDataSource;
// import net.sf.jasperreports.engine.JRException;
// import net.sf.jasperreports.engine.JRField;
//
// import java.text.DateFormat;
// import java.util.Date;
// import java.util.List;
//
// public class ScenarioReportSource implements JRDataSource {
//
// private Integer index = -1; // increments on the first run when stepping in
// next()
// // before get value.
// private List<Scenario> scenarios;
//
// ScenarioReportSource(List<Scenario> scenarios) {
// this.scenarios = scenarios;
// }
//
// @Override
// public Object getFieldValue(JRField jrField) throws JRException {
//
// String fieldName = jrField.getName();
// Scenario scenario = scenarios.get(index);
//
// if (fieldName.equalsIgnoreCase("duration")) {
// Double duration = scenario.getDuration();
// if (duration == null) {
// return JasperUtil.formatDuration(0);
// }
// return JasperUtil.formatDuration(duration);
// } else if (fieldName.equalsIgnoreCase("scenarioName")) {
// return scenario.getScenarioName();
// } else if (fieldName.equalsIgnoreCase("total")) {
// return scenario.getRunTest().toString();
// } else if (fieldName.equalsIgnoreCase("failed")) {
// return scenario.getFailTests().toString();
// } else if (fieldName.equalsIgnoreCase("warning")) {
// return scenario.getWarningTests().toString();
// } else if (fieldName.equalsIgnoreCase("pass")) {
// return scenario.getSuccessTests().toString();
// } else if (fieldName.equalsIgnoreCase("startTime")) {
// Date d = new Date(scenario.getStartTime());
// return DateFormat.getInstance().format(d);
// } else if (fieldName.equalsIgnoreCase("#")) {
// return ((Integer) (index + 1)).toString();
// }
//
// return null;
// }
//
// @Override
// public boolean next() throws JRException {
// if (index < (scenarios.size() - 1)) {
// index++;
// return true;
// }
// return false;
// }
//
// }
