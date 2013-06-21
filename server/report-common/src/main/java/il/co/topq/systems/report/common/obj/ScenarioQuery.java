package il.co.topq.systems.report.common.obj;

import il.co.topq.systems.report.common.model.ReportProperty;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ScenarioQuery extends AbstractQuery {

	@XmlElementWrapper(name = "scenarioProperties")
	private Collection<ReportProperty> properties;

	@XmlElement
	private Boolean timeAsc = false;

	/*
	 * required for jaxb usage
	 */
	public ScenarioQuery() {
	}

	public ScenarioQuery(TimeRange timeRange, Chunk chunk) {
		this.timeRange = timeRange;
		this.chunk = chunk;
	}

	// public Collection<ReportProperty> getProperties() {
	// return properties;
	// }
	//
	// public void setProperties(Collection<ReportProperty> properties) {
	// this.properties = properties;
	// }

	public Boolean getTimeAsc() {
		return timeAsc;
	}

	public void setTimeAsc(Boolean timeAsc) {
		this.timeAsc = timeAsc;
	}

	@Override
	public String toString() {
		return "ScenarioQuery [chunk=" + chunk + ", timeRange=" + timeRange + ", scenarioProperties=" + properties
				+ "]";

	}

	public Collection<ReportProperty> getProperties() {
		return properties;
	}

	public void setProperties(Collection<ReportProperty> properties) {
		this.properties = properties;
	}

}
