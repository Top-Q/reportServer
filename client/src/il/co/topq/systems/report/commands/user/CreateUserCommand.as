package il.co.topq.systems.report.commands.user
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.user.CreateUserEventDelegate;
	import il.co.topq.systems.report.business.user.GetUsersEventDelegate;
	import il.co.topq.systems.report.events.user.CreateUserEvent;
	import il.co.topq.systems.report.events.user.GetUsersEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	
	import mx.rpc.Responder;
	
	public class CreateUserCommand implements ICommand
	{
		private var model: ReportModelLocator = ReportModelLocator.getInstance();		
		
		public function execute(event:CairngormEvent): void
		{
			var evt:CreateUserEvent = event as CreateUserEvent;
			var responder:Responder = new Responder(evt.callbackResult,evt.callbackFault);
			var createUserEventDelegate:CreateUserEventDelegate = new CreateUserEventDelegate(responder);
			createUserEventDelegate.createUser(evt.user);
		}
	}
}