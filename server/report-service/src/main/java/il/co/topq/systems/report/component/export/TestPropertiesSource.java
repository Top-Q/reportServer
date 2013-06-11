package il.co.topq.systems.report.component.export;

import il.co.topq.systems.report.common.model.ReportProperty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class TestPropertiesSource implements JRDataSource {

	List<ReportProperty> properties;
	int index = -1;

	TestPropertiesSource(Collection<ReportProperty> properties) {
		this.properties = new ArrayList<ReportProperty>(properties);
	}

	public Object getFieldValue(JRField jrField) throws JRException {

		String fieldName = jrField.getName();
		if (fieldName.equalsIgnoreCase("property")) {
			ReportProperty testProperty = properties.get(index);
			return testProperty.getPropertyKey();
		} else if (fieldName.equalsIgnoreCase("value")) {
			ReportProperty testProperty = properties.get(index);
			String propertyValue = testProperty.getPropertyValue();
			if (propertyValue.equals("")) {
				propertyValue = "All";
			}
			return propertyValue;
		}
		return null;
	}

	public boolean next() throws JRException {
		if (index < (properties.size() - 1)) {
			index++;
			return true;
		}
		return false;
	}

}
