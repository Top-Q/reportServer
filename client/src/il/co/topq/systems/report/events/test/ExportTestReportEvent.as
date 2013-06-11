package il.co.topq.systems.report.events.test
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.models.valueobjects.TestQuery;
	
	public class ExportTestReportEvent extends CairngormEvent
	{
		
		public static var EXPORT_TEST_REPORT_EVENT:String = "ExportTestReport";	
		
		public var fileType:String;
		public var fileName:String;
		public var testQuery:TestQuery;
		
		public function ExportTestReportEvent(fileName:String, fileType:String, testQuery:TestQuery, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(EXPORT_TEST_REPORT_EVENT,bubbles,cancelable);
			this.fileType = fileType;
			this.fileName = fileName;
			this.testQuery = testQuery;
		}
	}
}
