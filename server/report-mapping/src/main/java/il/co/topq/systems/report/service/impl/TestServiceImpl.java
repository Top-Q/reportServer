package il.co.topq.systems.report.service.impl;

import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.model.Test;
import il.co.topq.systems.report.common.model.TestSummary;
import il.co.topq.systems.report.common.obj.Chunk;
import il.co.topq.systems.report.common.obj.TestQuery;
import il.co.topq.systems.report.common.obj.TestSummaryQuery;
import il.co.topq.systems.report.common.obj.TimeRange;
import il.co.topq.systems.report.dao.infra.PropertyDao;
import il.co.topq.systems.report.dao.infra.ScenarioDao;
import il.co.topq.systems.report.dao.infra.TestDao;
import il.co.topq.systems.report.service.infra.TestService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("testService")
@Transactional
public class TestServiceImpl implements TestService {

	@Autowired
	private TestDao testDao;

	@Autowired
	private ScenarioDao scenarioDao;

	private PropertyDao<ReportProperty> propertyDao;

	@Override
	public Test create(Test test) throws Exception {

		Collection<ReportProperty> testProperties = test.getProperties();
		if (propertyDao.areValidProperties(testProperties)) {
			test.setProperties(propertyDao.createProperties(testProperties));
		} else {
			throw new Exception("scenario properties are invalid, could not create scenario");
		}
		return testDao.create(test);
	}

	@Override
	public void delete(int id) {
		testDao.delete(id);
	}

	@Override
	public Test update(Test test) throws Exception {
		Test testFromDb = testDao.get(test.getId());
		test.setScenario(testFromDb.getScenario());
		Collection<ReportProperty> testProperties = test.getProperties();
		if (propertyDao.areValidProperties(testProperties)) {
			test.setProperties(propertyDao.createProperties(testProperties));
			scenarioDao.updateTestScenarioStatus(test.getScenario(), testFromDb.getStatus(), test.getStatus());
			return testDao.update(test);
		} else {
			throw new Exception("test properties are invalid, could not update test");
		}
	}

	@Override
	public long countAll(Map<String, Object> params) {
		return testDao.countAll(params);
	}

	@Override
	public Test get(long id) {
		return testDao.get(id);
	}

	/**
	 * This can easily cause a heap space exception
	 */
	@Override
	public List<Test> getAll() {
		return testDao.getAll();
	}

	@Override
	public List<Test> getTests(Chunk chunk, TimeRange timeRange) {
		return testDao.get(chunk, timeRange);
	}

	@Override
	public List<Test> getTestsByFiltersWithSortingCol(TestQuery testQuery) {
		return testDao.getTestsByFiltersWithSortingCol(testQuery);
	}

	@Override
	public Integer getSizeOfTestQuery(TestQuery testQuery) {
		return testDao.getSizeOfTestQuery(testQuery);
	}

	@Override
	public List<Test> getTestsByTestSummary(TestSummaryQuery testSummaryQuery) {
		return testDao.getTestsByTestSummary(testSummaryQuery);
	}

	@Override
	public List<TestSummary> getTestSummary(TestQuery testQuery) {
		return testDao.getTestSummary(testQuery);
	}

	@Override
	public List<Test> getTestsByScenarioId(Integer scenarioID, TestQuery testQuery) {
		return testDao.getTestsByScenarioID(scenarioID, testQuery);
	}

	@Override
	public List<Test> getChunk(Chunk chunk) {
		return testDao.get(chunk);
	}

	public void setPropertyDao(PropertyDao<ReportProperty> propertyDao) {
		this.propertyDao = propertyDao;
	}

	@Override
	public void updateAll(Collection<Test> entities) {
		testDao.updateAll(entities);
	}

	@Override
	public Object createIndex(String tableName, String column, String indexName) {
		return testDao.createIndex(tableName, column, indexName);
	}

}
