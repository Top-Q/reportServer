package il.co.topq.systems.report.events.general
{
	import com.adobe.cairngorm.control.CairngormEvent;

	public class DeleteTestCustomReportEvent extends CairngormEvent
	{
		public static var DELETE_TEST_CUSTOM_REPORT_EVENT:String = 
			"deleteTestCustomReportEventType";
		
		public var callbackResult:Function;
		public var callbackFault:Function;
		
		public var testCustomReportID:String;
		
		public function DeleteTestCustomReportEvent(testCustomReportID:String, callbackResult:Function, callbackFault:Function, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(DELETE_TEST_CUSTOM_REPORT_EVENT,bubbles,cancelable);
			this.testCustomReportID = testCustomReportID;
			this.callbackFault = callbackFault;
			this.callbackResult = callbackResult;
		}

	}
}