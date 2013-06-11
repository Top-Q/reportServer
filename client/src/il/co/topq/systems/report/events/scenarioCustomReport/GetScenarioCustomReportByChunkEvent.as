package il.co.topq.systems.report.events.scenarioCustomReport
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.models.valueobjects.ScenarioGridModel;

	public class GetScenarioCustomReportByChunkEvent extends CairngormEvent
	{
		public static var GET_SCENARIO_CUSTOM_REPORT_EVENT:String = "GetScenarioCustomReportEventType";
		public var scenarioGridModel:ScenarioGridModel;
		public var callbackResult:Function;
		public var callbackFault:Function;
		
		public function GetScenarioCustomReportByChunkEvent(scenarioGridModel:ScenarioGridModel,callbackResult:Function, callbackFault:Function, bubbles : Boolean = false, cancelable : Boolean = false)
		{	
			super(GET_SCENARIO_CUSTOM_REPORT_EVENT,bubbles,cancelable);
			this.scenarioGridModel = scenarioGridModel; 
			this.callbackFault = callbackFault;
			this.callbackResult = callbackResult;
		}
	}
}	
