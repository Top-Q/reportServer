package il.co.topq.systems.report.service.infra;

import il.co.topq.systems.report.common.model.Test;
import il.co.topq.systems.report.common.model.TestSummary;
import il.co.topq.systems.report.common.obj.Chunk;
import il.co.topq.systems.report.common.obj.TestQuery;
import il.co.topq.systems.report.common.obj.TestSummaryQuery;
import il.co.topq.systems.report.common.obj.TimeRange;

import java.util.List;

/**
 * 
 * @author Eran Golan & Tomer Gafner
 */
public interface TestService extends BaseService<Test> {

	/**
	 * Get a tests list formed by the given chunk and time range
	 * 
	 * @param chunk
	 *            The chunk for getting a tests list
	 * @param timeRange
	 *            The time range for getting a tests list
	 * @return return an array of tests in descending order by startTime. An empty list is return in case no test match
	 *         the criteria. In case the chunk is in Illegal state (empty,null) return an empty list and write a warning
	 *         to Logger In case timeRange is in Illegal state (empty,null, lowBound>upBound) return an empty list and
	 *         write a warning to the Logger
	 */
	List<Test> getTests(final Chunk chunk, final TimeRange timeRange);

	/**
	 * This method will fetch data according to the testQuery<br>
	 * timeRange, chunk, properties, timeAsc, and extra property data in the form of sorted col.
	 * 
	 * @param testQuery
	 *            -
	 * @return -
	 */
	List<Test> getTestsByFiltersWithSortingCol(TestQuery testQuery);

	/**
	 * This method will get the size of a query according to the Query object received as parameter.
	 * 
	 * @param testQuery
	 *            -
	 * @return -
	 */
	Integer getSizeOfTestQuery(TestQuery testQuery);

	/**
	 * @param testSummaryQuery
	 *            -
	 * @return list of tests
	 */
	List<Test> getTestsByTestSummary(TestSummaryQuery testSummaryQuery);

	/**
	 * @param testQuery
	 *            -
	 * @return list of tests
	 */
	List<TestSummary> getTestSummary(TestQuery testQuery);

	/**
	 * This method will get all test by specific scenario id and return it according to given chunk;
	 * 
	 * @param scenarioId
	 *            -
	 * @param chunk
	 *            -
	 * @return List<Test>
	 */
	public List<Test> getTestsByScenarioId(Integer scenarioId, TestQuery testQuery);

}
