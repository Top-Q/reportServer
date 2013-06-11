package il.co.topq.systems.report.commands.test
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.test.GetTestGroupDelegate;
	import il.co.topq.systems.report.business.test.GetTestLogFolderDelegate;
	import il.co.topq.systems.report.events.test.GetTestGroupEvent;
	import il.co.topq.systems.report.events.test.GetTestLogFolderEvent;
	
	import mx.rpc.Responder;
	
	public class GetTestLogFolderCommand implements ICommand
	{
		public function execute(event:CairngormEvent): void
		{
			var evt:GetTestLogFolderEvent = event as GetTestLogFolderEvent;
			var responder:Responder = new Responder(evt.callbackResult,evt.callbackFault);
			var getTestLogFolderDelegate :GetTestLogFolderDelegate = new GetTestLogFolderDelegate(responder);
			getTestLogFolderDelegate.getTestLogFolder(evt.testId);
		}
	}
}