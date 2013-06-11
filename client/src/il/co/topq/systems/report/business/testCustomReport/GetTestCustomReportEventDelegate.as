package il.co.topq.systems.report.business.testCustomReport
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import il.co.topq.systems.report.models.valueobjects.Filter;
	import il.co.topq.systems.report.models.valueobjects.TestGridModel;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;
	
	public class GetTestCustomReportEventDelegate
	{
		private var responder:IResponder;
		
		public function GetTestCustomReportEventDelegate(responder : IResponder)
		{
			this.responder = responder;
		}
		
		public function getCustomReport(testGridModel:TestGridModel) : void
		{  			
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("getTestCustomReport");
			service.request = testGridModel.testQuery.toXML();
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
	}
}