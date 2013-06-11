package il.co.topq.systems.report.events.scenario
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.views.components.reports.tools.TestDetail;
	
	public class GetScenarioByIDEvent extends CairngormEvent
	{
		public static var GET_SCENARIO_BY_ID_EVENT:String = "getScenarioByIDEvent";
		public var scenarioID:String;
		public var callbackResult:Function;
		public var callbackFault:Function; 

		public function GetScenarioByIDEvent(scenarioID : String,callbackResult:Function, callbackFault:Function, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(GET_SCENARIO_BY_ID_EVENT,bubbles,cancelable);
			this.scenarioID = scenarioID;
			this.callbackFault = callbackFault;
			this.callbackResult = callbackResult;
		}
	}
}
