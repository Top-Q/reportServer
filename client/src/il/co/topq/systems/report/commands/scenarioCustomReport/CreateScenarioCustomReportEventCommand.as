package il.co.topq.systems.report.commands.scenarioCustomReport
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.scenarioCustomReport.CreateScenarioCustomReportEventDelegate;
	import il.co.topq.systems.report.events.scenarioCustomReport.CreateScenarioCustomReportEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	import il.co.topq.systems.report.models.valueobjects.Chunk;
	import il.co.topq.systems.report.models.valueobjects.Filter;
	
	import mx.controls.Alert;
	import mx.rpc.IResponder;
	import mx.rpc.Responder;
	import mx.rpc.events.ResultEvent;
	
	public class CreateScenarioCustomReportEventCommand implements ICommand
	{
		private var model: ReportModelLocator = ReportModelLocator.getInstance();	
		
		public function execute(event:CairngormEvent): void
		{
			var evt:CreateScenarioCustomReportEvent = event as CreateScenarioCustomReportEvent;
			var responder:Responder = new Responder(evt.callbackResult,evt.callbackFault);
			var createScenarioCustomReportEventDelegate:CreateScenarioCustomReportEventDelegate = new CreateScenarioCustomReportEventDelegate(responder);			
			
			createScenarioCustomReportEventDelegate.createCustomReport();		
		}
	}
}