package il.co.topq.systems.report.business.testCustomReport
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import il.co.topq.systems.report.services.ReportServiceLocator;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;
	
	public class GetTestCustomReportPropertiesValuesEventDelegate
	{
		private var responder:IResponder;
		
		public function GetTestCustomReportPropertiesValuesEventDelegate(responder : IResponder)
		{
			this.responder = responder;
		}
		
		public function getTestCustomReportPropertiesValues(id : String):void{
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("getTestCustomReportPropertiesValues");
			
			service.url = ReportServiceLocator.domain + "/report-service/report/testCustomReport/values/"  + id;  
			service.request = new Date(); //will prevent caching the request;
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
		
	}
}