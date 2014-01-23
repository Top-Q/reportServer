package il.co.topq.systems.report.business.scenario
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import il.co.topq.systems.report.models.valueobjects.Filter;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;
	import il.co.topq.systems.report.services.ReportServiceLocator;
	public class GetScenarioTestsSizeEventDelegate
	{
		private var responder:IResponder;
		
		public function GetScenarioTestsSizeEventDelegate(responder : IResponder){
			this.responder = responder;		
		}
		
		public function getScenarioTestsSize(id: String) : void{
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("getScenariosTestsSize");
			service.url += id;  
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
	}
}
