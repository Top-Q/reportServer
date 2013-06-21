package il.co.topq.systems.report.events.test
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.models.valueobjects.TestQuery;

	public class GetTestGroupEvent extends CairngormEvent
	{
		public static var GET_TEST_GROUP_EVENT:String = "getTestGroupEvent";
		public var callbackFault : Function;
		public var callbackResult : Function;
		public var testQuery:TestQuery;
		
		public function GetTestGroupEvent(testQuery:TestQuery, callbackResult:Function, callbackFault:Function, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(GET_TEST_GROUP_EVENT,bubbles,cancelable);
			this.callbackFault = callbackFault;
			this.callbackResult = callbackResult;
			this.testQuery = testQuery;
		}
	}
}