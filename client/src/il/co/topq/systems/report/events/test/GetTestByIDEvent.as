package il.co.topq.systems.report.events.test
{
	import com.adobe.cairngorm.control.CairngormEvent;

	public class GetTestByIDEvent extends CairngormEvent
	{
		public static var GET_TEST_BY_ID_EVENT:String = "getTestByIDEvent";
		
		public var testID:String;
		public var callbackResult:Function;
		public var callbackFault:Function;
		
		public function GetTestByIDEvent(testID : String, callbackResult:Function, callbackFault:Function, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(GET_TEST_BY_ID_EVENT,bubbles,cancelable);
			this.testID = testID;
			this.callbackFault = callbackFault;
			this.callbackResult = callbackResult;
		}
	}
}
	