package il.co.topq.systems.report.commands.scenarioCustomReport
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.scenarioCustomReport.ExportScenarioCustomReportDelegate;
	import il.co.topq.systems.report.business.testCustomReport.ExportTestCustomReportDelegate;
	import il.co.topq.systems.report.events.scenarioCustomReport.ExportScenarioCustomReportEvent;
	import il.co.topq.systems.report.events.testCustomReport.ExportTestCustomReportEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	

	public class ExportScenarioCustomReportCommand implements ICommand
	{
		private var model: ReportModelLocator = ReportModelLocator.getInstance();
		
		public function execute(event:CairngormEvent): void
		{
			var evt:ExportScenarioCustomReportEvent = event as ExportScenarioCustomReportEvent;
			var exportScenarioCustomReportDelegate:ExportScenarioCustomReportDelegate = new ExportScenarioCustomReportDelegate();
			exportScenarioCustomReportDelegate.exportScenarioCustomReport(evt.fileName,evt.fileType,evt.scenarioQuery, evt.scenarioCustomReportID);
		}
	}
}