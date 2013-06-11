package il.co.topq.systems.report.events.testCustomReport
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.models.valueobjects.TestQuery;
	
	import org.as3commons.collections.utils.NullComparator;

	public class ExportTestCustomReportEvent extends CairngormEvent
	{
		public static var EXPORT_TEST_CUSTOM_REPORT_EVENT:String = "ExportTestCustomReport";	
		
		public var fileType:String;
		public var fileName:String;
		public var testQuery:TestQuery;
		public var testCustomReportID:String;
		
		public function ExportTestCustomReportEvent(fileName:String, fileType:String, testQuery:TestQuery,testCustomReportID:String, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(EXPORT_TEST_CUSTOM_REPORT_EVENT,bubbles,cancelable);
			this.fileType = fileType;
			this.fileName = fileName;
			this.testQuery = testQuery;
			this.testCustomReportID = testCustomReportID;
		}
	}
}
