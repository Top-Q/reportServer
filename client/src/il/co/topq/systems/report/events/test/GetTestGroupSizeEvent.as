package il.co.topq.systems.report.events.test
{
	import il.co.topq.systems.report.commands.general.GetTestQuerySizeEvent;
	import il.co.topq.systems.report.models.valueobjects.TestGridModel;

	public class GetTestGroupSizeEvent extends GetTestQuerySizeEvent
	{
		public static var GET_TEST_GROUP_SIZE_EVENT:String = "getTestGroupSizeEvent";
		private var eventServiceLocator:String = "getTestGroupSize";
		
		public function GetTestGroupSizeEvent(testGridModel:TestGridModel, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(GET_TEST_GROUP_SIZE_EVENT, testGridModel, eventServiceLocator , bubbles,cancelable);
		}
	}
}