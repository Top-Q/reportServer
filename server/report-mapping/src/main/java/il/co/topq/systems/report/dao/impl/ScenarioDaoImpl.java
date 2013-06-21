package il.co.topq.systems.report.dao.impl;

import static il.co.topq.systems.report.utils.QueryInterface.*;
import il.co.topq.systems.report.common.exception.SoftwareException;
import il.co.topq.systems.report.common.model.Scenario;
import il.co.topq.systems.report.common.model.Test;
import il.co.topq.systems.report.common.obj.Chunk;
import il.co.topq.systems.report.common.obj.ComparedTestDetails;
import il.co.topq.systems.report.common.obj.ScenarioComparator;
import il.co.topq.systems.report.common.obj.ScenarioQuery;
import il.co.topq.systems.report.common.obj.SortingColumn;
import il.co.topq.systems.report.common.obj.TestComparator;
import il.co.topq.systems.report.common.obj.TestQuery;
import il.co.topq.systems.report.dao.infra.ScenarioDao;
import il.co.topq.systems.report.utils.QueryInterface;
import il.co.topq.systems.report.utils.QueryUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

@Repository("scenarioDao")
public class ScenarioDaoImpl extends GenericDaoImpl<Scenario> implements ScenarioDao {

	@Override
	public int getScenarioNumberOfTests(Integer scenarioId) {

		String queryStr = "select count(*) from " + TEST_TABLE_NAME + " where runIndex = " + scenarioId;
		SQLQuery sqlQuery = getSession().createSQLQuery(queryStr);
		return DataAccessUtils.intResult(sqlQuery.list());
	}

	private List<Scenario> getScenarios(Integer[] scenariosIDArray) {
		List<Scenario> comparedScenarioList = new ArrayList<Scenario>();
		for (Integer scenarioID : scenariosIDArray) {
			comparedScenarioList.add(get(scenarioID));
		}
		return comparedScenarioList;
	}

	/**
	 * This method will get all test by specific scenario id and return it according to given chunk;
	 * 
	 * @param scenarioID
	 *            -
	 * @param chunk
	 *            -
	 * @return List<Test>
	 */
	@SuppressWarnings(value = "unchecked")
	@Override
	public List<Test> getTestsByScenarioID(Integer scenarioID, TestQuery testQuery) {
		Chunk chunk = testQuery.getChunk();
		Criteria criteria = getSession().createCriteria(Test.class);
		Criterion key = Restrictions.eq("scenario.id", scenarioID);
		criteria.add(key);
		if (chunk != null) {
			criteria.setFirstResult(chunk.getStartIndex());
			criteria.setMaxResults(chunk.getLength());
		}
		SortingColumn sortingColumn = testQuery.getSortingColumn();
		if (sortingColumn != null) {
			if (testQuery.getSortingColumn().getAsc()) {
				criteria.addOrder(Order.asc(testQuery.getSortingColumn().getName()));
			} else {
				criteria.addOrder(Order.desc(testQuery.getSortingColumn().getName()));
			}
		}
		return criteria.list();
	}

	/**
	 * @return will return a list of scenarios answering the filters; a List and not a Set in order to keep the order of
	 *         the tests returned as they are ordered by descending startTime;
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Scenario> getScenariosByFiltersWithSortingCol(ScenarioQuery scenarioQuery) {
		List<Scenario> scenariosList;
		String queryStr = QueryUtil.getQueryStr(scenarioQuery, "*");
		queryStr = QueryUtil.setQueryResultSize(queryStr, scenarioQuery.getChunk());

		Query query = getSession().createSQLQuery(queryStr).addEntity(Scenario.class);
		scenariosList = query.list();
		System.out.println(queryStr);
		return scenariosList;
	}

	public int getSizeOfScenarioQuery(ScenarioQuery scenarioQuery) {
		String queryStr = QueryUtil.getQueryStr(scenarioQuery, "count(*)");
		SQLQuery sqlQuery = getSession().createSQLQuery(queryStr);
		return DataAccessUtils.intResult(sqlQuery.list());
	}

	/**
	 * This method compares N scenarios and return Scenario Comparator object. In case scenarios are not equal an
	 * exception will be thrown.
	 */
	@Override
	public ScenarioComparator compareScenarios(Integer[] scenariosIDArray) {

		List<Scenario> comparedScenarioList = getScenarios(scenariosIDArray);
		ScenarioComparator scenarioComparator = new ScenarioComparator();
		List<TestComparator> comparedTests = scenarioComparator.getComparedTests();

		if (!comparedScenarioList.isEmpty()) {

			for (Scenario scenario : comparedScenarioList) {

				scenarioComparator.addScenario(scenario.getScenarioName(), scenario.getId());

				List<Test> tests = getTestsByScenarioID(scenario.getId(), new TestQuery());

				for (int i = 0; i < tests.size(); i++) {
					Test test = tests.get(i);
					ComparedTestDetails comparedTestDetails = new ComparedTestDetails(scenario.getId(), test.getId(),
							test.getStatus());
					TestComparator testComparator = new TestComparator(test.getTestDescription().getTestName(), i + 1);
					if (!(comparedTests.contains(testComparator))) {
						scenarioComparator.addTestComparator(testComparator);
					}
					Integer testComparatorIndex = comparedTests.indexOf(testComparator);
					comparedTests.get(testComparatorIndex).addComparedTestDetails(comparedTestDetails);
				}
			}
		} else {
			StringBuilder sb = new StringBuilder("no scenarios can be found");
			throw new SoftwareException(sb);
		}
		Collections.sort(comparedTests, new Comparator<TestComparator>() {
			@Override
			public int compare(TestComparator o1, TestComparator o2) {
				return o1.getTestIndex().compareTo(o2.getTestIndex());
			}
		});
		return scenarioComparator;
	}

	public void updateTestScenarioStatus(Scenario scenario, short oldStatus, short newStatus) {
		changeScenarioTestStatusCounters(scenario, oldStatus, newStatus);
		update(scenario);
	}

	private void changeScenarioTestStatusCounters(Scenario scenario, short oldStatus, short newStatus) {

		if (oldStatus == newStatus) {
			return;
		}

		switch (oldStatus) {
		case 0:
			scenario.setSuccessTests(scenario.getSuccessTests() - 1);
			break;
		case 1:
			scenario.setFailTests(scenario.getFailTests() - 1);
			break;
		case 2:
			scenario.setWarningTests(scenario.getWarningTests() - 1);
			break;
		default:
			throw new SoftwareException("Status should be only 0 or 1 or 2. Value received is " + oldStatus);
		}

		switch (newStatus) {
		case 0:
			scenario.setSuccessTests(scenario.getSuccessTests() + 1);
			break;
		case 1:
			scenario.setFailTests(scenario.getFailTests() + 1);
			break;
		case 2:
			scenario.setWarningTests(scenario.getWarningTests() + 1);
			break;
		default:
			throw new SoftwareException("Status should be only 0 or 1 or 2. Value received is " + newStatus);
		}
	}

}
