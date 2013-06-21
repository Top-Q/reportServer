package il.co.topq.systems.report.business.test
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import il.co.topq.systems.report.models.valueobjects.TestSummaryQuery;
	import il.co.topq.systems.report.services.ReportServiceLocator;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;

	public class GetGroupTestsDelegate
	{
		public var responder:IResponder;
		
		public function GetGroupTestsDelegate(responder:IResponder)
		{
			this.responder = responder;
		}
		
		public function getGroupTests(testSummaryQuery:TestSummaryQuery):void {
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("getGroupTests");
			service.request = testSummaryQuery.toXML();
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
	}
}