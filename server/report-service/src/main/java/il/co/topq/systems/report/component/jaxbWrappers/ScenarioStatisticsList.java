package il.co.topq.systems.report.component.jaxbWrappers;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ScenarioStatisticsList {

	@XmlElement(required = true)
	private List<ScenarioStatistics> scenarioStatistics;

	public ScenarioStatisticsList() {
	}

	public ScenarioStatisticsList(List<ScenarioStatistics> scenarioStatistics) {
		this.scenarioStatistics = scenarioStatistics;
	}

	public List<ScenarioStatistics> getScenarioStatistics() {
		return scenarioStatistics;
	}

	public void setScenarioStatistics(
			List<ScenarioStatistics> scenarioStatistics) {
		this.scenarioStatistics = scenarioStatistics;
	}
}
