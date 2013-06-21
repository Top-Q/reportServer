package il.co.topq.systems.report.events.test
{
	import il.co.topq.systems.report.commands.general.GetTestQuerySizeEvent;
	import il.co.topq.systems.report.models.valueobjects.TestGridModel;
	
	public class GetTestSizeEvent extends GetTestQuerySizeEvent 
	{
		public static var GET_TEST_SIZE_EVENT:String = "getTestSizeEventType";		
		private var eventServiceLocator:String = "getTestsSize";
		
		public function GetTestSizeEvent(testGridModel:TestGridModel, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(GET_TEST_SIZE_EVENT, testGridModel, eventServiceLocator , bubbles,cancelable);
		}
	}
}
