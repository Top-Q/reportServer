package il.co.topq.systems.report.component.jaxbWrappers;

import il.co.topq.systems.report.common.obj.PropertyValuesWrapper;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
public class PropertyValuesWrapperList {

	@XmlElement(required = true)
	List<PropertyValuesWrapper> propertyValuesWrappers;

	/*
	 * this c'tor is needed for jaxb usage.
	 */
	public PropertyValuesWrapperList() {
	}

	public PropertyValuesWrapperList(
			List<PropertyValuesWrapper> propertyValuesWrappers) {
		this.propertyValuesWrappers = propertyValuesWrappers;
	}

	public List<PropertyValuesWrapper> getPropertyValuesWrappers() {
		return propertyValuesWrappers;
	}

	public void setPropertyValuesWrappers(
			List<PropertyValuesWrapper> propertyValuesWrappers) {
		this.propertyValuesWrappers = propertyValuesWrappers;
	}
}
