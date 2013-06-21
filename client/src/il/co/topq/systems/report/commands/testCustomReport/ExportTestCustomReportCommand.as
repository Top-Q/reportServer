package il.co.topq.systems.report.commands.testCustomReport
{
	import com.adobe.cairngorm.commands.ICommand;
	
	import il.co.topq.systems.report.business.testCustomReport.ExportTestCustomReportDelegate;
	import il.co.topq.systems.report.events.testCustomReport.ExportTestCustomReportEvent;

	public class ExportTestCustomReportCommand implements ICommand
	{
		import com.adobe.cairngorm.control.CairngormEvent;
		
		import il.co.topq.systems.report.business.test.ExportTestDelegate;
		import il.co.topq.systems.report.events.test.ExportTestReportEvent;
		import il.co.topq.systems.report.models.ReportModelLocator;
		
		private var model: ReportModelLocator = ReportModelLocator.getInstance();
		
		public function execute(event:CairngormEvent): void
		{
			var evt:ExportTestCustomReportEvent = event as ExportTestCustomReportEvent;			
			var exportTestCustomReportDelegate:ExportTestCustomReportDelegate = new ExportTestCustomReportDelegate();
			exportTestCustomReportDelegate.exportTestCustomReport(evt.fileName,evt.fileType,evt.testQuery, evt.testCustomReportID);
		}
	}
	
}