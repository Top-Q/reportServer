package il.co.topq.systems.report.business.config
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import il.co.topq.systems.report.models.valueobjects.GridConfiguration;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;
	
	public class CreateGridConfigurationDelegate
	{
		private var responder:IResponder;
		
		public function CreateGridConfigurationDelegate(responder:IResponder)
		{
			this.responder = responder	
		}
		
		public function createGridConfiguration(gridConfiguration:GridConfiguration):void{
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("createGridConfiguration");
			
			service.request = gridConfiguration.toXML();
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
	}
}