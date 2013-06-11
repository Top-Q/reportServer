package il.co.topq.systems.report.events.testCustomReport
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	public class GetTestCustomReportPropertiesValuesEvent extends CairngormEvent
	{
		public static var GET_TEST_CUSTOM_REPORT_PROPERTIES_VALUES_EVENT:String = "GetTestCustomReportPropertiesValuesEventType";
		
		public var testCustomReportID:String;
		public var callbackResult:Function;
		public var callbackFault:Function;
		
		public function GetTestCustomReportPropertiesValuesEvent(testCustomReportID : String, callbackResult:Function, callbackFault:Function, bubbles : Boolean = false, cancelable : Boolean = false){
			super(GET_TEST_CUSTOM_REPORT_PROPERTIES_VALUES_EVENT,bubbles,cancelable);
			this.testCustomReportID = testCustomReportID;
			this.callbackFault = callbackFault;
			this.callbackResult = callbackResult;
		}
	}
}