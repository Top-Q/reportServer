package il.co.topq.systems.report.component.jaxbWrappers;

import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.model.ScenarioProperty;
import il.co.topq.systems.report.common.model.TestProperty;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({ TestProperty.class, ScenarioProperty.class })
public class PropertyList {

	@XmlAnyElement
	private List<ReportProperty> properties;

	/*
	 * required for jaxb usage.
	 */
	public PropertyList() {
	}

	public PropertyList(List<ReportProperty> properties) {
		this.properties = properties;
	}

	public List<ReportProperty> getProperties() {
		return properties;
	}

	public void setProperties(List<ReportProperty> properties) {
		this.properties = properties;
	}

}
