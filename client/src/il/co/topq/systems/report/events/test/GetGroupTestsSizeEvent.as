package il.co.topq.systems.report.events.test
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.models.valueobjects.TestGridModel;
	import il.co.topq.systems.report.models.valueobjects.TestSummaryQuery;

	public class GetGroupTestsSizeEvent extends CairngormEvent
	{
		public static var GET_GROUP_TESTS_SIZE:String = "getGroupTestsSizeEvent";
		public var serviceLocator:String = "getGroupTestsSize";
		public var testSummaryQuery:TestSummaryQuery;
		public var testGridModel:TestGridModel;
		
		public function GetGroupTestsSizeEvent(testSummaryQuery:TestSummaryQuery,testGridModel:TestGridModel, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(GET_GROUP_TESTS_SIZE,bubbles, cancelable);
			this.testSummaryQuery = testSummaryQuery;
			this.testGridModel = testGridModel;
		}
	}
}