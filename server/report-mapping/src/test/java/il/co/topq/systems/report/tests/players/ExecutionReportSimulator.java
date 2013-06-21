//package il.co.topq.systems.report.tests.players;
//
//
//import il.co.topq.systems.report.beans.ScenarioBean;
//import il.co.topq.systems.report.beans.TestBean;
//import il.co.topq.systems.report.common.model.Scenario;
//import il.co.topq.systems.report.common.model.ScenarioProperty;
//import il.co.topq.systems.report.common.model.Test;
//import il.co.topq.systems.report.common.obj.SortingColumn;
//import il.co.topq.systems.report.common.obj.TestQuery;
//
//import java.util.List;
//import java.util.Set;
//
//public class ExecutionReportSimulator {
//
//
//    private ScenarioBean scenarioBean = new ScenarioBean();
//    private TestBean testBean = new TestBean();
//
//
//    public Scenario addPropertyToScenario(Scenario scenario, ScenarioProperty scenarioProperty) {
//        Set<ScenarioProperty> scenarioProperties = scenario.getScenarioProperties();
//        scenarioProperties.add(scenarioProperty);
//        scenarioBean.updateScenario(scenario);
//        return scenario;
//    }
//
//    public void deleteSpecificScenario(Scenario scenario) {
//        scenarioBean.deleteScenario(scenario.getId());
//    }
//
//
//    public void updateTest(Test test) {
//        testBean.updateTest(test);
//    }
//
//    public Scenario getScenario(Integer id) {
//        return scenarioBean.getScenario(id);
//    }
//
//    public Test selectTestAt(Scenario scenario, int index) {
//    	TestQuery testQuery = new TestQuery();
//    	testQuery.setSortingColumn(new SortingColumn("startTime", true));
//        List<Test> tests = new TestBean().getTestsByScenarioID(scenario.getId(),testQuery);
//        return tests.get(index);
//    }
//}
