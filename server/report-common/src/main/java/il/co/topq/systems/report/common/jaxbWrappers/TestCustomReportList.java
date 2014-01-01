package il.co.topq.systems.report.common.jaxbWrappers;

import il.co.topq.systems.report.common.model.TestCustomReport;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
public class TestCustomReportList {

	@XmlElement(required = true)
	private List<TestCustomReport> customReports;

	public TestCustomReportList() {
	}

	public TestCustomReportList(List<TestCustomReport> customReports) {
		this.customReports = customReports;
	}

	public List<TestCustomReport> getCustomReports() {
		return customReports;
	}

	public void setCustomReports(List<TestCustomReport> customReports) {
		this.customReports = customReports;
	}
}
