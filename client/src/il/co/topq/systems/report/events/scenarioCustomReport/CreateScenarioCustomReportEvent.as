package il.co.topq.systems.report.events.scenarioCustomReport
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	public class CreateScenarioCustomReportEvent extends CairngormEvent
	{
		public static var CREATE_SCENARIO_CUSTOM_REPORT_EVENT:String = "CreateScenarioCustomReportEventType";
		public var callbackResult:Function;
		public var callbackFault:Function;
		
		public function CreateScenarioCustomReportEvent(callbackResult:Function, callbackFault:Function, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(CREATE_SCENARIO_CUSTOM_REPORT_EVENT, bubbles,cancelable);			
			this.callbackFault = callbackFault;
			this.callbackResult = callbackResult;
		}
	}
}	
