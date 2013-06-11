package il.co.topq.systems.report.events.general
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	public class DeleteTestEvent extends CairngormEvent
	{
		public static var DELETE_TEST_EVENT:String = "deleteTestEventType";
		public var testID:String;
		public var callbackResult:Function;
		public var callbackFault:Function;
		
		public function DeleteTestEvent(testID: String, callbackResult:Function, callbackFault:Function, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(DELETE_TEST_EVENT,bubbles,cancelable);
			this.testID = testID;
			this.callbackFault = callbackFault;
			this.callbackResult = callbackResult;
		}
	}
}