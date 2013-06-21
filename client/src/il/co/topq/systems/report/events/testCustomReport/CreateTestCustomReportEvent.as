package il.co.topq.systems.report.events.testCustomReport
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	public class CreateTestCustomReportEvent extends CairngormEvent
	{
		public static var CREATE_TEST_CUSTOM_REPORT_EVENT:String = "CreateTestCustomReportEventType";
		public var callbackResult:Function;
		public var callbackFault:Function;
		
		public function CreateTestCustomReportEvent(callbackResult:Function, callbackFault:Function, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(CREATE_TEST_CUSTOM_REPORT_EVENT, bubbles,cancelable);			
			this.callbackFault = callbackFault;
			this.callbackResult = callbackResult;
		}
	}
}