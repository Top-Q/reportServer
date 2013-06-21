package il.co.topq.systems.report.business.user
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import il.co.topq.systems.report.models.valueobjects.ScenarioQuery;
	import il.co.topq.systems.report.models.valueobjects.User;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;

	public class CreateUserEventDelegate
	{
		private var responder:IResponder;
		
		public function CreateUserEventDelegate(responder : IResponder){
			this.responder = responder;
		}
		
		public function createUser(user:User):void {
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("createUser");
			service.request = user.toXML();		
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
	}
}