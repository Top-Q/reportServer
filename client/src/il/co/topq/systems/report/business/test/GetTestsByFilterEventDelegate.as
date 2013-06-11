package il.co.topq.systems.report.business.test
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;
	
	public class GetTestsByFilterEventDelegate
	{
		private var responder:IResponder;
		
		public function GetTestsByFilterEventDelegate(responder : IResponder){			
			this.responder = responder;			
		}
		
		public function getTests(xml:XML) : void
		{  
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("getTestsByFilter");
			service.request = xml;		
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
	}
}
