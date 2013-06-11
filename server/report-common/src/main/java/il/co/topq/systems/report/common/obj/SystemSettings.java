package il.co.topq.systems.report.common.obj;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SystemSettings implements Serializable {

	/**
	 * Different organizations may be interested in saving a default test
	 * grouping behaviour. That means the way the organization perceive the same
	 * test can be in one of the following ways: 0 - None grouping 1 - By Test
	 * name 2 - By Test Name and its Scenario (context) 3 - By Test Name and its
	 * Scenario (context) and its parameters
	 */
	private Integer defaultTestGrouping = 1;

	public Integer getDefaultTestGrouping() {
		return defaultTestGrouping;
	}

	public void setDefaultTestGrouping(Integer defaultTestGrouping) {
		this.defaultTestGrouping = defaultTestGrouping;
	}

}
