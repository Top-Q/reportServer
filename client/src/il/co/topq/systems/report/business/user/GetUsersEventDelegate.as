package il.co.topq.systems.report.business.user
{
	import mx.rpc.IResponder;
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import il.co.topq.systems.report.models.valueobjects.ScenarioQuery;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;
	public class GetUsersEventDelegate
	{
		private var responder:IResponder;
		
		public function GetUsersEventDelegate(responder : IResponder){
			this.responder = responder;
		}
		
		public function getUsers():void {
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("getUsers");
			service.request = new Date();//used to break the chaching of the get request.
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
	}
}