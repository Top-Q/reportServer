package il.co.topq.systems.report.service.infra;

import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.obj.PropertyValuesWrapper;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author Eran Golan & Tomer Gafner
 */
public interface PropertyService<T> extends BaseService<T> {
	Set<String> getPropertyValuesByKey(String key);

	List<String> getUniquePropertyKeyList();

	T getProperty(String key, String value);

	boolean isPersisted(String key, String value);

	String getPropertyKey(final Integer propertyId);

	/**
	 * This method will check the DB for orphan properties and deletes them.<br>
	 * Orphan Properties: a property which has no test / test custom report which refers to it.
	 */
	void deleteOrphanProperties();

	List<PropertyValuesWrapper> getAllPropertiesValues(Collection<ReportProperty> properties);

}
