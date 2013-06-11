package il.co.topq.systems.report.common.model;


import javax.xml.bind.annotation.*;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement(name = "testDescription")
@XmlAccessorType(XmlAccessType.FIELD)
public class TestDescription implements java.io.Serializable {

    @XmlTransient
    private static final long serialVersionUID = 1L;
    @XmlTransient
    private Integer testId;
    @XmlElement
    private String testName;
    @XmlElement
    private String testDescription;
    @XmlTransient
    private Set<Test> publishedTest01s = new HashSet<Test>(0);

    public TestDescription() {
    }

    public TestDescription(String testName, String testDescription, Set<Test> publishedTest01s) {
        this.testName = testName;
        this.testDescription = testDescription;
        this.publishedTest01s = publishedTest01s;
    }

    public Integer getTestId() {
        return this.testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return this.testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestDescription() {
        return this.testDescription;
    }

    public void setTestDescription(String testDescription) {
        this.testDescription = testDescription;
    }

    public Set<Test> getPublishedTest01s() {
        return this.publishedTest01s;
    }

    public void setPublishedTest01s(Set<Test> publishedTest01s) {
        this.publishedTest01s = publishedTest01s;
    }

}
