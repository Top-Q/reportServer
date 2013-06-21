package il.co.topq.systems.report.business.user
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import il.co.topq.systems.report.models.valueobjects.ScenarioQuery;
	import il.co.topq.systems.report.models.valueobjects.User;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;
	
	public class UpdateUserEventDelegate
	{
		private var responder:IResponder;
		
		public function UpdateUserEventDelegate(responder : IResponder){
			this.responder = responder;
		}
		
		public function updateUser(user:User):void {
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("updateUser");
			service.request = user.toXML();		
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
	}
}