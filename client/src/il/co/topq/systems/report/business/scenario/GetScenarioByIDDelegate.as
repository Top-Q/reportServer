package il.co.topq.systems.report.business.scenario
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import il.co.topq.systems.report.models.ReportModelLocator;
	import il.co.topq.systems.report.services.ReportServiceLocator;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;
	
	public class GetScenarioByIDDelegate
	{
		private var responder:IResponder;
		
		public function GetScenarioByIDDelegate(responder : IResponder){
			this.responder = responder;
		}
		
		public function getScenario(scenarioID: String) : void
		{  			
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("getScenarioByID");
			service.url = ReportServiceLocator.domain + "/report-service/report/scenario/"  + scenarioID;  
			service.request = new Date();//used to break the chaching of the get request.
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
	}
}
