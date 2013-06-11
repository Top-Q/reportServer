package il.co.topq.systems.report.utils;

import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.obj.SortingColumn;
import il.co.topq.systems.report.common.obj.TestQuery;
import il.co.topq.systems.report.common.obj.TimeRange;

import java.util.Collection;
import java.util.Iterator;

public class QueryBuilder {

	private StringBuilder sb = new StringBuilder();

	public QueryBuilder() {
	}

	public QueryBuilder(String query) {
		sb.append(query);
	}

	public QueryBuilder select(String what) {
		sb.append("select ").append(what).append(" ");
		return this;
	}

	public QueryBuilder count() {
		add("count(*)");
		return this;
	}

	public QueryBuilder add(String str) {
		sb.append(" ").append(str).append(" ");
		return this;
	}

	public QueryBuilder between(TimeRange timeRange, String timeField) {
		// no time range query is needed.
		if (timeRange == null || timeRange.isEmpty()) {
			return this;
		}

		Long lowBoundDate = timeRange.getLowBoundDate();
		Long upBoundDate = timeRange.getUpBoundDate();
		if ((null == lowBoundDate) && (null == upBoundDate)) {
			return this;
		}
		if (lowBoundDate == null) {
			add("(" + timeField + " <'" + upBoundDate + "')");
		} else if (upBoundDate == null) {
			add("(" + timeField + " >'" + lowBoundDate + "')");
		} else if ((lowBoundDate.compareTo(upBoundDate) < 0)) {
			add("(" + timeField + " <" + upBoundDate + " and " + timeField + " > " + lowBoundDate + " )");
		}
		return this;
	}

	public QueryBuilder sort(SortingColumn sortingColumn) {
		if (sortingColumn != null) {
			orderBy(sortingColumn.getName()).ascending(sortingColumn.isAsc());
		}
		return this;
	}

	private QueryBuilder ascending(boolean asc) {
		if (asc) {
			add("asc");
		} else {
			add("desc");
		}
		return this;
	}

	private QueryBuilder orderBy(String... fields) {
		if (fields == null)
			return this;
		add("order by");
		for (int i = 0; i < fields.length; i++) {
			add(fields[i]);
			if (i + 1 < fields.length) {
				addComa();
			}
		}
		return this;
	}

	public QueryBuilder where() {
		add("where");
		return this;
	}

	public QueryBuilder and() {
		add("and");
		return this;
	}

	public QueryBuilder in() {
		add("in");
		return this;
	}

	public QueryBuilder addGroupBy(String... groups) {
		if (groups == null)
			return this;
		add("group by");
		for (int i = 0; i < groups.length; i++) {
			sb.append(groups[i]);
			if (i + 1 < groups.length) {
				add(",");
			}
		}
		return this;
	}

	public QueryBuilder addComa() {
		add(",");
		return this;
	}

	@Override
	public String toString() {
		return sb.toString();
	}

	public String build() {
		return toString();
	}

	public QueryBuilder addProperties(Collection<ReportProperty> properties) {
		StringBuilder sb = new StringBuilder();

		Iterator<ReportProperty> iterator = properties.iterator();
		while (iterator.hasNext()) {
			ReportProperty reportProperty = iterator.next();
			sb.append("(propertiesStr LIKE '%").append(reportProperty.getPropertyKey()).append('=')
					.append(reportProperty.getPropertyValue());
			/*
			 * If propertyValue == "" will fetch all the matching result for this key.
			 */
			if (reportProperty.getPropertyValue().equals("")) {
				sb.append("%')");
			} else {
				sb.append(",%')");
			}

			if (iterator.hasNext()) {
				sb.append(" and ");
			}
		}
		return add(sb.toString());
	}

	public QueryBuilder from(String fromWhere) {
		add("from").add(fromWhere);
		return this;
	}

	/**
	 * This method will append to query the comparison of the testName.
	 * 
	 * @param testName
	 * @return QueryBuilder
	 */
	public QueryBuilder addTestNameEquals(String testName) {
		add("testIndex").in().add("(").select("testId").from("test_description").where()
				.add("test_description.testName=").add("'" + testName + "'").add(")");
		return this;
	}

	/**
	 * This method will append to query the comparison of the scenarioName.
	 * 
	 * @param scenarioName
	 * @return QueryBuilder
	 */
	public QueryBuilder addScenarioNameEquals(String scenarioName) {
		if (scenarioName != null && !scenarioName.isEmpty()) {
			and().add("runIndex").in().add("(").select("runIndex").from("published_runs_01").where()
					.add("scenarioName=").add("'" + scenarioName + "'").add(")");
		}
		return this;
	}

	public QueryBuilder addSearchNameLike(String searchField, String searchName) {
		if (searchName != null && !searchName.isEmpty()) {
			add("(" + searchField + " like '%" + searchName + "%')");
		}
		return this;
	}

	public QueryBuilder addGroupTestsSameScenario(TestQuery testQuery) {
		if (testQuery.getGroupBy() != 0) {
			where().add("published_runs_01.runIndex = published_test_01.runIndex");
		}
		return this;

		// switch(testQuery.getGroupBy()){
		// case 2: //testName + Scenario
		// where().add("published_runs_01.runIndex = published_test_01.runIndex");
		// case 4: //testName + Scenario + params
		// }
		//
	}

	// /**
	// * this method will create a string for the getTestsByProperty Query;
	// *
	// * @param propertiesList
	// * : a list of properties to filter upon.
	// * @return String representation of the query;
	// */
	// private static String generateQueryStrGetTestsByProperties(
	// Collection<ReportProperty> propertiesList) {
	//
	//
	// String queryStr = " where (published_test_01.testIndex in (";
	// queryStr += QueryInterface.CREATE_TESTS_BY_FILTER_QUERY;
	// if (propertiesList != null && !propertiesList.isEmpty()) {
	// for (int i = 0; i < propertiesList.size(); i++) {
	// queryStr += " and (propertyKey = '"
	// + propertiesList.get(i).getPropertyKey() + "' ";
	// if ((!propertiesList.get(i).getPropertyValue().isEmpty())
	// && (propertiesList.get(i).getPropertyValue() != null)) {
	// queryStr += "and propertyValue = '"
	// + propertiesList.get(i).getPropertyValue() + "')";
	// } else {
	// queryStr += ")";
	// }
	// if ((i + 1) == propertiesList.size())// last property
	// {
	// String closingBrackets =
	// getClosingBracketsForTestFilterQuery(propertiesList
	// .size());
	// queryStr += closingBrackets;
	// } else // not last property --> concatenate next;
	// {
	// queryStr += "and (published_test_01.testIndex in("
	// + QueryInterface.CREATE_TESTS_BY_FILTER_QUERY;
	// }
	// }
	// } else // / in case no properties as filters need to add 3 closing
	// // brackets
	// {
	// queryStr += getClosingBracketsForTestFilterQuery(1);
	// }
	//
	// return queryStr;
	// }
}
