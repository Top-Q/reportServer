//package il.co.topq.systems.report.tests.players;
//
//import il.co.topq.systems.report.beans.ScenarioBean;
//import il.co.topq.systems.report.common.interfaces.ScenarioService;
//import il.co.topq.systems.report.common.model.Scenario;
//import il.co.topq.systems.report.utils.RandomData;
//
//public class JSystemGUI {
//
//	public enum PublishType {
//		NO_PROPERTIES, SCENARIO_PROPERTIES, TEST_PROPERTIES, DEFAULT
//	}
//
//	private ScenarioService scenarioService = new ScenarioBean();
//
//	public Scenario publish(PublishType type, int testNumber) {
//		Scenario scenario;
//		switch (type) {
//		case NO_PROPERTIES:
//			scenario = buildScenario(testNumber);
//			scenarioService.createScenario(scenario);
//			break;
//
//		case SCENARIO_PROPERTIES:
//			scenario = buildScenario(testNumber);
//			scenario.setScenarioProperties(RandomData.getScenarioProperties());
//			scenarioService.createScenario(scenario);
//			break;
//
//		case TEST_PROPERTIES:
//			scenario = buildScenario(testNumber);
//			RandomData.fillPropertiesForScenarioTests(scenario);
//			scenarioService.createScenario(scenario);
//			break;
//
//		default:
//			scenario = buildScenario(testNumber);
//			scenario.setScenarioProperties(RandomData.getScenarioProperties());
//			RandomData.fillPropertiesForScenarioTests(scenario);
//			scenarioService.createScenario(scenario);
//			break;
//
//		}
//		return scenario;
//	}
//
//	/**
//	 * 
//	 * Will set random scenario Name.
//	 * 
//	 * @param numOfGroups
//	 */
//	public void publishTestGroupByName(int numOfGroups) {
//		publishTestGroupByName(numOfGroups, null);
//	}
//
//	/**
//	 * 
//	 * Will set scenarioName to be the same as parameter.
//	 * 
//	 * @param numOfGroups
//	 * @param scenarioName
//	 */
//	public void publishTestGroupByName(int numOfGroups, String scenarioName) {
//		Scenario scenario = RandomData.getScenarioWithTestsGroupByName(numOfGroups);
//		if (scenarioName != null) {
//			scenario.setScenarioName(scenarioName);
//		}
//		scenarioService.createScenario(scenario);
//	}
//
//	private Scenario buildScenario(int testNumber) {
//		return RandomData.getScenarioWithTests(testNumber);
//	}
//}
