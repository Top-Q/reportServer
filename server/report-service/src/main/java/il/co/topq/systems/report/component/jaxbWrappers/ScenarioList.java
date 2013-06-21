package il.co.topq.systems.report.component.jaxbWrappers;

import il.co.topq.systems.report.common.model.Scenario;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
public class ScenarioList {

	@XmlElement(required = true)
	private List<Scenario> scenarios;

	/*
	 * This c'tor is needed for jaxb to marshall
	 */
	public ScenarioList() {
	}

	public ScenarioList(List<Scenario> scenarios) {
		this.scenarios = scenarios;
	}

	public List<Scenario> getScenarios() {
		return scenarios;
	}

	public void setScenarios(List<Scenario> scenarios) {
		this.scenarios = scenarios;
	}
}
