package il.co.topq.systems.report.common.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "TestSummary")
@XmlAccessorType(XmlAccessType.FIELD)
public class TestSummary {

    @XmlElement
    private String testName;

    @XmlElement
    private String packageName;

    @XmlElement
    private BigInteger total;

    @XmlElement
    private BigDecimal pass;

    @XmlElement
    private BigDecimal fail;

    @XmlElement
    private BigDecimal warning;

    @XmlElement
    private String scenarioName;

    @XmlElement
    private String params;


    public String getTestName() {
        return testName;
    }


    public void setTestName(String testName) {
        this.testName = testName;
    }


    public BigInteger getTotal() {
        return total;
    }


    public void setTotal(BigInteger total) {
        this.total = total;
    }


    public BigDecimal getPass() {
        return pass;
    }


    public void setPass(BigDecimal pass) {
        this.pass = pass;
    }


    public BigDecimal getFail() {
        return fail;
    }


    public void setFail(BigDecimal fail) {
        this.fail = fail;
    }


    public BigDecimal getWarning() {
        return warning;
    }


    public void setWarning(BigDecimal warning) {
        this.warning = warning;
    }


    @Override
    public String toString() {
        return "TestSummary [testName=" + testName + ", total=" + total + ", pass=" + pass + ", " + "fail=" + fail
                + ", warning=" + warning + "]";
    }

    public String getScenarioName() {
        return scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }


    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }


    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
    
    
}
