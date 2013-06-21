package il.co.topq.systems.report.business.test
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import flash.system.System;
	import flash.utils.flash_proxy;
	
	import il.co.topq.systems.report.models.valueobjects.Filter;
	import il.co.topq.systems.report.models.valueobjects.TestGridModel;
	import il.co.topq.systems.report.models.valueobjects.TestQuery;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;
	
	public class GetTestSizeEventDelegate
	{
		private var responder:IResponder;
		
		public function GetTestSizeEventDelegate(responder : IResponder){
			this.responder = responder;		
		}
		public function getTestSize(testQuery:TestQuery) : void{
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("getTestsSize");			
			service.request = testQuery.toXML();
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
	}
}