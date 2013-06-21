package il.co.topq.systems.report.commands
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.authorization.LoginDelegate;
	import il.co.topq.systems.report.events.LoginEvent;
	
	import mx.rpc.Responder;

	public class LoginCommand implements ICommand
	{
		public function execute(event:CairngormEvent): void
		{
			var evt:LoginEvent = event as LoginEvent;
			var responder:Responder = new Responder(evt.callbackResult,evt.callbackFault);
			var loginDelegate:LoginDelegate = new LoginDelegate(responder);
			loginDelegate.login(evt.username,evt.password);
//			loginDelegate.login(evt.user.username,evt.user.password);
		}
		
	}
}