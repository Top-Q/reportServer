package il.co.topq.systems.report.business.scenarioCustomReport
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import flash.events.Event;
	
	import il.co.topq.systems.report.services.ReportServiceLocator;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.http.HTTPService;
	import mx.utils.OnDemandEventDispatcher;
	
	public class GetScenarioCustomReportPropertiesValuesEventDelegate
	{
		private var responder:IResponder;
		
		public function GetScenarioCustomReportPropertiesValuesEventDelegate(responder : IResponder)
		{
			this.responder = responder;
		}
		public function getScenarioCustomReportPropertiesValues(id : String):void{
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("getScenarioCustomReportPropertiesValues");
			
			service.url = ReportServiceLocator.domain + "/report-service/report/scenarioCustomReport/values/"  + id;
			service.request = new Date();//will prevent caching the request;
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
	}
}