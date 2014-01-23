package il.co.topq.systems.report.business.test
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import il.co.topq.systems.report.services.ReportServiceLocator;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;
	
	public class GetTestByIDDelegate
	{
		private var responder:IResponder;

		public function GetTestByIDDelegate(responder : IResponder){
			this.responder = responder;
		}
		public function getTest(testID: String) : void
		{  			
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("getTestByID");
			service.url += testID;  
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
	}
}
