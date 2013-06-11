package il.co.topq.systems.report.commands.test
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.test.GetTestGroupDelegate;
	import il.co.topq.systems.report.events.test.GetTestGroupEvent;
	
	import mx.rpc.Responder;

	public class GetTestGroupCommand implements ICommand
	{
		public function execute(event:CairngormEvent): void
		{
			var evt:GetTestGroupEvent= event as GetTestGroupEvent;
			var responder:Responder = new Responder(evt.callbackResult,evt.callbackFault);
			var getTestGroupDelegate :GetTestGroupDelegate = new GetTestGroupDelegate(responder);
			getTestGroupDelegate.getTestGroup(evt.testQuery);
		}
	}
}