package il.co.topq.systems.report.dao.infra;

import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.obj.PropertyValuesWrapper;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface PropertyDao<T> extends GenericDao<T> {

	Set<String> getPropertyValuesByKey(String key);

	List<String> getUniquePropertyKeyList();

	T getProperty(String key, String value);

	boolean isPersisted(String key, String value);

	String getPropertyKey(final Integer propertyId);

	void deleteOrphanProperties();

	boolean areValidProperties(Collection<ReportProperty> properties);

	Collection<ReportProperty> createProperties(Collection<ReportProperty> properties);

	List<PropertyValuesWrapper> getAllPropertiesValues(Collection<ReportProperty> properties);

}
