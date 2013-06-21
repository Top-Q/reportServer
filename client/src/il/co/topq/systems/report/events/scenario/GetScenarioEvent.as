package il.co.topq.systems.report.events.scenario
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.models.valueobjects.ScenarioQuery;

	public class GetScenarioEvent extends CairngormEvent
	{
		public static var EXECUTION_GET_SCENARIO_EVENT:String = "GetScenarioEvent";
		
		public var scenarioQuery:ScenarioQuery;
		public var callbackResult:Function;
		public var callbackFault:Function;
		
		public function GetScenarioEvent(scenarioQuery:ScenarioQuery, callbackResult:Function, callbackFault:Function, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(EXECUTION_GET_SCENARIO_EVENT,bubbles,cancelable);
			this.scenarioQuery = scenarioQuery;
			this.callbackFault = callbackFault;
			this.callbackResult = callbackResult;
		}
	}
}



	