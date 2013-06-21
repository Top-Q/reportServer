package il.co.topq.systems.report.business.summary
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import il.co.topq.systems.report.models.ReportModelLocator;
	import il.co.topq.systems.report.models.valueobjects.SummaryDataVO;
	
	import mx.controls.Alert;
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.http.HTTPService;

	public class GetTotalNumberOfScenarioDelegate  implements  IResponder
	{
		private var responder:IResponder;
		private var model: ReportModelLocator = ReportModelLocator.getInstance();
		
		public function GetTotalNumberOfScenarioDelegate(){
			this.responder = this;
		}
		
		public function getTotalNumberOfScenarios(scenarioQuery:XML):void{
			
			if (scenarioQuery != null) {
				var service:HTTPService = ServiceLocator.getInstance().getHTTPService("getScenariosByFilterSize");
				service.request = scenarioQuery;		
				var token:AsyncToken = service.send();
				token.addResponder(responder) ;
			}
		}
		
		public function result(data:Object): void
		{
			var xml:XML = new XML(data.message.body);
			model.summaryDataVO.totalScenarios = xml.toString();
		}
		
		public function fault(info:Object): void
		{
			var event:FaultEvent = info as FaultEvent;
			model.error.setMessage(event,"Cannot load the total number of Scenarios");
			model.applicationState.currentState = "ErrorPage";	
		}
		
	}
}