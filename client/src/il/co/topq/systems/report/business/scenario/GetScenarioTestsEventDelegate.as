package il.co.topq.systems.report.business.scenario
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import il.co.topq.systems.report.models.valueobjects.TestQuery;
	import il.co.topq.systems.report.services.ReportServiceLocator;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;
	
	public class GetScenarioTestsEventDelegate
	{
		private var responder:IResponder;
		
		public function GetScenarioTestsEventDelegate(responder : IResponder){
			this.responder = responder;		
		}
		public function getScenarioTests(id:String,testQuery:TestQuery):void{
			var service:HTTPService = ServiceLocator.getInstance()
				.getHTTPService("getScenariosTests");	
			 
			service.url = ReportServiceLocator.domain + "/report-service/report/scenario/tests/" + id;
			service.request = testQuery.toXML();
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
	}
}