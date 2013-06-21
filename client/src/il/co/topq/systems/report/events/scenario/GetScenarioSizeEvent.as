package il.co.topq.systems.report.events.scenario
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.commands.general.GetScenarioQuerySizeEvent;
	import il.co.topq.systems.report.models.valueobjects.ScenarioGridModel;
	
	public class GetScenarioSizeEvent extends GetScenarioQuerySizeEvent
	{
		public static var GET_SCENARIO_SIZE_EVENT:String = "GetScenarioSizeEventType";		
		public var eventServiceLocator:String = "getScenariosSize";
		
		public function GetScenarioSizeEvent(scenarioGridModel:ScenarioGridModel, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(GET_SCENARIO_SIZE_EVENT, scenarioGridModel, eventServiceLocator, bubbles,cancelable);
		}
	}
}
