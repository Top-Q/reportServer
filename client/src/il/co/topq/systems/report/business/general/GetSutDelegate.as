package il.co.topq.systems.report.business.general
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import il.co.topq.systems.report.models.ReportModelLocator;
	
	import mx.controls.Alert;
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.http.HTTPService;

	public class GetSutDelegate implements  IResponder
	{
		    private var model: ReportModelLocator = ReportModelLocator.getInstance();
	
			private var responder:IResponder;
			
			public function GetSutDelegate(){
				this.responder = this;
			}
			
			public function get() : void
			{  
				var service:HTTPService = ServiceLocator.getInstance().getHTTPService("getSuts");
				var token:AsyncToken = service.send();
				token.addResponder(responder) ;
			}
			
			public function result(data:Object): void
			{
				var evt:ResultEvent = data as ResultEvent;
				model.suts = new XML(evt.message.body);
			}
			
			public function fault(info:Object): void
			{
				var event:FaultEvent = FaultEvent(info);
				model.error.setMessage(event,"Cannot get the builds list");
				model.applicationState.currentState = "ErrorPage";	
			}
			
		
	}
}