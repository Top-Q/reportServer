package il.co.topq.systems.report.business.general
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;

	public class GetQuerySizeDelegate
	{
		private var responder :IResponder;
		public function GetQuerySizeDelegate(responder:IResponder)
		{
			this.responder = responder;
		}
		public function getSizeOfQuery(query:XML, serviceLocator:String):void {
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService(serviceLocator);
			service.request = query;
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
	}
}