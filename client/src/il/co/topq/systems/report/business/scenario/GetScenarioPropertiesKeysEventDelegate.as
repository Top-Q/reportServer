package il.co.topq.systems.report.business.scenario
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;	
	
	public class GetScenarioPropertiesKeysEventDelegate
	{
		private var responder:IResponder;
		
		public function GetScenarioPropertiesKeysEventDelegate(responder : IResponder)
		{
			this.responder = responder;		
		}
		
		public function getScenarioProperties() : void{
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("getScenariosPropertiesValues");			
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}	

	}
}