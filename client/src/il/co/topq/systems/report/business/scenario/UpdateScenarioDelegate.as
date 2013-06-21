package il.co.topq.systems.report.business.scenario
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;
	
	public class UpdateScenarioDelegate
	{
		private var responder:IResponder;
		
		public function UpdateScenarioDelegate(responder : IResponder){
			this.responder = responder;
		}
		
		public function updateScenario(scenario:XML):void {
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("updateScenario");
			service.request = scenario;		
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
	}
}