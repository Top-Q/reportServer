/**
 * © 2011 Top-Q Intellectual Property. All rights reserved.
 * Authors: Tomer Gafner & Eran Golan
 * 
 */ 
package il.co.topq.systems.report.business.config
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import il.co.topq.systems.report.services.ReportServiceLocator;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;

	public class ConfigurationDelegate 
	{
		
		private var responder:IResponder; 
		
		public function ConfigurationDelegate(responder:IResponder)
		{
			this.responder = responder;
		}
		
		 public function isAvailable():void
		 {
			 var service:HTTPService = ServiceLocator.getInstance().getHTTPService("isConfigAvailable");
//			 service.url = ReportServiceLocator.domain + "/report-service/config-service/config";
			 var token:AsyncToken = service.send();
			 token.addResponder(responder) ;
		 }
		 
		 public function setSetupConfig(username:String,password:String):void
		 {
			 var service:HTTPService = ServiceLocator.getInstance().getHTTPService("setConfig");
			 service.url += "?username=" + username + "&password="+password;
//			 service.url = ReportServiceLocator.domain + "/report-service/report/config?username=" + username + "&password="+password;
			 var token:AsyncToken = service.send();
			 token.addResponder(responder) ;
		 }
		 		 
	}
}

