package il.co.topq.systems.report.common.obj;

import il.co.topq.systems.report.common.exception.SoftwareException;
import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.model.TestProperty;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(TestProperty.class)
public class TestQuery extends AbstractQuery {

	@XmlElementWrapper(name = "testProperties")
	Collection<ReportProperty> properties;

	@XmlElement
	private int groupBy = 0;

	/*
	 * required for jaxb usage
	 */
	public TestQuery() {
	}

	public Collection<ReportProperty> getProperties() {
		return properties;
	}

	public void setProperties(Collection<ReportProperty> properties) {
		this.properties = properties;
	}

	public int getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(int groupBy) {
		this.groupBy = groupBy;
	}

	public String[] getGroupByQueryString() {
		switch (groupBy) {
		case 0:
			return null;
		case 1:
			return new String[] { "test_description.testName", "packageName" };
		case 2:
			return new String[] { "test_description.testName", "packageName", "scenarioName" };
		case 3:
			return new String[] { "test_description.testName", "packageName", "params" };
		case 4:
			return new String[] { "test_description.testName", "packageName", "scenarioName", "params" };
		default:
			throw new SoftwareException("group out of valid range: groupBy= " + groupBy);
		}
	}

	@Override
	public String toString() {
		return "TestQuery [chunk=" + chunk + ", timeRange=" + timeRange + ", testProperties=" + properties
				+ ", sortingColumn=" + sortingColumn + ", groupBy=" + groupBy + "]";
	}

}
