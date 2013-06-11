package il.co.topq.systems.report.common.model;

import java.util.Collection;
import java.util.HashSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlSeeAlso(TestProperty.class)
@XmlAccessorType(XmlAccessType.FIELD)
public class Test implements java.io.Serializable, Comparable<Test> {

	@XmlTransient
	private static final long serialVersionUID = 1L;
	@XmlTransient
	private static int identifierGenerator = 0;
	@XmlTransient
	private int identifier;
	@XmlAttribute
	private Integer id;
	@XmlTransient
	private Scenario scenario;
	@XmlElement
	private PackageDescription packageDescription;
	@XmlElement
	private TestDescription testDescription;
	@XmlElement
	private String message;
	@XmlElement
	private Short status; // Pass:0, Fail:1, Warning:2
	@XmlElement
	private long startTime;
	@XmlElement
	private long endTime;
	@XmlElement
	private String steps;
	@XmlElement
	private String failCause;
	@XmlElement
	private String documentation;
	@XmlElement
	private String grpahsXml;
	@XmlElement
	private String params;
	@XmlElement
	private Integer count;
	@XmlElement
	private Double duration;
	@XmlElement
	private String scenarioName;

	@XmlElementWrapper(name = "testProperties")
	private Collection<ReportProperty> properties = new HashSet<ReportProperty>(0);

	@XmlTransient
	private String propertiesStr;

	public Test() {
		setIdentifier();
	}

	public Test(Scenario publishedRuns01, PackageDescription packageDescription, TestDescription testDescription) {
		this.scenario = publishedRuns01;
		this.packageDescription = packageDescription;
		this.testDescription = testDescription;
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

	public Scenario getScenario() {
		return this.scenario;
	}

	public void setScenario(Scenario publishedRuns01) {
		this.scenario = publishedRuns01;
	}

	public PackageDescription getPackageDescription() {
		return this.packageDescription;
	}

	public void setPackageDescription(PackageDescription packageDescription) {
		this.packageDescription = packageDescription;
	}

	public TestDescription getTestDescription() {
		return this.testDescription;
	}

	public void setTestDescription(TestDescription testDescription) {
		this.testDescription = testDescription;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public long getStartTime() {
		return this.startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return this.endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public String getSteps() {
		return this.steps;
	}

	public void setSteps(String steps) {
		this.steps = steps;
	}

	public String getFailCause() {
		return this.failCause;
	}

	public void setFailCause(String failCause) {
		this.failCause = failCause;
	}

	public String getDocumentation() {
		return this.documentation;
	}

	public void setDocumentation(String documentation) {

		if (this.testDescription != null) {
			this.testDescription.setTestDescription(documentation);
		} else {
			this.testDescription = new TestDescription();
			this.testDescription.setTestDescription(documentation);
		}

		this.documentation = documentation;
	}

	public String getGrpahsXml() {
		return this.grpahsXml;
	}

	public void setGrpahsXml(String grpahsXml) {
		this.grpahsXml = grpahsXml;
	}

	public String getParams() {
		return this.params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Collection<ReportProperty> getProperties() {
		return this.properties;
	}

	public void setProperties(Collection<ReportProperty> testProperties) {
		this.properties = testProperties;
		if (testProperties != null) {
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
	public boolean equals(Object object) {
		if (!(object instanceof Test))
			return false;
		Test test = (Test) object;
		return (id == null) ? test.getId() == null : id.equals(test.getId());
	}

	@Override
	public int hashCode() {
		return identifier;
	}

	public int getIdentifier() {
		return identifier;
	}

	@Override
	public int compareTo(Test o) {

		if (this.identifier == o.getIdentifier()) {
			return 0;
		} else if (this.identifier > o.getIdentifier()) {
			return 1;
		} else {
			return -1;
		}
	}

	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}

	public String getPropertiesStr() {
		return propertiesStr;
	}

	public void setPropertiesStr(String propertiesStr) {
		this.propertiesStr = propertiesStr;
	}

	public String getScenarioName() {
		return scenarioName;
	}

	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}
}
