package il.co.topq.systems.report.business.scenario
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import il.co.topq.systems.report.models.ReportModelLocator;
	import il.co.topq.systems.report.services.ReportServiceLocator;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;
	
	public class CompareScenarioDelegate
	{
		private var responder:IResponder;		
		public var model:ReportModelLocator = ReportModelLocator.getInstance();
		
		public function CompareScenarioDelegate(responder : IResponder){
			this.responder = responder;
		}
		public function compareScenarios(comparedScenarios:String):void{
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("compareScenarios");
			service.url = ReportServiceLocator.domain + "/report-service/report/scenario/compareScenarios/"  + comparedScenarios;  
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
	}
}