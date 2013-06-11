package il.co.topq.systems.report.business.testCustomReport
{	
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import il.co.topq.systems.report.models.valueobjects.Filter;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;
	
	public class GetTestCustomReportSizeEventDelegate
	{
		private var responder:IResponder;
		
		public function GetTestCustomReportSizeEventDelegate(responder : IResponder)
		{
			this.responder = responder;
		}
		public function getTestCustomReportSize(filter: Filter):void{
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("getTestCustomReportSize");
			service.request.testQuery = filter;
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
	}
}