package il.co.topq.systems.report.business.config
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import il.co.topq.systems.report.models.ReportModelLocator;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.Responder;
	import mx.rpc.http.HTTPService;
	
	public class SetUIConfigurationsDelegate
	{
		private var responder:IResponder;
		
		public function SetUIConfigurationsDelegate(responder:IResponder)
		{
			this.responder = responder;
		}
		public function setUIConfigurations():void {
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("setUIConfigurations");
			service.request = ReportModelLocator.getInstance().uiConfigurations.toXML();
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
	}
}
