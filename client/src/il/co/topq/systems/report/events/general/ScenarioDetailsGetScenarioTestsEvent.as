 package il.co.topq.systems.report.events.general
{
	import flash.events.Event;
	
	import il.co.topq.systems.report.models.valueobjects.Scenario;
	
	public class ScenarioDetailsGetScenarioTestsEvent extends Event
	{
		public static var SCENARIO_DETAILS_GET_SCENARIO_TESTS_EVENT:String = "scenarioDetailsGetScenarioTestsEvent";
		public var scenario:Scenario;
		
		public function ScenarioDetailsGetScenarioTestsEvent(scenario:Scenario, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(SCENARIO_DETAILS_GET_SCENARIO_TESTS_EVENT, bubbles, cancelable);
			this.scenario = scenario;
		}
	}
}