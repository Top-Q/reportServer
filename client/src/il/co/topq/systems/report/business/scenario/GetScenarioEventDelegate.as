package il.co.topq.systems.report.business.scenario
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import il.co.topq.systems.report.models.valueobjects.ScenarioQuery;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;

	public class GetScenarioEventDelegate
	{
		private var responder:IResponder;
		
		public function GetScenarioEventDelegate(responder : IResponder){
			this.responder = responder;
		}
		
		public function getScenarios(scenarioQuery:ScenarioQuery) : void
		{  
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("getScenarios");
			service.request = scenarioQuery.toXML();
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
	}
}