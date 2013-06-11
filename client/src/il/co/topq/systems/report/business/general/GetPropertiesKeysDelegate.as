package il.co.topq.systems.report.business.general
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;

	public class GetPropertiesKeysDelegate
	{
		public var responder:IResponder;
		
		public function GetPropertiesKeysDelegate(responder:IResponder)
		{
			this.responder = responder;
		}
		
		public function getPropertiesKeys(serviceLocator:String) :void{
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService(serviceLocator);	
			service.request = new Date();//used to break the chaching of the get request.
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
	}
}