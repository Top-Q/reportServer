package il.co.topq.systems.report.business.test
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import flash.system.System;
	import flash.utils.flash_proxy;
	
	import il.co.topq.systems.report.models.valueobjects.Filter;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;
	
	public class GetTestPropertiesKeysEventDelegate
	{
		private var responder:IResponder;
		
		public function GetTestPropertiesKeysEventDelegate(responder : IResponder)
		{
			this.responder = responder;		
		}
		
		public function getTestsProperties() : void{
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("getTestsPropertiesValues");			
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}	
	}
}