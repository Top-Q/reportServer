package il.co.topq.systems.report.business.scenarioCustomReport
{
	
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import il.co.topq.systems.report.models.valueobjects.ScenarioGridModel;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;
	
	public class GetScenarioCustomReportEventDelegate
	{
		private var responder:IResponder;
		
		
		public function GetScenarioCustomReportEventDelegate(responder : IResponder)
		{
			this.responder = responder;
		}
		
		public function GetScenarioCustomReport(scenarioGridModel:ScenarioGridModel) : void
		{  			
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("getScenarioCustomReport");
			service.request = scenarioGridModel.scenarioQuery.toXML();
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
		
	}
}