package il.co.topq.systems.report.events.scenario
{
	import il.co.topq.systems.report.events.general.GetPropertiesKeysEvent;

	public class GetScenarioPropertiesKeysEvent extends GetPropertiesKeysEvent  
	{
		public static var GET_SCENARIO_PROPERTIES_VALUES_EVENT:String = "getScenarioPropertiesValueEventType";
		public var eventServiceLocator:String = "getScenariosPropertiesValues";
	
		public function GetScenarioPropertiesKeysEvent(callbackResult:Function, callbackFault:Function, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(GET_SCENARIO_PROPERTIES_VALUES_EVENT, eventServiceLocator, callbackResult, callbackFault, bubbles,cancelable);
		}
	}
}
