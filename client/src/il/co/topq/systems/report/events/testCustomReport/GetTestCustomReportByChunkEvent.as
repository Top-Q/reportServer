package il.co.topq.systems.report.events.testCustomReport
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.models.valueobjects.TestGridModel;
	
	public class GetTestCustomReportByChunkEvent extends CairngormEvent
	{
		public static var GET_CUSTOM_REPORT_EVENT:String = "GetCustomReportEventType";
		public var testGridModel:TestGridModel
		public var callbackResult:Function;
		public var callbackFault:Function;
		
		public function GetTestCustomReportByChunkEvent(testGridModel:TestGridModel,callbackResult:Function, callbackFault:Function, bubbles : Boolean = false, cancelable : Boolean = false)
		{			
			super(GET_CUSTOM_REPORT_EVENT,bubbles,cancelable);
			this.testGridModel = testGridModel;
			this.callbackFault = callbackFault;
			this.callbackResult = callbackResult;
		}
	}
}