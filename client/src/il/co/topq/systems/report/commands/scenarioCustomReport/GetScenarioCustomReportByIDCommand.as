package il.co.topq.systems.report.commands.scenarioCustomReport
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.scenarioCustomReport.GetScenarioCustomReportByIDEventDelegate;
	import il.co.topq.systems.report.business.testCustomReport.GetTestCustomReportByIDEventDelegate;
	import il.co.topq.systems.report.events.scenarioCustomReport.GetScenarioCustomReportByIDEvent;
	import il.co.topq.systems.report.events.testCustomReport.GetTestCustomReportByIDEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	import il.co.topq.systems.report.models.valueobjects.CustomReportVO;
	
	import mx.controls.Alert;
	import mx.rpc.IResponder;
	
	public class GetScenarioCustomReportByIDCommand implements ICommand, IResponder
	{
		private var model: ReportModelLocator = ReportModelLocator.getInstance();
		
		public function execute(event:CairngormEvent): void
		{
			var evt:GetScenarioCustomReportByIDEvent = event as GetScenarioCustomReportByIDEvent;
			var getScenarioCustomReportByIDEventDelegate:GetScenarioCustomReportByIDEventDelegate 
			= new GetScenarioCustomReportByIDEventDelegate(this);			
			
			getScenarioCustomReportByIDEventDelegate.getScenarioCustomReportByID(evt.id);				
		}
		
		public function result(data:Object): void
		{		
			model.customReport = new XML(data.message.body);	
		}
		
		public function fault(info:Object): void
		{
			Alert.show("GetScenarioCustomReportByIDCommand service was not initialized correctly");
		}
		
	}
}