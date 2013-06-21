package il.co.topq.systems.report.business.test
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import il.co.topq.systems.report.models.ReportModelLocator;
	import il.co.topq.systems.report.services.ReportServiceLocator;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;
	
	public class GetTestScenarioDelegate
	{
		private var responder:IResponder;
		public var model:ReportModelLocator = ReportModelLocator.getInstance();
		
		public function GetTestScenarioDelegate(responder : IResponder){
			this.responder = responder;
		}
		public function getTestScenario(testID:String) : void {
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("getTestScenario");
			service.url = ReportServiceLocator.domain + "/report-service/report/test/testScenario/"  + testID;  
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
	}
}
