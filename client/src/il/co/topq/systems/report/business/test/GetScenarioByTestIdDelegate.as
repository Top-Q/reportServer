package il.co.topq.systems.report.business.test
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import il.co.topq.systems.report.services.ReportServiceLocator;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;
	
	public class GetScenarioByTestIdDelegate
	{
		private var responder : IResponder;
		
		public function GetScenarioByTestIdDelegate(responder : IResponder)
		{		
			this.responder = responder;
		}
		
		public function getScenarioByTestId(testId:String):void {
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("scenarioOfTest");
			service.url = ReportServiceLocator.domain + "/report-service/report/test/scenarioOfTest/"  + testId;  
			service.request = new Date();//used to break the chaching of the get request.
			var token:AsyncToken = service.send();
			token.addResponder(responder);
		}
	}
}