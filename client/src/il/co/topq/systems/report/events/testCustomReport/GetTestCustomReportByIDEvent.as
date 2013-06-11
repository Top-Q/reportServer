package il.co.topq.systems.report.events.testCustomReport
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.models.valueobjects.Filter;
	
	import mx.controls.Alert;
	
	public class GetTestCustomReportByIDEvent extends CairngormEvent
	{
		public static var GET_TEST_CUSTOM_REPORT_BY_ID_EVENT:String = "GetTestCustomReportByIDEventType";
		public var id:String;
		
		public function GetTestCustomReportByIDEvent(id: String, bubbles : Boolean = false, cancelable : Boolean = false)
		{			
			super(GET_TEST_CUSTOM_REPORT_BY_ID_EVENT,bubbles,cancelable);		
			this.id = id;
		}
	}
}