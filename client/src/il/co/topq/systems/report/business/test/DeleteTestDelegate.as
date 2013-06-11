package il.co.topq.systems.report.business.test
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import il.co.topq.systems.report.models.ReportModelLocator;
	import il.co.topq.systems.report.services.ReportServiceLocator;
	
	import mx.controls.Alert;
	import mx.core.FlexGlobals;
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;
	
	public class DeleteTestDelegate
	{
		private var responder:IResponder;
		
		public var model:ReportModelLocator = ReportModelLocator.getInstance();
		
		
		public function DeleteTestDelegate(responder : IResponder)
		{
			this.responder = responder;
		}
		
		public function deleteTest(id: String) : void
		{  			
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("deleteTest");
			service.url = ReportServiceLocator.domain +"/report-service/report/test/delete/"  + id;  
			var token:AsyncToken = service.send();
			token.addResponder(responder);
		}
	}
}