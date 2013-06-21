package il.co.topq.systems.report.common.obj;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ComparedTestDetails {

    @XmlElement
    Integer scenarioID = null;

    @XmlElement
    Short status = null;

    @XmlElement
    Integer testID = null;


    public ComparedTestDetails() {

    }

    public ComparedTestDetails(Integer scenarioID, Integer testID, Short status) {
        this.scenarioID = scenarioID;
        this.testID = testID;
        this.status = status;
    }
}
