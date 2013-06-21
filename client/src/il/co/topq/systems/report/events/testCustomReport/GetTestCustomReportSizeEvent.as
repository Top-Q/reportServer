package il.co.topq.systems.report.events.testCustomReport
{
	import il.co.topq.systems.report.commands.general.GetTestQuerySizeEvent;
	import il.co.topq.systems.report.models.valueobjects.TestGridModel;
	
	public class GetTestCustomReportSizeEvent extends GetTestQuerySizeEvent
	{
		public static var GET_TEST_CUSTOM_REPORT_SIZE_EVENT:String = "GetTestCustomReportSizeEventType";		
		private var eventServiceLocator:String = "getTestCustomReportSize";
		
		public function GetTestCustomReportSizeEvent(testGridModel:TestGridModel, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(GET_TEST_CUSTOM_REPORT_SIZE_EVENT,testGridModel,eventServiceLocator,bubbles, cancelable);
		}
	}
}
