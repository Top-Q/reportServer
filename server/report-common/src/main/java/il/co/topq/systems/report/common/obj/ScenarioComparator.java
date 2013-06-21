package il.co.topq.systems.report.common.obj;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ScenarioComparator {

    /**
     * This will save the scenario details in the form of:<br>
     * scenarioName;scenarioID;<br>
     * There is no need for all the scenario details to be passed.
     */
    @XmlElement
    private List<String> comparedScenarios;

    @XmlElement
    private List<TestComparator> comparedTests;

    public ScenarioComparator() {
        this.comparedScenarios = new ArrayList<String>();
        this.comparedTests = new ArrayList<TestComparator>();
    }

    public void addScenario(String scenarioName, Integer scenarioID) {
        comparedScenarios.add(scenarioName + ";" + scenarioID.toString());
    }

    public List<TestComparator> getComparedTests() {
        return this.comparedTests;
    }

    public void addTestComparator(TestComparator testComparator) {
        this.comparedTests.add(testComparator);
    }

}
