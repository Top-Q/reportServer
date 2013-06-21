package il.co.topq.systems.report.common.model;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "property")
@XmlAccessorType(XmlAccessType.FIELD)
public class TestProperty implements Comparable<TestProperty>, ReportProperty {

	@XmlTransient
	private static final long serialVersionUID = -1035922014582944807L;
	@XmlTransient
	private Set<Test> publishedTest01s;
	@XmlTransient
	private Set<TestCustomReport> testCustomReports;
	@XmlTransient
	protected static int identifierGenerator = 0;
	@XmlTransient
	protected int identifier;
	@XmlElement
	protected String propertyKey;
	@XmlElement
	protected String propertyValue;
	@XmlAttribute
	protected Integer index1;

	@Override
	public int hashCode() {
		String key = "";
		String val = "";
		if (propertyKey != null) {
			key = propertyKey.toLowerCase();
		}
		if (propertyValue != null) {
			val = propertyValue.toLowerCase();
		}

		return (key + val).hashCode();
	}

	public void setIdentifier() {
		this.identifier = identifierGenerator;
		identifierGenerator++;
	}

	public Integer getIndex1() {
		return this.index1;
	}

	public void setIndex1(Integer index1) {
		this.index1 = index1;
	}

	public String getPropertyKey() {
		return this.propertyKey;
	}

	public void setPropertyKey(String propertyKey) {
		this.propertyKey = propertyKey;
	}

	public String getPropertyValue() {
		return this.propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public int getIdentifier() {
		return identifier;
	}

	public TestProperty() {
		setIdentifier();
	}

	public TestProperty(String propertyKey, String propertyValue, Set<Test> publishedTest01s) {
		this.propertyKey = propertyKey;
		this.propertyValue = propertyValue;
		this.publishedTest01s = publishedTest01s;
		setIdentifier();
	}

	public TestProperty(String propertyKey, String propertyValue) {
		if (!propertyKey.isEmpty()) {
			this.propertyKey = propertyKey;
		}
		this.propertyValue = propertyValue;
		setIdentifier();
	}

	public Set<Test> getPublishedTest01s() {
		return this.publishedTest01s;
	}

	public void setPublishedTest01s(Set<Test> publishedTest01s) {
		this.publishedTest01s = publishedTest01s;
	}

	public Set<TestCustomReport> getTestCustomReports() {
		return this.testCustomReports;
	}

	public void setTestCustomReports(Set<TestCustomReport> testCustomReports) {
		this.testCustomReports = testCustomReports;
	}

	@Override
	public int compareTo(TestProperty testProperty) {

		if (this.identifier == testProperty.getIdentifier()) {
			return 0;
		} else if (this.identifier > testProperty.getIdentifier()) {
			return 1;
		} else {
			return -1;
		}
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		TestProperty that = (TestProperty) o;

		return ((this.propertyKey.equals(that.getPropertyKey())) && (this.propertyValue.equals(that.getPropertyValue())));
	}

	@Override
	public String toString() {
		return "TestProperty [propertyKey=" + propertyKey + ", propertyValue=" + propertyValue + "]";
	}

	@Override
	public long getNumberOfReferences() {
		return publishedTest01s.size();
	}

	@Override
	public long getNumberOfCustomReport() {
		return testCustomReports.size();
	}
}
