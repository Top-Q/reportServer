package il.co.topq.systems.report.common.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "customReport")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(TestProperty.class)
public class TestCustomReport extends AbstractCustomReport {

	@XmlTransient
	private static final long serialVersionUID = 1741121976609545008L;
}
