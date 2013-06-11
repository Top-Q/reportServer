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
	
	public class GetLastExecutionVersionDelegate implements  IResponder
	{
		private var responder:IResponder;
		private var model: ReportModelLocator = ReportModelLocator.getInstance();
		
		public function GetLastExecutionVersionDelegate(){
			this.responder = this;
		}
		
		public function getLastExecutionVersion():void{
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("lastVersionExecution");
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
		
		
		public function result(data:Object): void
		{
			var xml:XML = new XML(data.message.body);
			if(xml.toString() == 0.0){
				model.summaryDataVO.lastVersionExecution = "No version was found";
			}
			else{
				model.summaryDataVO.lastVersionExecution = xml.toString();	
			}
		}
		
		public function fault(info:Object): void
		{
			var event:FaultEvent = FaultEvent(info);
			model.error.setMessage(event);
			model.applicationState.currentState = "ErrorPage";	
		}

	}
}