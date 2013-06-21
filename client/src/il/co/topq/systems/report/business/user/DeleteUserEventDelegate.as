package il.co.topq.systems.report.business.user
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import il.co.topq.systems.report.models.valueobjects.ScenarioQuery;
	import il.co.topq.systems.report.models.valueobjects.User;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;
	
	public class DeleteUserEventDelegate
	{
		private var responder:IResponder;
		
		public function DeleteUserEventDelegate(responder : IResponder){
			this.responder = responder;
		}
		
		public function deleteUser(user:User):void {
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("deleteUser");
			service.url += user.id;  
//			service.request = new Date();//used to break the chaching of the get request.
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
	}
}