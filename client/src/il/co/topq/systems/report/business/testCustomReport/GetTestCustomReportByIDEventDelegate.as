package il.co.topq.systems.report.business.testCustomReport
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import il.co.topq.systems.report.services.ReportServiceLocator;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;
	
	public class GetTestCustomReportByIDEventDelegate
	{
		private var responder:IResponder;
		
		public function GetTestCustomReportByIDEventDelegate(responder : IResponder)
		{
			this.responder = responder;
		}
		public function getTestCustomReportByID(id: String):void{
			var service:HTTPService = ServiceLocator.getInstance()
				.getHTTPService("getTestCustomReportByID");
			service.url = ReportServiceLocator.domain + "/report-service/report/testCustomReport/"  + id;  
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
	}
}