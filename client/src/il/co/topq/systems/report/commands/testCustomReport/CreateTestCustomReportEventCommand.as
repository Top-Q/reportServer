package il.co.topq.systems.report.commands.testCustomReport
{	
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.testCustomReport.CreateTestCustomReportEventDelegate;
	import il.co.topq.systems.report.events.testCustomReport.CreateTestCustomReportEvent;
	import il.co.topq.systems.report.events.testCustomReport.GetTestCustomReportByChunkEvent;
	import il.co.topq.systems.report.events.testCustomReport.GetTestCustomReportSizeEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	import il.co.topq.systems.report.models.valueobjects.Chunk;
	import il.co.topq.systems.report.models.valueobjects.Filter;
	
	import mx.controls.Alert;
	import mx.rpc.IResponder;
	import mx.rpc.Responder;
	import mx.rpc.events.ResultEvent;
	
	public class CreateTestCustomReportEventCommand implements ICommand
	{
		private var model: ReportModelLocator = ReportModelLocator.getInstance();	
		
		public function execute(event:CairngormEvent): void
		{ 
			var evt:CreateTestCustomReportEvent = event as CreateTestCustomReportEvent;
			var responder:Responder = new Responder(evt.callbackResult,evt.callbackFault);
			var createTestCustomReportEventDelegate:CreateTestCustomReportEventDelegate = new CreateTestCustomReportEventDelegate(responder);			
			createTestCustomReportEventDelegate.createCustomReport();		
		}
	}
}