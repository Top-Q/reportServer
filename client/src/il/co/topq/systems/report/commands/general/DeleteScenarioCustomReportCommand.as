package il.co.topq.systems.report.commands.general
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import flash.profiler.showRedrawRegions;
	
	import il.co.topq.systems.report.business.scenario.DeleteScenarioDelegate;
	import il.co.topq.systems.report.business.scenarioCustomReport.DeleteScenarioCustomReportDelegate;
	import il.co.topq.systems.report.events.general.DeleteScenarioCustomReportEvent;
	import il.co.topq.systems.report.events.general.DeleteScenarioEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	import il.co.topq.systems.report.views.components.reports.CustomReports;
	import il.co.topq.systems.report.views.components.reports.ExecutionReport;
	
	import mx.controls.Alert;
	import mx.rpc.IResponder;
	import mx.rpc.Responder;
	import mx.rpc.events.FaultEvent;
	
	public class DeleteScenarioCustomReportCommand implements ICommand
	{
		private var model: ReportModelLocator = ReportModelLocator.getInstance();
		
		public function execute(event:CairngormEvent): void
		{
			var evt:DeleteScenarioCustomReportEvent = event as DeleteScenarioCustomReportEvent;
			var responder:Responder = new Responder(evt.callbackResult, evt.callbackFault);
			var deleteScenarioCustomReportDelegate:DeleteScenarioCustomReportDelegate = new DeleteScenarioCustomReportDelegate(responder);
			deleteScenarioCustomReportDelegate.deleteScenarioCustomReport(evt.scenarioCustomReportID);			
		}
	}
}