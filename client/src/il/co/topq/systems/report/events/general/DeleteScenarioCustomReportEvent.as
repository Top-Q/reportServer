package il.co.topq.systems.report.events.general
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	public class DeleteScenarioCustomReportEvent extends CairngormEvent
	{
		public static var DELETE_SCENARIO_CUSTOM_REPORT_EVENT:String = "deleteScenarioCustomReportEventType";
		
		public var callbackResult:Function;
		public var callbackFault:Function;
		
		public var scenarioCustomReportID:String;
		
		public function DeleteScenarioCustomReportEvent(scenarioCustomReportID: String, callbackResult:Function, callbackFault:Function, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(DELETE_SCENARIO_CUSTOM_REPORT_EVENT,bubbles,cancelable);
			this.scenarioCustomReportID = scenarioCustomReportID;
			this.callbackFault = callbackFault;
			this.callbackResult = callbackResult;
		}
	}
}