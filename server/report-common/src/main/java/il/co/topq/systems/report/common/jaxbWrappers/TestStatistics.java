package il.co.topq.systems.report.common.jaxbWrappers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TestStatistics {

    @XmlElement
    private Integer failed;

    @XmlElement
    private Integer passed;

    @XmlElement
    private Integer warned;

    @XmlElement
    private Integer total;

    public TestStatistics() {
        this.failed = 0;
        this.passed = 0;
        this.warned = 0;
        this.total = 0;
    }

    public void addFailedTests(Integer failedTests) {
        this.failed += failedTests;
        this.total += failedTests;
    }

    public void addPassedTests(Integer passedTests) {
        this.passed += passedTests;
        this.total += passedTests;
    }

    public void addWarnedTests(Integer warnedTests) {
        this.warned += warnedTests;
        this.total += warnedTests;
    }

    public TestStatistics(Integer failed, Integer passed, Integer warned, Integer total) {
        this.failed = failed;
        this.passed = passed;
        this.warned = warned;
        this.total = total;
    }

    public Integer getFailed() {
        return failed;
    }

    public void setFailed(Integer failed) {
        this.failed = failed;
    }

    public Integer getPassed() {
        return passed;
    }

    public void setPassed(Integer passed) {
        this.passed = passed;
    }

    public Integer getWarned() {
        return warned;
    }

    public void setWarned(Integer warned) {
        this.warned = warned;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
