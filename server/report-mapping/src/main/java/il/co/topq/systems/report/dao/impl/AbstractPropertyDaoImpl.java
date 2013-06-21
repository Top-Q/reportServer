package il.co.topq.systems.report.dao.impl;

import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.obj.PropertyValuesWrapper;
import il.co.topq.systems.report.dao.infra.PropertyDao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("propertyDao")
public abstract class AbstractPropertyDaoImpl<T> extends GenericDaoImpl<T> implements PropertyDao<T> {

	public Set<String> getPropertyValuesByKey(String key) {

		Set<String> propertiesValuesSet = new HashSet<String>();
		Criteria criteria = getSession().createCriteria(type);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		Criterion keyCriteria = Restrictions.like("propertyKey", key, MatchMode.EXACT);
		criteria.add(keyCriteria);
		@SuppressWarnings(value = "unchecked")
		List<ReportProperty> propertiesList = criteria.list();
		for (ReportProperty property : propertiesList) {
			if (property.getPropertyValue() != null) {
				if (!property.getPropertyValue().isEmpty()) {
					propertiesValuesSet.add(property.getPropertyValue());
				}
			}
		}
		return propertiesValuesSet;
	}

	public List<PropertyValuesWrapper> getAllPropertiesValues(Collection<ReportProperty> properties) {
		List<PropertyValuesWrapper> propertyValuesWrapperList = new ArrayList<PropertyValuesWrapper>();
		for (ReportProperty property : properties) {
			String propertyKey = property.getPropertyKey();
			Set<String> propertyValueList = getPropertyValuesByKey(propertyKey);
			propertyValuesWrapperList.add(new PropertyValuesWrapper(propertyKey, propertyValueList));
		}
		return propertyValuesWrapperList;
	}

	@SuppressWarnings(value = "unchecked")
	public List<String> getUniquePropertyKeyList() {
		StringBuilder sb = new StringBuilder();
		sb.append("select DISTINCT(propsList.propertyKey) from ").append(type.getSimpleName()).append(" as propsList");
		Query query = getSession().createQuery(sb.toString());
		return query.list();
	}

	@SuppressWarnings(value = "unchecked")
	public T getProperty(String key, String value) {
		Criteria criteria = getSession().createCriteria(type);
		Criterion keyCriteria = Restrictions.like("propertyKey", key, MatchMode.EXACT);
		Criterion valueCriteria = Restrictions.like("propertyValue", value, MatchMode.EXACT);
		LogicalExpression andExp = Restrictions.and(keyCriteria, valueCriteria);
		criteria.add(andExp);
		return (T) criteria.uniqueResult();
	}

	public boolean isPersisted(String key, String value) {
		T property = getProperty(key, value);
		return (null != property);
	}

	public String getPropertyKey(final Integer propertyId) {
		String queryStr = "from " + type.getSimpleName() + " as propList where propList.id=" + propertyId;
		Query query = getSession().createQuery(queryStr);
		return (String) query.uniqueResult();
	}

	@Override
	public boolean areValidProperties(Collection<ReportProperty> properties) {
		if (properties != null) {
			for (ReportProperty t : properties) {
				if (t != null) {
					if (t.getPropertyKey().isEmpty() || t.getPropertyValue().isEmpty()) {
						return false;
					}
				} else {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public T get(final Object id) {
		return (T) hibernateTemplate.get(type, Integer.valueOf(id.toString()));
	}
}