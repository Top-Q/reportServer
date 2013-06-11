package il.co.topq.systems.report.component.jaxbWrappers;

import il.co.topq.systems.report.common.model.Test;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
public class TestList {

	@XmlElement(required = true)
	private List<Test> tests;

	/*
	 * This c'tor is needed for jaxb to marshall
	 */
	public TestList() {
	}

	public TestList(List<Test> tests) {
		this.tests = tests;
	}

	public List<Test> getTests() {
		return tests;
	}

	public void setTests(List<Test> tests) {
		this.tests = tests;
	}

}
