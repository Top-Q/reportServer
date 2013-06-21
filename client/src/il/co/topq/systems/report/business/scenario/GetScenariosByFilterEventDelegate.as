package il.co.topq.systems.report.business.scenario
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;
	
	public class GetScenariosByFilterEventDelegate
	{
		private var responder:IResponder;
		
		public function GetScenariosByFilterEventDelegate(responder : IResponder){			
			this.responder = responder;			
		}
		
		public function getScenarios(xml:XML) : void
		{  
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("getScenariosByFilter");
			service.request = xml;		
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
	}
}
