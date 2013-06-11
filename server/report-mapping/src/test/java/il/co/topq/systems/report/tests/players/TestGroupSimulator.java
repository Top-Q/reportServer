//package il.co.topq.systems.report.tests.players;
//
//import il.co.topq.systems.report.beans.TestBean;
//import il.co.topq.systems.report.common.interfaces.TestService;
//import il.co.topq.systems.report.common.model.Test;
//import il.co.topq.systems.report.common.model.TestSummary;
//import il.co.topq.systems.report.common.obj.TestQuery;
//import il.co.topq.systems.report.common.obj.TestSummaryQuery;
//
//import java.util.List;
//
//public class TestGroupSimulator {
//
//	private TestService testService = new TestBean();
//
//	public List<TestSummary> getTestGroupByName() {
//		TestQuery testQuery = new TestQuery();
//		testQuery.setGroupBy(1);
//		return testService.getTestSummary(testQuery);
//	}
//
//	public List<TestSummary> getTestGroupByNameAndScenario() {
//		TestQuery testQuery = new TestQuery();
//		testQuery.setGroupBy(2);
//		return testService.getTestSummary(testQuery);
//	}
//
//	//
//	// public List<TestSummary> getTestGroupByNameAndScenarioAndParams() {
//	// TestQuery testQuery = new TestQuery();
//	// testQuery.setGroupBy(3);
//	// return testService.getTestSummary(testQuery);
//	// }
//
//	public List<TestSummary> getTestByGroup(TestQuery testQuery) {
//		return testService.getTestSummary(testQuery);
//	}
//
//	public List<Test> getTestByTestSummary(TestSummaryQuery testSummaryQuery) {
//		return testService.getTestsByTestSummary(testSummaryQuery);
//	}
//}
