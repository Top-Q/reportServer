package il.co.topq.systems.report.events.test
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.models.valueobjects.TestSummaryQuery;

	public class GetGroupTestsEvent extends CairngormEvent
	{
		public static var GET_GROUP_TESTS_EVENT:String = "getGroupTestsEvent";
		public var callbackResult:Function;
		public var callbackFault:Function;
		public var testSummaryQuery:TestSummaryQuery;
		
		public function GetGroupTestsEvent(testSummaryQuery:TestSummaryQuery,callbackResult:Function,callbackFault:Function,bubbles:Boolean = false, cancelable:Boolean = false)
		{
			super(GET_GROUP_TESTS_EVENT,bubbles,cancelable);
			this.callbackFault = callbackFault;
			this.callbackResult = callbackResult;
			this.testSummaryQuery = testSummaryQuery;
		}
	}
}