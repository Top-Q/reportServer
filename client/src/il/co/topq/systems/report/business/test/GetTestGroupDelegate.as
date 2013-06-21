package il.co.topq.systems.report.business.test
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;
	import il.co.topq.systems.report.models.valueobjects.TestQuery;

	public class GetTestGroupDelegate
	{
		private var responder : IResponder;
		
		public function GetTestGroupDelegate(responder:IResponder)
		{
			this.responder = responder;
		}
		
		public function getTestGroup(testQuery:TestQuery):void {
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("getTestGroup");
			service.request = testQuery.toXML();
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
	}
}