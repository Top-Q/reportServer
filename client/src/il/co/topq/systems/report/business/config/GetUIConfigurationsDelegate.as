package il.co.topq.systems.report.business.config
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.Responder;
	import mx.rpc.http.HTTPService;
	
	public class GetUIConfigurationsDelegate
	{
		private var responder:IResponder;
		
		public function GetUIConfigurationsDelegate(responder:Responder)
		{
			this.responder = responder;
		}
		
		public function getSystemSettings():void{
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("getUIConfigurations");
			service.request = new Date();//used to break the chaching of the get request.
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
	}
}