package il.co.topq.systems.report.business.config
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import il.co.topq.systems.report.models.valueobjects.TestGroupingPolicy;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;

	public class SetSystemSettingsDelegate
	{
		private var responder:IResponder
		
		public function SetSystemSettingsDelegate(responder:IResponder)
		{
			this.responder = responder;
		}
		
		public function setSystemSettings(systemSettings:TestGroupingPolicy):void {
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("setSystemSettings");
			service.request = systemSettings.toXML();
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
	}
}