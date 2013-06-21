package il.co.topq.systems.report.business.mock
{
	import il.co.topq.systems.report.models.ReportModelLocator;
	import il.co.topq.systems.report.models.valueobjects.SummaryDataVO;
	
	import mx.controls.Alert;
	import mx.rpc.IResponder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;

	public class GetTotalScenarioDelegateMock  implements  IResponder
	{
		private var responder:IResponder;
		private var model: ReportModelLocator = ReportModelLocator.getInstance();
		
		public function GetTotalScenarioDelegateMock(){
			this.responder = this;
		}
		
		public function getTotalNumberOfScenarios():void{
			responder.result(97);
		}
		
		public function result(data:Object): void
		{
			model.summaryDataVO.totalScenarios = data as Number;
		}
		
		public function fault(info:Object): void
		{
			var event:FaultEvent = info as FaultEvent;
			model.error.setMessage(event);
			model.applicationState.currentState = "ErrorPage";	
		}
		
	}
}