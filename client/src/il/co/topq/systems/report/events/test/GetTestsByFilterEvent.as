package il.co.topq.systems.report.events.test
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.models.valueobjects.TestCustomReportGridModel;
	
	public class GetTestsByFilterEvent extends CairngormEvent
	{
		public static var GET_TESTS_BY_FILTER_EVENT:String = "GetTestsByFilterEvent";
		public var xml:XML;
		public var testCustomReportGridModel:TestCustomReportGridModel;
		
		public function GetTestsByFilterEvent(xml: XML, testCustomReportGridModel:TestCustomReportGridModel, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(GET_TESTS_BY_FILTER_EVENT, bubbles,cancelable);		
			this.xml = xml;
			this.testCustomReportGridModel = testCustomReportGridModel;
		}
	}
}	