package il.co.topq.systems.report.commands.testCustomReport
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.testCustomReport.GetTestCustomReportByIDEventDelegate;
	import il.co.topq.systems.report.business.testCustomReport.GetTestCustomReportSizeEventDelegate;
	import il.co.topq.systems.report.events.testCustomReport.GetTestCustomReportByIDEvent;
	import il.co.topq.systems.report.events.testCustomReport.GetTestCustomReportSizeEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	
	import mx.controls.Alert;
	import mx.formatters.NumberFormatter;
	import mx.rpc.IResponder;
	
	public class GetTestCustomReportByIDCommand implements ICommand, IResponder
	{
		private var model: ReportModelLocator = ReportModelLocator.getInstance();
		
		public function execute(event:CairngormEvent): void
		{
			var evt:GetTestCustomReportByIDEvent = event as GetTestCustomReportByIDEvent;
			var getTestCustomReportByIDEventDelegate:GetTestCustomReportByIDEventDelegate 
			= new GetTestCustomReportByIDEventDelegate(this);			
			
			getTestCustomReportByIDEventDelegate.getTestCustomReportByID(evt.id);				
		}
		
		public function result(data:Object): void
		{		
			model.customReport = new XML(data.message.body);
			
		}
		
		public function fault(info:Object): void
		{
			Alert.show("GetTestCustomReportByIDCommand service was not initialized correctly");
		}
		
	}
}