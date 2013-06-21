package il.co.topq.systems.report.common.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "customReport")
@XmlAccessorType(XmlAccessType.FIELD)
public class ScenarioCustomReport extends AbstractCustomReport {

	@XmlTransient
	private static final long serialVersionUID = 5712714692227582230L;
}