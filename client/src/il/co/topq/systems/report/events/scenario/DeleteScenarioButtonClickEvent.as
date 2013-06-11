package il.co.topq.systems.report.events.scenario
{
	import flash.events.Event;

	public class DeleteScenarioButtonClickEvent extends Event
	{
		public var scenarioID:String;
		
		public static var DELETE_SCENARIO_BUTTON_CLICK_EVENT:String = "deleteScenarioButtonClickEvent";
		
		public function DeleteScenarioButtonClickEvent(scenarioID:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{	super(DELETE_SCENARIO_BUTTON_CLICK_EVENT, bubbles, cancelable);
			this.scenarioID = scenarioID;
		}
	}
}
