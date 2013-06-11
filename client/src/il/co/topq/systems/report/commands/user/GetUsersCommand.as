package il.co.topq.systems.report.commands.user
{
	import mx.rpc.Responder;
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.user.GetUsersEventDelegate;
	import il.co.topq.systems.report.events.user.GetUsersEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;

	public class GetUsersCommand implements ICommand
		{
			private var model: ReportModelLocator = ReportModelLocator.getInstance();		
			
			public function execute(event:CairngormEvent): void
			{
				var evt:GetUsersEvent = event as GetUsersEvent;
				var responder:Responder = new Responder(evt.callbackResult,evt.callbackFault);
				var getUsersEventDelegate:GetUsersEventDelegate = new GetUsersEventDelegate(responder);
				getUsersEventDelegate.getUsers();
				
			}
			
			
		public function GetUsersCommand()
		{
		}
	}
}