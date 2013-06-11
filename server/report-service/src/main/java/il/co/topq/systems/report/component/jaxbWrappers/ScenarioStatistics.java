package il.co.topq.systems.report.component.jaxbWrappers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ScenarioStatistics {

    @XmlElement
    String scenarioType;// will hold the scenario name

    @XmlElement
    TestStatistics testStatistics;

    public ScenarioStatistics() {
        this.scenarioType = "";
        this.testStatistics = new TestStatistics();
    }

    public ScenarioStatistics(String scenarioType, TestStatistics testStatistics) {
        this.scenarioType = scenarioType;
        this.testStatistics = testStatistics;
    }

    public void updateTestStatistics(TestStatistics testStatistics) {
        this.testStatistics.addFailedTests(testStatistics.getFailed());
        this.testStatistics.addPassedTests(testStatistics.getPassed());
        this.testStatistics.addWarnedTests(testStatistics.getWarned());
    }

    public String getScenarioType() {
        return scenarioType;
    }

    public void setScenarioType(String scenarioType) {
        this.scenarioType = scenarioType;
    }

    public TestStatistics getTestStatistics() {
        return testStatistics;
    }

    public void setTestStatistics(TestStatistics testStatistics) {
        this.testStatistics = testStatistics;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ScenarioStatistics that = (ScenarioStatistics) o;

        return (this.scenarioType.equalsIgnoreCase(that.getScenarioType()));
    }

    @Override
    public int hashCode() {

        String key = scenarioType.toLowerCase();

        return (key).hashCode();
    }

}
