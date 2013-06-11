package il.co.topq.systems.report.commands.general
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.testCustomReport.DeleteTestCustomReportDelegate;
	import il.co.topq.systems.report.events.general.DeleteTestCustomReportEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	
	import mx.controls.Alert;
	import mx.rpc.IResponder;
	import mx.rpc.Responder;
	
	public class DeleteTestCustomReportCommand implements ICommand
	{
		private var model: ReportModelLocator = ReportModelLocator.getInstance();
		
		public function execute(event:CairngormEvent): void
		{
			var evt:DeleteTestCustomReportEvent = event as DeleteTestCustomReportEvent;
			var responder:Responder = new Responder(evt.callbackResult, evt.callbackFault);
			var deleteTestCustomReportDelegate:DeleteTestCustomReportDelegate = new DeleteTestCustomReportDelegate(responder);
			deleteTestCustomReportDelegate.deleteTestCustomReport(evt.testCustomReportID);			
		}
	}
}