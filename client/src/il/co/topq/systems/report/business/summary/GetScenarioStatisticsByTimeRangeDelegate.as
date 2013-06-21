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
	
	public class GetScenarioStatisticsByTimeRangeDelegate  implements  IResponder
	{
		private var responder:IResponder;
		private var model: ReportModelLocator = ReportModelLocator.getInstance();
		
		public function GetScenarioStatisticsByTimeRangeDelegate(){
			this.responder = this;
		}
		
		public function getScenarioStatisticsByTimeRange(scenarioQuery:XML):void{
			if (scenarioQuery != null){
				
				var service:HTTPService = ServiceLocator.getInstance().getHTTPService("getScenariosByFilter");
				service.headers["Pragma"] = "no-cache"; 
				service.headers["Cache-Control"] = "no-cache"; 
				service.request = scenarioQuery;;		
				var token:AsyncToken = service.send();
				token.addResponder(responder) ;
			}
		}
		
		public function result(data:Object): void
		{
			var scenarioStatisticsByTimeRange:XML = new XML(data.message.body);
			model.summaryDataVO.setExecutionStatisticsByTimeRange(scenarioStatisticsByTimeRange);
		}
		
		public function fault(info:Object): void
		{
			var event:FaultEvent = info as FaultEvent;
			model.error.setMessage(event,"Cannot load the total number of Tests");
			model.applicationState.currentState = "ErrorPage";	
		}
		
	}
}