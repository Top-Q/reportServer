package il.co.topq.systems.report.business.test
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;

	public class UpdateTestDelegate
	{
		private var responder:IResponder;
		
		public function UpdateTestDelegate(responder : IResponder){
			this.responder = responder;
		}
		
		public function updateTest(test:XML):void {
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("updateTest");
			service.request = test;		
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
	}
}