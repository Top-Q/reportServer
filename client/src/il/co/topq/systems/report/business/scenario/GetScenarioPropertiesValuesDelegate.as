package il.co.topq.systems.report.business.scenario
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import il.co.topq.systems.report.models.ReportModelLocator;
	import il.co.topq.systems.report.services.ReportServiceLocator;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;
	
	public class GetScenarioPropertiesValuesDelegate
	{
		private var responder:IResponder;		
		public var model:ReportModelLocator = ReportModelLocator.getInstance();
		
		public function GetScenarioPropertiesValuesDelegate(responder : IResponder){
			this.responder = responder;
		}
		public function getScenarioPropertiesValues(scenarioProperties:String):void{
			if (scenarioProperties != null && scenarioProperties.length > 0){
				var service:HTTPService = ServiceLocator.getInstance().getHTTPService("getScenarioPropertiesValues");
				service.url += scenarioProperties;  
				var token:AsyncToken = service.send();
				token.addResponder(responder) ;
			}
		}
	}
}