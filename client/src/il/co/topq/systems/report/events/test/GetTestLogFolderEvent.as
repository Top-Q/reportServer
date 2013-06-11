package il.co.topq.systems.report.events.test
{
	import com.adobe.cairngorm.control.CairngormEvent;

	public class GetTestLogFolderEvent extends CairngormEvent
	{
		public static var GET_TEST_LOG_FOLDER_EVENT:String = "getTestLogFolderEvent";
		public var callbackFault : Function;
		public var callbackResult : Function;
		public var testId : String;
		
		public function GetTestLogFolderEvent(testId:String, callbackResult:Function, callbackFault:Function, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(GET_TEST_LOG_FOLDER_EVENT,bubbles,cancelable);
			this.callbackFault = callbackFault;
			this.callbackResult = callbackResult;
			this.testId = testId;
		}
	}
}

