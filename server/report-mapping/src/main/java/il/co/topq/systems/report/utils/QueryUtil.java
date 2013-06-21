package il.co.topq.systems.report.utils;

import static il.co.topq.systems.report.utils.QueryInterface.*;
import il.co.topq.systems.report.common.exception.SoftwareException;
import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.obj.Chunk;
import il.co.topq.systems.report.common.obj.ScenarioQuery;
import il.co.topq.systems.report.common.obj.SortingColumn;
import il.co.topq.systems.report.common.obj.TestQuery;
import il.co.topq.systems.report.common.obj.TestSummaryQuery;
import il.co.topq.systems.report.common.obj.TimeRange;

import java.text.SimpleDateFormat;
import java.util.Collection;

import org.hibernate.Query;

public class QueryUtil {

	private QueryUtil() {
	}

	/**
	 * This method will create a restriction for descending StartTime
	 * 
	 * @param queryStr
	 *            the query to concatenating to
	 * @return String represent the whole query with restriction.
	 */
	public static String createQueryStrWithDescending(String queryStr) {
		return queryStr + " order by startTime desc";
	}

	/**
	 * Set query result size by the given chunk
	 * 
	 * @param query
	 *            The query instance
	 * @param chunk
	 *            The chunk
	 * @return The updated query instance
	 * @throws NullPointerException
	 */
	public static Query setQueryResultSize(Query query, final Chunk chunk) {
		if (chunk != null) {
			query.setFirstResult(chunk.getStartIndex());
			query.setMaxResults(chunk.getLength());
		}
		return query;
	}

	public static String setQueryResultSize(String query, final Chunk chunk) {
		if (chunk != null) {
			return new StringBuilder().append(query).append(" limit ").append(chunk.getStartIndex()).append(',')
					.append(chunk.getLength()).toString();
		}
		return query;
	}

	/**
	 * Create query from chunk, time range and session
	 * 
	 * @param timeRange
	 *            The time range parameters
	 * @param serviceName
	 *            A string of type "Scenario as scenario" for creating string "from Scenario as scenario where ..."
	 * @return A String representing the query
	 */
	public static String createQueryStr(final TimeRange timeRange, String serviceName) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		final Long lowBoundDate = timeRange.getLowBoundDate(),

		upBoundDate = timeRange.getUpBoundDate();

		if ((null == lowBoundDate) && (null == upBoundDate)) {
			return ("from " + serviceName);
		}
		if (lowBoundDate == null) {
			return ("from " + serviceName + " where startTime <'" + format.format(upBoundDate) + "'");
		}
		if (upBoundDate == null) {
			return ("from " + serviceName + " where startTime >'" + format.format(lowBoundDate) + "'");
		}
		if ((lowBoundDate.compareTo(upBoundDate) < 0)) {
			return ("from " + serviceName + " where startTime <'" + format.format(upBoundDate) + "' and startTime >'"
					+ format.format(lowBoundDate) + "'");
		}
		String message = new StringBuilder().append("lowBoundDate = ").append(lowBoundDate).append(", upBoundDate = ")
				.append(upBoundDate).append(" lowBoundDate greater than upBoundDate").append("").toString();
		throw new SoftwareException(message);
	}

	/**
	 * This method creates a timeRange restriction for query;
	 * 
	 * @param timeRange
	 *            -
	 * @param timeMemberName
	 *            -
	 * @return String represents the query restriction.
	 */
	public static String createTimeRangeRestrictionQuery(TimeRange timeRange, String timeMemberName) {

		final Long lowBoundDate = timeRange.getLowBoundDate(), upBoundDate = timeRange.getUpBoundDate();

		if ((null == lowBoundDate) && (null == upBoundDate)) {
			return null;
		}
		if (lowBoundDate == null) {
			return (" " + timeMemberName + " < " + upBoundDate);
		}
		if (upBoundDate == null) {
			return (" " + timeMemberName + " > " + lowBoundDate);
		}
		if ((lowBoundDate.compareTo(upBoundDate) < 0)) {
			return (" " + timeMemberName + " < " + upBoundDate + " and " + timeMemberName + " > " + lowBoundDate);
		}
		throw new RuntimeException("lowBoundDate greater than upBoundDate");
	}

	/**
	 * This method will be used to fetch testSummaryQuery data.<br>
	 * 
	 * @param testQuery
	 * @param selectWhat
	 * @param fromWhere
	 * @return
	 */
	public static String formatTestSummarySelectQuery(TestQuery testQuery, String selectWhat, String fromWhere) {

		TimeRange timeRange = testQuery.getTimeRange();

		QueryBuilder queryBuilder = new QueryBuilder().select(selectWhat).from(fromWhere).where()
				.add("published_runs_01.runIndex = published_test_01.runIndex");

		// Add Test Properties to the query
		Collection<ReportProperty> properties = testQuery.getProperties();
		if (properties != null && !properties.isEmpty()) {
			queryBuilder.and().addProperties(properties);
		}

		// Add time range, grouping and sorting
		if ((timeRange != null) && (!timeRange.isEmpty())) {
			queryBuilder.and();
		}
		queryBuilder.between(timeRange, TEST_TABLE_NAME + "." + TIME_FIELD.TEST_TIME_FIELD.valueOf());
		queryBuilder.addGroupBy(testQuery.getGroupByQueryString());
		queryBuilder.sort(testQuery.getSortingColumn());
		// Build the query
		return queryBuilder.build();
	}

	// public static String formatTestSelectQuery(TestQuery testQuery, String selectWhat, String fromWhere) {
	// QueryBuilder queryBuilder = new QueryBuilder().select(selectWhat).from(fromWhere);
	//
	// // Add Test Properties to the query
	// Collection<ReportProperty> properties = testQuery.getProperties();
	// if (properties != null && !properties.isEmpty()) {
	// queryBuilder.where().addProperties(properties);
	// }
	// TimeRange timeRange = testQuery.getTimeRange();
	//
	// // If time range is not null and is not empty and the same is for the
	// // properties
	// // add the relevant connection keyword
	// if ((timeRange != null) && (!timeRange.isEmpty())) {
	// if ((properties == null) || properties.isEmpty()) {
	// queryBuilder.where();
	// } else {
	// queryBuilder.and();
	// }
	// }
	//
	// // Add time range, grouping and sorting
	// queryBuilder.between(testQuery.getTimeRange(), TIME_FIELD.TEST_TIME_FIELD.valueOf());
	// queryBuilder.addGroupTestsSameScenario(testQuery);
	// queryBuilder.addGroupBy(testQuery.getGroupByQueryString());
	// queryBuilder.sort(testQuery.getSortingColumn());
	// // Build the query
	// return queryBuilder.build();
	// }

	public static String formatTestSelectQuery(TestQuery testQuery, String selectWhat, String fromWhere) {
		QueryBuilder queryBuilder = new QueryBuilder().select(selectWhat).from(fromWhere);

		// Add Test Properties to the query
		Collection<ReportProperty> properties = testQuery.getProperties();
		if (properties != null && !properties.isEmpty()) {
			queryBuilder.where().addProperties(properties);
		}
		TimeRange timeRange = testQuery.getTimeRange();
		String searchName = testQuery.getSearchName();

		if (searchName != null) {
			if (properties == null || properties.isEmpty()) {
				queryBuilder.where();
			} else {
				queryBuilder.and();
			}
			queryBuilder.addSearchNameLike(TEST_NAME_FIELD, searchName);
		}

		// If time range is not null and is not empty and the same is for the
		// properties
		// add the relevant connection keyword
		if ((timeRange != null) && (!timeRange.isEmpty())) {
			if (((properties == null) || properties.isEmpty()) && searchName == null) {
				queryBuilder.where();
			} else {
				queryBuilder.and();
			}
		}

		// Add time range, grouping and sorting
		queryBuilder.between(testQuery.getTimeRange(), TIME_FIELD.TEST_TIME_FIELD.valueOf());
		queryBuilder.addGroupTestsSameScenario(testQuery);
		queryBuilder.addGroupBy(testQuery.getGroupByQueryString());
		queryBuilder.sort(testQuery.getSortingColumn());
		// Build the query
		return queryBuilder.build();
	}

	/**
	 * This method will be called upon fetching tests from a group of tests.<br>
	 * Looking for test by name and scenario name.
	 * 
	 * @param testSummaryQuery
	 * @param selectWhat
	 * @return
	 */
	public static String selectFromTestTable(TestSummaryQuery testSummaryQuery, String selectWhat) {

		TestQuery testQuery = testSummaryQuery.getTestQuery();
		TimeRange timeRange = testQuery.getTimeRange();
		QueryBuilder queryBuilder = new QueryBuilder().select(selectWhat).from(TEST_TABLE_NAME);
		// Add Test Properties to the query
		Collection<ReportProperty> properties = testQuery.getProperties();
		if (properties != null && !properties.isEmpty()) {
			queryBuilder.where().addProperties(properties);
		}

		if (properties != null && !testQuery.getProperties().isEmpty()) {
			queryBuilder.and();
		} else {
			queryBuilder.where();
		}
		queryBuilder.addTestNameEquals(testSummaryQuery.getTestSummary().getTestName());
		queryBuilder.addScenarioNameEquals(testSummaryQuery.getTestSummary().getScenarioName());

		if ((timeRange != null) && (!timeRange.isEmpty())) {
			queryBuilder.and();
		}

		// Add time range, grouping and sorting
		queryBuilder.between(timeRange, TEST_TABLE_NAME.toString() + "." + TIME_FIELD.TEST_TIME_FIELD.valueOf());
		queryBuilder.addGroupBy(testQuery.getGroupByQueryString()).sort(testQuery.getSortingColumn());
		// Build the query
		return queryBuilder.build();
	}

	/**
	 * This method will create an SQL query in respect to the query received.<br>
	 * 
	 * @param scenarioQuery
	 *            -
	 * @param selectMethod
	 *            "*" / "count (*)"
	 * @return Query
	 */
	public static String getQueryStr(ScenarioQuery scenarioQuery, String selectMethod) {
		Collection<ReportProperty> propertiesList = scenarioQuery.getProperties();
		TimeRange timeRange = scenarioQuery.getTimeRange();
		Boolean timeAsc = scenarioQuery.getTimeAsc();
		String searchName = scenarioQuery.getSearchName();
		String orderType;
		SortingColumn sortingColumn = scenarioQuery.getSortingColumn();
		String queryStr;
		if ((propertiesList != null) && (!propertiesList.isEmpty())) {
			queryStr = generateQueryStrGetScenariosByProperties(propertiesList, selectMethod);
		} else {
			queryStr = "select " + selectMethod + " from " + SCENARIO_TABLE_NAME;
		}

		if (searchName != null) {
			if (propertiesList == null || propertiesList.isEmpty()) {
				queryStr += " where ( scenarioName like '%" + searchName + "%' )";
			} else {
				queryStr += " and ( scenarioName like '%" + searchName + "%' )";
			}
		}

		if (timeRange != null) {
			String timeRangeQueryStr = QueryUtil.createTimeRangeRestrictionQuery(timeRange, "startTime");
			if (timeRangeQueryStr != null) {
				if ((propertiesList == null || propertiesList.isEmpty()) && searchName == null) {
					queryStr += " where (" + timeRangeQueryStr + ")";
				} else {
					queryStr += " and (" + timeRangeQueryStr + ")";
				}
			}
		}

		if (sortingColumn != null && sortingColumn.getName() != null) {
			String sortedColName = sortingColumn.getName();
			Boolean colAsc = sortingColumn.isAsc();
			String colAscStr;
			if (colAsc) {
				colAscStr = "asc";
			} else {
				colAscStr = "desc";
			}
			if (timeAsc) {
				orderType = "asc";
			} else {
				orderType = "desc";
			}
			queryStr += "order by " + sortedColName + " " + colAscStr;
			if (!sortedColName.equals("startTime")) {
				queryStr += ", startTime " + orderType; // descending;
			}
		} else {

			if (timeAsc) {
				orderType = "asc";
			} else {
				orderType = "desc";
			}
			queryStr += "order by startTime " + orderType; // descending;
		}
		return queryStr;

	}

	/**
	 * this method will create a string for the getTestsByProperty Query;
	 * 
	 * @param propertiesList
	 *            : a list of properties to filter upon.
	 * @param selection
	 *            : in a role of * or count(*) for size of result query;
	 * @return String representation of the query;
	 */
	private static String generateQueryStrGetScenariosByProperties(Collection<ReportProperty> propertiesList,
			String selection) {

		QueryBuilder queryBuilder = new QueryBuilder().select(selection).from(SCENARIO_TABLE_NAME);

		if (propertiesList != null && !propertiesList.isEmpty()) {
			queryBuilder.where().addProperties(propertiesList);
		}

		return queryBuilder.toString();

	}

}
