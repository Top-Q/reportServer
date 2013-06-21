package il.co.topq.systems.report.events.local
{
	import flash.events.Event;
	
	import il.co.topq.systems.report.models.valueobjects.ScenarioProperty;
	
	public class SaveScenarioPropertyEvent extends Event
	{
		public var scenarioProperty:ScenarioProperty;
		
		public static var SAVE_SCENARIO_PROPERTY_EVENT:String = "saveScenarioPropertyEvent";
		
		public function SaveScenarioPropertyEvent(scenarioProperty:ScenarioProperty = null, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(SAVE_SCENARIO_PROPERTY_EVENT, bubbles, cancelable);
			this.scenarioProperty = scenarioProperty;
		}
	}
}