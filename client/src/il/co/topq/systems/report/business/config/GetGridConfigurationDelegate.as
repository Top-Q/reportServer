package il.co.topq.systems.report.business.config
{	
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import il.co.topq.systems.report.models.valueobjects.TypeEnum;
	import il.co.topq.systems.report.services.ReportServiceLocator;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.Responder;
	import mx.rpc.http.HTTPService;

	public class GetGridConfigurationDelegate
	{
		private var responder:IResponder;
		public function GetGridConfigurationDelegate(responder:Responder)
		{
			this.responder = responder	
		}
		
		public function getGridConfiguration(type:TypeEnum, id:int):void{
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("getGridConfiguration");
			service.url +=  type.valueOf() + "/" + id;  
//			service.url = ReportServiceLocator.domain + "/report-service/report/settings/system/getGridConfiguration/"  + type.valueOf() + "/" + id;  
			service.request = new Date();//used to break the chaching of the get request.
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
	}
}