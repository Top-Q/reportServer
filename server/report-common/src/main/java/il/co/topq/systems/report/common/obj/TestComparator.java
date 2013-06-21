package il.co.topq.systems.report.common.obj;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TestComparator {

    @XmlElement
    private Integer testIndex;// represent the testIndex in scenario tests flow.

    @XmlElement
    private String testName;

    @XmlElement
    List<ComparedTestDetails> tests;

    public TestComparator() {
        this.testName = "";
        this.testIndex = 0;
        tests = new ArrayList<ComparedTestDetails>();
    }

    public TestComparator(String testName, Integer testIndex) {
        this.testName = testName;
        this.testIndex = testIndex;
        tests = new ArrayList<ComparedTestDetails>();
    }

    public void addComparedTestDetails(ComparedTestDetails comparedTestDetails) {
        this.tests.add(comparedTestDetails);
    }

    public String getTestName() {
        return testName;
    }

    @Override
    public int hashCode() {
        String res = this.testName + "." + this.testIndex.toString();
        return res.hashCode();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        TestComparator that = (TestComparator) o;

        return ((this.testName.equals(that.getTestName())) && (this.testIndex.equals(that.getTestIndex())));
    }

    public Integer getTestIndex() {
        return testIndex;
    }

}
