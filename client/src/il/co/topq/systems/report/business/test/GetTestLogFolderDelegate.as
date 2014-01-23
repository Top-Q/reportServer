package il.co.topq.systems.report.business.test
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import il.co.topq.systems.report.services.ReportServiceLocator;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;
	
	public class GetTestLogFolderDelegate
	{
		private var responder : IResponder;
		
		public function GetTestLogFolderDelegate(responder : IResponder)
		{		
			this.responder = responder;
		}
		
		public function getTestLogFolder(testId:String):void {
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("getTestLogFolder");
			service.url += testId;  
			var token:AsyncToken = service.send();
			token.addResponder(responder);
		}
	}
}