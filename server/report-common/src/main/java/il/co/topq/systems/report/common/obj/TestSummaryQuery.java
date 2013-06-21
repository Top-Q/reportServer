package il.co.topq.systems.report.common.obj;

import il.co.topq.systems.report.common.model.TestSummary;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author eran_g
 * 
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TestSummaryQuery {

	@XmlElement
	private TestQuery testQuery;

	@XmlElement
	private TestSummary testSummary;

	public TestSummaryQuery() {

	}

	public TestSummaryQuery(TestQuery testQuery, TestSummary testSummary) {
		this.testQuery = testQuery;
		this.testSummary = testSummary;
	}

	public TestQuery getTestQuery() {
		return testQuery;
	}

	public void setTestQuery(TestQuery testQuery) {
		this.testQuery = testQuery;
	}

	public TestSummary getTestSummary() {
		return testSummary;
	}

	public void setTestSummary(TestSummary testSummary) {
		this.testSummary = testSummary;
	}

	@Override
	public String toString() {
		return "TestSummaryQuery [testQuery=" + testQuery + "\ntestSummary="
				+ testSummary + "]";
	}

}
