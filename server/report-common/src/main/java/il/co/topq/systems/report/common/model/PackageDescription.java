package il.co.topq.systems.report.common.model;


import javax.xml.bind.annotation.*;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement(name = "packageDescription")
@XmlAccessorType(XmlAccessType.FIELD)
public class PackageDescription implements java.io.Serializable {


    private static final long serialVersionUID = 1L;
    @XmlTransient
    private Integer id;
    @XmlElement
    private String packageName;
    @XmlElement
    private String packageDescription;
    @XmlTransient
    private Set<Test> tests = new HashSet<Test>(0);

    public PackageDescription() {
    }

    public PackageDescription(String packageName, String packageDescription, Set<Test> publishedTest01s) {
        this.packageName = packageName;
        this.packageDescription = packageDescription;
        this.tests = publishedTest01s;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageDescription() {
        return this.packageDescription;
    }

    public void setPackageDescription(String packageDescription) {
        this.packageDescription = packageDescription;
    }

    public Set<Test> getTests() {
        return this.tests;
    }

    public void setTests(Set<Test> publishedTest01s) {
        this.tests = publishedTest01s;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
