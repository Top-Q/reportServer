package il.co.topq.systems.report.events.scenario
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.models.valueobjects.ScenarioQuery;
	
	public class DeleteScenarioByScenarioQueryEvent extends CairngormEvent
	{
		public static var DELETE_SCENARIO_BY_SCENARIO_QUERY_EVENT:String = "deleteScenarioByScenarioQueryEvent";
		public var scenarioQuery:ScenarioQuery;
		public var callbackResult:Function;
		public var callbackFault:Function;
		
		public function DeleteScenarioByScenarioQueryEvent(scenarioQuery:ScenarioQuery, callbackResult:Function, callbackFault:Function, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(DELETE_SCENARIO_BY_SCENARIO_QUERY_EVENT,bubbles,cancelable);
			this.scenarioQuery = scenarioQuery;
			this.callbackFault = callbackFault;
			this.callbackResult = callbackResult;
		}
	}
}