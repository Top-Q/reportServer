package il.co.topq.systems.report.commands.scenarioCustomReport
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.scenarioCustomReport.GetScenarioCustomReportEventDelegate;
	import il.co.topq.systems.report.events.scenarioCustomReport.GetScenarioCustomReportByChunkEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	
	import mx.controls.Alert;
	import mx.rpc.IResponder;
	import mx.rpc.Responder;
	import mx.rpc.events.ResultEvent;
	
	public class GetScenarioCustomReportCommand implements ICommand
	{
		private var model: ReportModelLocator = ReportModelLocator.getInstance();		
		
		public function execute(event:CairngormEvent): void
		{
			var evt:GetScenarioCustomReportByChunkEvent = event as GetScenarioCustomReportByChunkEvent;
			var responder:Responder = new Responder (evt.callbackResult, evt.callbackFault);
			var getScenarioCustomReportEventDelegate:GetScenarioCustomReportEventDelegate 
			= new GetScenarioCustomReportEventDelegate(responder);			
			
			getScenarioCustomReportEventDelegate.GetScenarioCustomReport(evt.scenarioGridModel);				
		}
	}
}