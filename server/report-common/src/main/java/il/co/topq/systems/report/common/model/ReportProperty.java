package il.co.topq.systems.report.common.model;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.sun.xml.bind.AnyTypeAdapter;

@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface ReportProperty {

	String getPropertyKey();

	void setPropertyKey(String propertyKey);

	String getPropertyValue();

	void setPropertyValue(String propertyValue);

	Integer getIndex1();

	long getNumberOfReferences();

	long getNumberOfCustomReport();

}
