/**
 * © 2011 Top-Q Intellectual Property. All rights reserved.
 * Authors: Tomer Gafner & Eran Golan
 * 
 * Send an authorization request to the server.
 * Request Example:
 * "{host}:{port}/report-service/report/authorization/" +username+ "?password="+password
 * 
 * Response Example:
 * <bool> true </bool>
 */ 
package il.co.topq.systems.report.business.authorization
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import il.co.topq.systems.report.services.ReportServiceLocator;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;

	public class LoginDelegate
	{
		private var responder:IResponder;
		
		public function LoginDelegate(responder:IResponder)
		{
			this.responder = responder;
		}
		
		public function login(username:String,password:String) : void
		{  
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("isUserAuthorized");
			service.url = ReportServiceLocator.domain + "/report-service/report/authorization/" +username+ "?password="+password;
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
		
	}
}

