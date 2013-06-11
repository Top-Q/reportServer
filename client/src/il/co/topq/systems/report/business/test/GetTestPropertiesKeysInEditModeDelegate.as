package il.co.topq.systems.report.business.test
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;
	import il.co.topq.systems.report.services.ReportServiceLocator;
	
	public class GetTestPropertiesKeysInEditModeDelegate
	{
		private var responder:IResponder;
		
		public function GetTestPropertiesKeysInEditModeDelegate(responder : IResponder)
		{
			this.responder = responder;		
		}
		
		public function getTestsProperties(id:String) : void{
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("getTestsPropertiesValuesInEditMode");	
			service.url = ReportServiceLocator.domain + "/report-service/report/testProperties/uniqeKeysInEdit/"  + id;  
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}	
	}
}