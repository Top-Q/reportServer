package il.co.topq.systems.report.business.test
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import il.co.topq.systems.report.models.valueobjects.Filter;
	import il.co.topq.systems.report.models.valueobjects.TestQuery;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;
	

	public class GetTestDelegate
	{
		private var responder:IResponder;
		
		public function GetTestDelegate(responder : IResponder){
			this.responder = responder;
		}
		
		public function getTests(testQuery:TestQuery) : void
		{  
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("getTests");
			service.request = testQuery.toXML();
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
		
		
	}
}