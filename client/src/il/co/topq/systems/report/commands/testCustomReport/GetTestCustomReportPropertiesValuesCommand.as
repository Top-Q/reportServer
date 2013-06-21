package il.co.topq.systems.report.commands.testCustomReport
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.testCustomReport.GetTestCustomReportPropertiesValuesEventDelegate;
	import il.co.topq.systems.report.events.testCustomReport.GetTestCustomReportPropertiesValuesEvent;
	
	import mx.rpc.Responder;
	
	public class GetTestCustomReportPropertiesValuesCommand implements ICommand	{
		private var testCustomReportID:String;
		public function execute(event:CairngormEvent): void
		{			
			var evt:GetTestCustomReportPropertiesValuesEvent = event as GetTestCustomReportPropertiesValuesEvent;
			var responder:Responder = new Responder(evt.callbackResult, evt.callbackFault);
			this.testCustomReportID = evt.testCustomReportID ;
			
			var getTestCustomReportPropertiesValuesEventDelegate:GetTestCustomReportPropertiesValuesEventDelegate 
			= new GetTestCustomReportPropertiesValuesEventDelegate(responder);			
			
			getTestCustomReportPropertiesValuesEventDelegate.getTestCustomReportPropertiesValues(evt.testCustomReportID);				
		}
	}
}