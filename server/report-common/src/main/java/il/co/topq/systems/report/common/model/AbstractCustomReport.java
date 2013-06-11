package il.co.topq.systems.report.common.model;

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
@XmlSeeAlso({ ScenarioProperty.class, TestProperty.class })
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractCustomReport implements java.io.Serializable, CustomReport<ReportProperty> {

	@XmlTransient
	private static final long serialVersionUID = 5712714692227582230L;

	@XmlAttribute
	private Integer id;
	@XmlElement
	private String name;
	@XmlElement
	private String description;
	@XmlElement
	private Long dateOfCreation;
	@XmlElement
	private String filterList;
	@XmlElement(name = "properties")
	private Collection<ReportProperty> properties;

	@Override
	public Collection<ReportProperty> getProperties() {
		return this.properties;
	}

	@Override
	public void setProperties(Collection<ReportProperty> properties) {
		this.properties = properties;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getDateOfCreation() {
		return this.dateOfCreation;
	}

	public void setDateOfCreation(Long dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}

	public String getFilterList() {
		return this.filterList;
	}

	public void setFilterList(String filterList) {
		this.filterList = filterList;
	}

	public void createDate() {
		this.dateOfCreation = new Date().getTime();
	}

	// public abstract Collection<ReportProperty> getProperties();
	//
	// public abstract void setProperties(Collection<ReportProperty> properties);
	//
	@Override
	public String toString() {
		return "Customer Report: " + name + " " + getProperties().toString();
	}
}
