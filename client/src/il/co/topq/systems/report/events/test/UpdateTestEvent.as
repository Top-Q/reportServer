package il.co.topq.systems.report.events.test
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	public class UpdateTestEvent extends CairngormEvent
	{
		public static var UPDATE_TEST_EVENT:String = "updateTestEvent";
		public var testXML:XML;
		public var callbackResult:Function;
		public var callbackFault:Function;
		
		public function UpdateTestEvent(testXML:XML, callbackResult:Function, callbackFault:Function, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(UPDATE_TEST_EVENT,bubbles,cancelable);
			this.testXML = testXML;
			this.callbackResult = callbackResult;
			this.callbackFault = callbackFault;
		}
	}
}