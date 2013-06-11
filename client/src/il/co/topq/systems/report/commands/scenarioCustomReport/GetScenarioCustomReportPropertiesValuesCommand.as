package il.co.topq.systems.report.commands.scenarioCustomReport
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.scenarioCustomReport.GetScenarioCustomReportPropertiesValuesEventDelegate;
	import il.co.topq.systems.report.events.testCustomReport.GetScenarioCustomReportPropertiesValuesEvent;
	
	import mx.rpc.Responder;

	
	public class GetScenarioCustomReportPropertiesValuesCommand implements ICommand
	{
		private var scenarioCustomReportID: String;
		
		public function execute(event:CairngormEvent): void
		{
			var evt:GetScenarioCustomReportPropertiesValuesEvent = event as GetScenarioCustomReportPropertiesValuesEvent;
			this.scenarioCustomReportID = evt.scenarioCustomReportID;
			
			var responder:Responder = new Responder(evt.callbackResult, evt.callbackFault);
			
			var getScenarioCustomReportPropertiesValuesEventDelegate:GetScenarioCustomReportPropertiesValuesEventDelegate 
			= new GetScenarioCustomReportPropertiesValuesEventDelegate(responder);	
			
			getScenarioCustomReportPropertiesValuesEventDelegate.getScenarioCustomReportPropertiesValues(evt.scenarioCustomReportID);				
		}
	}
}