package il.co.topq.systems.report.dao.infra;

import java.util.List;

import il.co.topq.systems.report.common.model.Test;
import il.co.topq.systems.report.common.model.TestSummary;
import il.co.topq.systems.report.common.obj.Chunk;
import il.co.topq.systems.report.common.obj.TestQuery;
import il.co.topq.systems.report.common.obj.TestSummaryQuery;
import il.co.topq.systems.report.common.obj.TimeRange;

public interface TestDao extends GenericDao<Test> {

	List<Test> getTestsByScenarioID(Integer scenarioID, TestQuery testQuery);

	List<Test> get(final Chunk chunk, final TimeRange timeRange);

	List<TestSummary> getTestSummary(TestQuery testQuery);

	List<Test> getTestsByFiltersWithSortingCol(TestQuery testQuery);

	Integer getSizeOfTestQuery(TestQuery testQuery);

	List<Test> getTestsByTestSummary(TestSummaryQuery testSummaryQuery);

}
