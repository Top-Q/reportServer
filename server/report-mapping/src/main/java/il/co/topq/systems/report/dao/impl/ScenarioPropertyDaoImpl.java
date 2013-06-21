package il.co.topq.systems.report.dao.impl;

import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.model.ScenarioProperty;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository("scenarioPropertyDao")
public class ScenarioPropertyDaoImpl extends AbstractPropertyDaoImpl<ScenarioProperty> {

	/**
	 * This method will search the DB for orphan test properties and deletes them.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void deleteOrphanProperties() {
		Session session = getSession();
		List<ScenarioProperty> testProperties = getAll();
		for (ScenarioProperty testProperty : testProperties) {
			testProperty = (ScenarioProperty) session.get(type, testProperty.getIndex1());
			if (testProperty.getNumberOfReferences() == 0 && testProperty.getNumberOfCustomReport() == 0) {
				delete(testProperty.getIndex1());
			}
		}
	}

	/**
	 * If exist will get a reference, else will create new.
	 */
	public Collection<ReportProperty> createProperties(Collection<ReportProperty> properties) {
		Collection<ReportProperty> resultProperties = new HashSet<ReportProperty>();
		if (properties != null) {
			for (ReportProperty t : properties) {
				if (!isPersisted(t.getPropertyKey(), t.getPropertyValue())) {
					resultProperties.add(create((ScenarioProperty) t));
				} else {
					resultProperties.add(getProperty(t.getPropertyKey(), t.getPropertyValue()));
				}
			}
		}
		return resultProperties;
	}

	public ReportProperty getProperty(long id) {
		 return (ReportProperty) getSession().createSQLQuery(
		 new StringBuilder().append("select * from scenario_properties ").append(" where index1=")
		 .append(String.valueOf(id)).toString()).uniqueResult();
//		return null;
	}
}
