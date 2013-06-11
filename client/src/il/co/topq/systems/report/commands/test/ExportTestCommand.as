package il.co.topq.systems.report.commands.test
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.test.ExportTestDelegate;
	import il.co.topq.systems.report.events.test.ExportTestReportEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;

	public class ExportTestCommand implements ICommand
	{
		private var model: ReportModelLocator = ReportModelLocator.getInstance();
		
		public function execute(event:CairngormEvent): void
		{
			var evt:ExportTestReportEvent = event as ExportTestReportEvent;			
			var exportTestDelegate:ExportTestDelegate = new ExportTestDelegate();
			exportTestDelegate.exportTest(evt.fileName,evt.fileType,evt.testQuery);
		}
	}
}

