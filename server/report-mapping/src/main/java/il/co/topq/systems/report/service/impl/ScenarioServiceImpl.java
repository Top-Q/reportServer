package il.co.topq.systems.report.service.impl;

import il.co.topq.systems.report.common.exception.SoftwareException;
import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.model.Scenario;
import il.co.topq.systems.report.common.model.Test;
import il.co.topq.systems.report.common.obj.Chunk;
import il.co.topq.systems.report.common.obj.ComparedTestDetails;
import il.co.topq.systems.report.common.obj.ScenarioComparator;
import il.co.topq.systems.report.common.obj.ScenarioQuery;
import il.co.topq.systems.report.common.obj.SortingColumn;
import il.co.topq.systems.report.common.obj.TestComparator;
import il.co.topq.systems.report.common.obj.TestQuery;
import il.co.topq.systems.report.dao.infra.PropertyDao;
import il.co.topq.systems.report.dao.infra.ScenarioDao;
import il.co.topq.systems.report.dao.infra.TestDao;
import il.co.topq.systems.report.service.infra.ScenarioService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("scenarioService")
@Transactional
public class ScenarioServiceImpl implements ScenarioService {

	@Autowired
	private ScenarioDao scenarioDao;

	@Autowired
	private TestDao testDao;

	private PropertyDao<ReportProperty> scenarioPropertyDao;
	private PropertyDao<ReportProperty> testPropertyDao;

	@Override
	public Scenario create(Scenario scenario) throws Exception {
		Collection<ReportProperty> scenarioProperties = scenario.getProperties();
		if (scenarioPropertyDao.areValidProperties(scenarioProperties)) {
			scenario.setProperties(scenarioPropertyDao.createProperties(scenarioProperties));
		} else {
			throw new Exception("scenario properties are invalid, could not create scenario");
		}

		for (Test test : scenario.getTests()) {
			Collection<ReportProperty> properties = test.getProperties();
			if (testPropertyDao.areValidProperties(properties)) {
				test.setProperties(testPropertyDao.createProperties(properties));
			} else {
				throw new Exception("test properties are invalid, could not create test");
			}
		}
		return scenarioDao.create(scenario);
	}

	@Secured(value = "ROLE_DELETE_SCENARIO")
	@Override
	public void delete(int id) {
		scenarioDao.delete(id);
	}

	@Override
	public Scenario update(Scenario scenario) throws Exception {
		Collection<ReportProperty> scenarioProperties = scenario.getProperties();
		if (scenarioPropertyDao.areValidProperties(scenarioProperties)) {
			scenario.setProperties(scenarioPropertyDao.createProperties(scenarioProperties));
			return scenarioDao.update(scenario);
		} else {
			throw new Exception("test properties are invalid, could not update scenario");
		}

	}

	@Override
	public long countAll(Map<String, Object> params) {
		return scenarioDao.countAll(params);
	}

	@Override
	public Scenario get(long id) {
		return scenarioDao.get(id);
	}

	@Override
	public List<Scenario> getAll() {
		return scenarioDao.getAll();
	}

	@Override
	public int getSizeOfTestsSetByScenarioID(Integer id) {
		return scenarioDao.getScenarioNumberOfTests(id);
	}

	@Override
	public ScenarioComparator compareScenarios(Integer[] scenariosIDArray) {
		List<Scenario> comparedScenarioList = getScenarios(scenariosIDArray);
		ScenarioComparator scenarioComparator = new ScenarioComparator();
		List<TestComparator> comparedTests = scenarioComparator.getComparedTests();
		TestQuery testQuery = new TestQuery();
		// TODO: change table column name to fetch from consts class
		testQuery.setSortingColumn(new SortingColumn("startTime", true));

		if (!comparedScenarioList.isEmpty()) {

			for (Scenario scenario : comparedScenarioList) {

				scenarioComparator.addScenario(scenario.getScenarioName(), scenario.getId());

				List<Test> tests = testDao.getTestsByScenarioID(scenario.getId(), testQuery);

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

	@Override
	public List<Scenario> getScenariosByFiltersWithSortingCol(ScenarioQuery scenarioQuery) {
		return scenarioDao.getScenariosByFiltersWithSortingCol(scenarioQuery);
	}

	@Override
	public int getSizeOfScenarioQuery(ScenarioQuery scenarioQuery) {
		return scenarioDao.getSizeOfScenarioQuery(scenarioQuery);
	}

	private List<Scenario> getScenarios(Integer[] scenariosIDArray) {
		List<Scenario> comparedScenarioList = new ArrayList<Scenario>();
		for (Integer scenarioID : scenariosIDArray) {
			comparedScenarioList.add(scenarioDao.get(scenarioID));
		}
		return comparedScenarioList;
	}

	@Override
	public List<Scenario> getChunk(Chunk chunk) {
		return scenarioDao.get(chunk);
	}

	public void setScenarioPropertyDao(PropertyDao<ReportProperty> scenarioPropertyDao) {
		this.scenarioPropertyDao = scenarioPropertyDao;
	}

	public void setTestPropertyDao(PropertyDao<ReportProperty> testPropertyDao) {
		this.testPropertyDao = testPropertyDao;
	}

	@Override
	public void updateAll(Collection<Scenario> scenarios) {
		scenarioDao.updateAll(scenarios);
	}

	@Override
	public Object createIndex(String tableName, String column, String indexName) {
		return scenarioDao.createIndex(tableName, column, indexName);
	}

}
