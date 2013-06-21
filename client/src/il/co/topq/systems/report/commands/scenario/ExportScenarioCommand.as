package il.co.topq.systems.report.commands.scenario
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.scenario.ExportScenarioDelegate;
	import il.co.topq.systems.report.events.scenario.ExportScenarioReportEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	
	public class ExportScenarioCommand implements ICommand
	{
		private var model: ReportModelLocator = ReportModelLocator.getInstance();
		private var exportScenarioDelegate:ExportScenarioDelegate;
		
		public function execute(event:CairngormEvent): void
		{
			var evt:ExportScenarioReportEvent = event as ExportScenarioReportEvent;			
			exportScenarioDelegate = new ExportScenarioDelegate();
			exportScenarioDelegate.exportScenario(evt.fileName,evt.fileType,evt.scenarioQuery); 
		}
	
	}
}
