package il.co.topq.systems.report.common.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlSeeAlso(ScenarioProperty.class)
@XmlAccessorType(XmlAccessType.FIELD)
public class Scenario implements Serializable, Comparable<Scenario> {

	@XmlTransient
	private static final long serialVersionUID = 1L;
	@XmlTransient
	private static int identifierGenerator = 0;
	@XmlTransient
	private int identifier;

	@XmlAttribute
	private Integer id;
	@XmlElement
	private String scenarioName;
	@XmlElement
	private long startTime;
	@XmlElement
	private Double duration;
	@XmlElement
	private Integer runTest = 0;
	@XmlElement
	private Integer failTests = 0;
	@XmlElement
	private Integer successTests = 0;
	@XmlElement
	private Integer warningTests = 0;
	@XmlElement
	private String description;
	@XmlElement
	private String htmlDir;
	@XmlElement
	private Short isDeleted;
	@XmlElement
	private Date lastUpdate;
	@XmlElementWrapper(name = "scenarioProperties")
	private Collection<ReportProperty> properties;
	@XmlTransient
	private Collection<Test> tests;
	@XmlTransient
	private String propertiesStr;

	public Scenario() {
		setIdentifier();
	}

	public void setIdentifier() {
		this.identifier = identifierGenerator;
		identifierGenerator++;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getScenarioName() {
		return this.scenarioName;
	}

	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}

	public long getStartTime() {
		return this.startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public Integer getRunTest() {
		return this.runTest;
	}

	public void setRunTest(Integer runTest) {
		this.runTest = runTest;
	}

	public Integer getFailTests() {
		return this.failTests;
	}

	public void setFailTests(Integer failTests) {
		this.failTests = failTests;
	}

	public Integer getSuccessTests() {
		return this.successTests;
	}

	public void setSuccessTests(Integer successTests) {
		this.successTests = successTests;
	}

	public Integer getWarningTests() {
		return this.warningTests;
	}

	public void setWarningTests(Integer warningTests) {
		this.warningTests = warningTests;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHtmlDir() {
		return this.htmlDir;
	}

	public void setHtmlDir(String htmlDir) {
		this.htmlDir = htmlDir;
	}

	public Short getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Short isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Collection<ReportProperty> getProperties() {
		return this.properties;
	}

	public void setProperties(Collection<ReportProperty> properties) {
		this.properties = properties;
		if (properties != null) {
			StringBuilder sb = new StringBuilder();
			for (ReportProperty reportProperty : properties) {
				if (reportProperty != null) {
					sb.append(reportProperty.getPropertyKey()).append("=").append(reportProperty.getPropertyValue())
							.append(",");
				}
			}
			propertiesStr = sb.toString();
		}
	}

	@Override
	public int hashCode() {
		return identifier;
	}

	public int getIdentifier() {
		return this.identifier;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Scenario scenario = (Scenario) o;

		return !(id != null ? !id.equals(scenario.id) : scenario.id != null);

	}

	@Override
	public int compareTo(Scenario o) {
		if (this.identifier == o.getIdentifier()) {
			return 0;
		} else if (this.identifier > o.getIdentifier()) {
			return 1;
		} else {
			return -1;
		}
	}

	public static int getIdentifierGenerator() {
		return identifierGenerator;
	}

	public static void setIdentifierGenerator(int identifierGenerator) {
		Scenario.identifierGenerator = identifierGenerator;
	}

	public static long getSerialVersionUUID() {
		return serialVersionUID;
	}

	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}

	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "Scenario [identifier=" + identifier + ", id=" + id + ", scenarioName=" + scenarioName + ", startTime="
				+ startTime + ", duration=" + duration + ", runTest=" + runTest + ", failTests=" + failTests
				+ ", successTests=" + successTests + ", warningTests=" + warningTests + ", description=" + description
				+ ", htmlDir=" + htmlDir + ", isDeleted=" + isDeleted + ", lastUpdate=" + lastUpdate
				+ ", scenarioProperties=" + properties;
	}

	public String getPropertiesStr() {
		return propertiesStr;
	}

	public void setPropertiesStr(String propertiesStr) {
		this.propertiesStr = propertiesStr;
	}

	public Collection<Test> getTests() {
		return tests;
	}

	public void setTests(Collection<Test> tests) {
		this.tests = tests;
	}

}
