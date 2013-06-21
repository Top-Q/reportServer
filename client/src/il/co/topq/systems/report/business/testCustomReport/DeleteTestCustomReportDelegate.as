package il.co.topq.systems.report.business.testCustomReport
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import il.co.topq.systems.report.models.ReportModelLocator;
	import il.co.topq.systems.report.services.ReportServiceLocator;
	
	import mx.controls.Alert;
	import mx.core.FlexGlobals;
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;
	
	public class DeleteTestCustomReportDelegate
	{
		private var responder:IResponder;
		
		public var model:ReportModelLocator = ReportModelLocator.getInstance();
		
		public function DeleteTestCustomReportDelegate(responder : IResponder)
		{
			this.responder = responder;
		}		
		
		public function deleteTestCustomReport(id: String) : void
		{  			
			var service:HTTPService = 
				ServiceLocator.getInstance().getHTTPService("deleteTestCustomReport");
			service.url = ReportServiceLocator.domain + "/report-service/report/testCustomReport/delete/"  + id;  
			service.request = new Date();//used to break the chaching of the get request.
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
	}
}