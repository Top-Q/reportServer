package il.co.topq.systems.report.commands.scenario
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.scenario.ExportScenarioDetailsDelegate;
	import il.co.topq.systems.report.events.scenario.ExportScenarioDetailsEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	
	public class ExportScenarioDetailsCommand implements ICommand
	{
		private var model: ReportModelLocator = ReportModelLocator.getInstance();
		private var exportScenarioDetailsDelegate:ExportScenarioDetailsDelegate;
		
		public function execute(event:CairngormEvent): void
		{
			var evt:ExportScenarioDetailsEvent = event as ExportScenarioDetailsEvent;			
			exportScenarioDetailsDelegate = new ExportScenarioDetailsDelegate();
			exportScenarioDetailsDelegate.exportScenarioDetails(evt.fileName,evt.fileType,evt.scenarioID); 
		}
	}
}