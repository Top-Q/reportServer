package il.co.topq.systems.report.events.testCustomReport
{
	import flash.events.Event;
	
	import il.co.topq.systems.report.models.valueobjects.TestCustomReportVO;

	public class TestCustomReportGridDoubleClickEvent extends Event
	{
		public static var TEST_CUSTOM_REPORT_GRID_DOUBLE_CLICK_EVENT:String = "TestCustomReportGridDoubleClickEvent";
		public var testCustomReportVO:TestCustomReportVO;
		
		public function TestCustomReportGridDoubleClickEvent(testCustomReportVO:TestCustomReportVO, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(TEST_CUSTOM_REPORT_GRID_DOUBLE_CLICK_EVENT,bubbles,cancelable);
			this.testCustomReportVO = testCustomReportVO;
		}
	}
}