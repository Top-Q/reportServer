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
public class ScenarioProperty implements Comparable<ScenarioProperty>, ReportProperty {

	@XmlTransient
	private static final long serialVersionUID = -4851217952305792602L;

	@XmlTransient
	private Set<ScenarioCustomReport> scenarioCustomReports;
	@XmlTransient
	private Set<Scenario> publishedRuns01s;

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

	public ScenarioProperty() {
		setIdentifier();
	}

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

	public int getIdentifier() {
		return identifier;
	}

	public ScenarioProperty(String propertyKey, String propertyValue, Set<ScenarioCustomReport> scenarioCustomReports,
			Set<Scenario> publishedRuns01s) {
		this.propertyKey = propertyKey;
		this.propertyValue = propertyValue;
		this.scenarioCustomReports = scenarioCustomReports;
		this.publishedRuns01s = publishedRuns01s;
		setIdentifier();
	}

	public ScenarioProperty(String propertyKey, String propertyValue) {
		this.propertyKey = propertyKey;
		this.propertyValue = propertyValue;
		setIdentifier();
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

	public Set<ScenarioCustomReport> getScenarioCustomReports() {
		return this.scenarioCustomReports;
	}

	public void setScenarioCustomReports(Set<ScenarioCustomReport> scenarioCustomReports) {
		this.scenarioCustomReports = scenarioCustomReports;
	}

	public Set<Scenario> getPublishedRuns01s() {
		return this.publishedRuns01s;
	}

	public void setPublishedRuns01s(Set<Scenario> publishedRuns01s) {
		this.publishedRuns01s = publishedRuns01s;
	}

	@Override
	public int compareTo(ScenarioProperty scenarioProperty) {

		if (this.identifier == scenarioProperty.getIdentifier()) {
			return 0;
		} else if (this.identifier > scenarioProperty.getIdentifier()) {
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

		ScenarioProperty that = (ScenarioProperty) o;

		return ((this.propertyKey.equals(that.getPropertyKey())) && (this.propertyValue.equals(that.getPropertyValue())));
	}

	@Override
	public String toString() {
		return "ScenarioProperty [propertyKey=" + propertyKey + ", propertyValue=" + propertyValue + "]";
	}

	@Override
	public long getNumberOfReferences() {
		return publishedRuns01s.size();
	}

	@Override
	public long getNumberOfCustomReport() {
		return scenarioCustomReports.size();
	}
}
