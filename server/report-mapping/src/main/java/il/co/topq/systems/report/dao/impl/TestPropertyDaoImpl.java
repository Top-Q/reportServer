package il.co.topq.systems.report.dao.impl;

import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.model.TestProperty;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository("testPropertyDao")
public class TestPropertyDaoImpl extends AbstractPropertyDaoImpl<TestProperty> {

	/**
	 * This method will search the DB for orphan test properties and deletes them.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void deleteOrphanProperties() {
		Session session = getSession();
		List<TestProperty> testProperties = getAll();
		for (TestProperty testProperty : testProperties) {
			testProperty = (TestProperty) session.get(type, testProperty.getIndex1());
			if (testProperty.getNumberOfReferences() == 0 && testProperty.getNumberOfCustomReport() == 0) {
				delete(testProperty.getIndex1());
			}
		}
	}

	public Collection<ReportProperty> createProperties(Collection<ReportProperty> properties) {
			Collection<ReportProperty> resultProperties = new HashSet<ReportProperty>();
		if (properties != null) {
			for (ReportProperty t : properties) {
				if (!isPersisted(t.getPropertyKey(), t.getPropertyValue())) {
					resultProperties.add(create((TestProperty) t));
				} else {
					resultProperties.add(getProperty(t.getPropertyKey(), t.getPropertyValue()));
				}
			}
		}
		return resultProperties;
	}

}
