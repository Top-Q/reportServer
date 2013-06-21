package il.co.topq.systems.report.component.export;

import il.co.topq.systems.report.common.model.ReportProperty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class ScenarioPropertiesSource implements JRDataSource {

	private List<ReportProperty> properties;
	int index = -1;

	ScenarioPropertiesSource(Collection<ReportProperty> properties) {
		this.properties = new ArrayList<ReportProperty>(properties);
	}

	public Object getFieldValue(JRField jrField) throws JRException {

		String fieldName = jrField.getName();
		if (fieldName.equalsIgnoreCase("property")) {
			ReportProperty scenarioProperty = properties.get(index);
			return scenarioProperty.getPropertyKey();
		} else if (fieldName.equalsIgnoreCase("value")) {
			ReportProperty scenarioProperty = properties.get(index);
			String propertyValue = scenarioProperty.getPropertyValue();
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
