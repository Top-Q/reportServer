package il.co.topq.systems.report.business.scenario
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;
	
	public class GetScenariosByFilterSizeEventDelegate
	{
		private var responder:IResponder;
		
		public function GetScenariosByFilterSizeEventDelegate(responder : IResponder){			
			this.responder = responder;			
		}
		
		public function getScenariosByFilterSize(xml:XML) : void
		{  
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("getScenariosByFilterSize");
			service.request = xml;		
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
	}
}
