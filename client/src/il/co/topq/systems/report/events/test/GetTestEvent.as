package il.co.topq.systems.report.events.test
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.models.valueobjects.TestGridModel;
	import il.co.topq.systems.report.models.valueobjects.TestQuery;
	
	public class GetTestEvent  extends CairngormEvent
	{
		public static var GET_TEST_EVENT:String = "getTestEventType";
		public var callbackResult:Function;
		public var callbackFault:Function;
		public var testQuery:TestQuery;
		
		public function GetTestEvent(testQuery:TestQuery, callbackResult:Function, callbackFault:Function, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(GET_TEST_EVENT,bubbles,cancelable);
			this.callbackFault = callbackFault;
			this.callbackResult = callbackResult;
			this.testQuery = testQuery;
		}
	}
}