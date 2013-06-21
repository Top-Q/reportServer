package il.co.topq.systems.report.component.jaxbWrappers;

import il.co.topq.systems.report.common.model.TestSummary;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
public class TestSummaryList {

	@XmlElement(required = true)
	private List<TestSummary> testsSummary;

	/*
	 * default empty c'tor is required for jaxb marshling
	 */
	public TestSummaryList() {
	}

	public TestSummaryList(List<TestSummary> testsSummary) {
		this.testsSummary = testsSummary;
	}

	public List<TestSummary> getTestsSummary() {
		return testsSummary;
	}

	public void setTestsSummary(List<TestSummary> testsSummary) {
		this.testsSummary = testsSummary;
	}

}
