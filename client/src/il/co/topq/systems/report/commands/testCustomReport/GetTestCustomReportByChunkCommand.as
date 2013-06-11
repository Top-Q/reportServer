package il.co.topq.systems.report.commands.testCustomReport
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.testCustomReport.GetTestCustomReportEventDelegate;
	import il.co.topq.systems.report.events.testCustomReport.GetTestCustomReportByChunkEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	
	import mx.rpc.Responder;
	

	public class GetTestCustomReportByChunkCommand implements ICommand
	{		

		private var model: ReportModelLocator = ReportModelLocator.getInstance();		
		
		public function execute(event:CairngormEvent): void
		{
			var evt:GetTestCustomReportByChunkEvent = event as GetTestCustomReportByChunkEvent;
			var responder:Responder = new Responder(evt.callbackResult, evt.callbackFault);
			var getTestCustomReportEventDelegate:GetTestCustomReportEventDelegate = new GetTestCustomReportEventDelegate(responder);			
			getTestCustomReportEventDelegate.getCustomReport(evt.testGridModel);				
		}
	}
}