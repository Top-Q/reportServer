package il.co.topq.systems.report.business.scenarioCustomReport
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import il.co.topq.systems.report.services.ReportServiceLocator;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;
	
	public class GetScenarioCustomReportByIDEventDelegate
	{
		private var responder:IResponder;
		
		public function GetScenarioCustomReportByIDEventDelegate(responder : IResponder)
		{
			this.responder = responder;
		}
		public function getScenarioCustomReportByID(id: String):void{
			var service:HTTPService = ServiceLocator.getInstance()
				.getHTTPService("getScenarioCustomReportByID");
			service.url = ReportServiceLocator.domain + "/report-service/report/scenarioCustomReport/"  + id;  
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
	}
}