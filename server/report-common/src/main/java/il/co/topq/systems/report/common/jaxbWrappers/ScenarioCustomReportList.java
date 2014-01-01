package il.co.topq.systems.report.common.jaxbWrappers;

import il.co.topq.systems.report.common.model.ScenarioCustomReport;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
public class ScenarioCustomReportList {

	@XmlElement(required = true)
	private List<ScenarioCustomReport> customReports;

	/*
	 * This c'tor is required for jaxb usage.
	 */
	public ScenarioCustomReportList() {
	}

	public ScenarioCustomReportList(List<ScenarioCustomReport> customReports) {
		this.customReports = customReports;
	}

	public List<ScenarioCustomReport> getCustomReports() {
		return customReports;
	}

	public void setCustomReports(List<ScenarioCustomReport> customReports) {
		this.customReports = customReports;
	}

}
