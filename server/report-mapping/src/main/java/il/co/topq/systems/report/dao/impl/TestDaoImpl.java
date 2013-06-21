package il.co.topq.systems.report.dao.impl;

import static il.co.topq.systems.report.utils.QueryInterface.FROM_JOIN_TEST_WITH_PACKAGE_AND_NAME;
import static il.co.topq.systems.report.utils.QueryInterface.TEST_SUMMARY_FIELDS;
import il.co.topq.systems.report.common.model.Test;
import il.co.topq.systems.report.common.model.TestSummary;
import il.co.topq.systems.report.common.obj.Chunk;
import il.co.topq.systems.report.common.obj.SortingColumn;
import il.co.topq.systems.report.common.obj.TestQuery;
import il.co.topq.systems.report.common.obj.TestSummaryQuery;
import il.co.topq.systems.report.common.obj.TimeRange;
import il.co.topq.systems.report.dao.infra.TestDao;
import il.co.topq.systems.report.utils.QueryUtil;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

@Repository("testDao")
public class TestDaoImpl extends GenericDaoImpl<Test> implements TestDao {

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
		Criteria criteria = getSession().createCriteria(Test.class);
		Criterion key = Restrictions.eq("scenario.id", scenarioID);
		criteria.add(key);
		if (testQuery != null) {
			Chunk chunk = testQuery.getChunk();
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
		}
		return criteria.list();
	}

	/**
	 * Get a chunk of tests from DB arranged by a time range. The tests in the chunk are sorted in descending order
	 * 
	 * @param chunk
	 *            The parameters of the chunk
	 * @param timeRange
	 *            The time range parameters
	 * @return return a list of tests in descending order by startTime. An empty list is return in case no test match
	 *         the criteria. In case the chunk is in Illegal state (empty,null) return an empty list and write a warning
	 *         to Logger In case timeRange is in Illegal state (empty,null, lowBound>upBound) return an empty list and
	 *         write a warning to the Logger
	 */
	@SuppressWarnings(value = "unchecked")
	@Override
	public List<Test> get(final Chunk chunk, final TimeRange timeRange) {
		if (timeRange == null) {
			return get(chunk);
		}
		String queryStr = QueryUtil.createQueryStr(timeRange, "Test as test");
		queryStr = QueryUtil.createQueryStrWithDescending(queryStr);
		Query query = getSession().createQuery(queryStr);
		query = QueryUtil.setQueryResultSize(query, chunk);
		return query.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TestSummary> getTestSummary(TestQuery testQuery) {
		String queryStr = QueryUtil.formatTestSummarySelectQuery(testQuery, TEST_SUMMARY_FIELDS,
				FROM_JOIN_TEST_WITH_PACKAGE_AND_NAME);

		// Mapping result to TestSummary
		SQLQuery sqlQuery = getSession().createSQLQuery(queryStr);
		sqlQuery.addScalar("testName", Hibernate.STRING);
		sqlQuery.addScalar("packageName", Hibernate.STRING);
		sqlQuery.addScalar("scenarioName", Hibernate.STRING);
		sqlQuery.addScalar("total", Hibernate.BIG_INTEGER);
		sqlQuery.addScalar("pass", Hibernate.BIG_DECIMAL);
		sqlQuery.addScalar("fail", Hibernate.BIG_DECIMAL);
		sqlQuery.addScalar("warning", Hibernate.BIG_DECIMAL);
		sqlQuery.addScalar("params", Hibernate.STRING);
		Query query = sqlQuery.setResultTransformer(Transformers.aliasToBean(TestSummary.class));
		QueryUtil.setQueryResultSize(query, testQuery.getChunk());
		return query.list();
	}

	/**
	 * a list of properties to filter on. in case of no properties to filter upon, will return all tests in DB according
	 * to chunk and timeRange case given as input and not Null;
	 * 
	 * @param testQuery
	 *            -
	 * @return will return a list of scenarios answering the filters; a List and not a Set in order to keep the order of
	 *         the tests returned as they are ordered by descending startTime;
	 */
	@SuppressWarnings(value = "unchecked")
	@Override
	public List<Test> getTestsByFiltersWithSortingCol(TestQuery testQuery) {
		String queryStr = QueryUtil.formatTestSelectQuery(testQuery, "*", "published_test_01");
		queryStr = QueryUtil.setQueryResultSize(queryStr, testQuery.getChunk());
		Query query = getSession().createSQLQuery(queryStr).addEntity(Test.class);

		List<Test> tests = query.list();
		for (Test test : tests) {
			test.setScenarioName(test.getScenario().getScenarioName());
		}
		System.out.println(queryStr);
		return tests;
	}

	/**
	 * This method will get the size of a query according to the Query object received as parameter.
	 * 
	 * @param testQuery
	 *            -
	 * @return The number of tests
	 */
	public Integer getSizeOfTestQuery(TestQuery testQuery) {
		String queryStr = QueryUtil.formatTestSelectQuery(testQuery, "count(*)", "published_test_01");
		SQLQuery sqlQuery = getSession().createSQLQuery(queryStr);
		return DataAccessUtils.intResult(sqlQuery.list());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Test> getTestsByTestSummary(TestSummaryQuery testSummaryQuery) {

		TestQuery testQuery = testSummaryQuery.getTestQuery();
		String queryStr = QueryUtil.selectFromTestTable(testSummaryQuery, "*");
		Query query = getSession().createSQLQuery(queryStr).addEntity(Test.class);
		if (testQuery.getChunk() != null) {
			query = QueryUtil.setQueryResultSize(query, testQuery.getChunk());
		}
		return query.list();
	}

}
