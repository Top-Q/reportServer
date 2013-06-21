package il.co.topq.systems.report.events.test
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.models.valueobjects.TestGridModel;
	
	public class GetTestsByFilterSizeEvent extends CairngormEvent
	{
		public static var GET_TESTS_BY_FILTER_SIZE_EVENT:String = "GetTestsByFilterSizeEvent";
		public var xml:XML;
		public var testGridModel:TestGridModel;
		
		public function GetTestsByFilterSizeEvent(xml: XML, testGridModel:TestGridModel, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(GET_TESTS_BY_FILTER_SIZE_EVENT, bubbles,cancelable);		
			this.xml = xml;
			this.testGridModel = testGridModel;
		}
	}
}	