package il.co.topq.systems.report.events.testCustomReport
{
	import com.adobe.cairngorm.control.CairngormEvent;

	public class GetScenarioCustomReportPropertiesValuesEvent extends CairngormEvent
	{
		public static var GET_SCENARIO_CUSTOM_REPORT_PROPERTIES_VALUES_EVENT:String = 
			"GetScenarioCustomReportPropertiesValuesEventType";
		
		public var callbackResult:Function;
		public var callbackFault:Function;
		
		public var scenarioCustomReportID:String;
		
		public function GetScenarioCustomReportPropertiesValuesEvent(scenarioCustomReportID : String, callbackResult:Function, callbackFault:Function,  bubbles : Boolean = false, cancelable : Boolean = false){
		
			super(GET_SCENARIO_CUSTOM_REPORT_PROPERTIES_VALUES_EVENT,bubbles,cancelable);
			this.scenarioCustomReportID = scenarioCustomReportID;		
			this.callbackFault = callbackFault;
			this.callbackResult = callbackResult;
		}
	}
}