package il.co.topq.systems.report.commands.test
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.test.GetTestByIDDelegate;
	import il.co.topq.systems.report.events.test.GetTestByIDEvent;
	
	import mx.rpc.Responder;
	
	public class GetTestByIDCommand implements ICommand
	{
		public function execute(event:CairngormEvent): void
		{
			var evt:GetTestByIDEvent = event as GetTestByIDEvent;
			var responder:Responder = new Responder(evt.callbackResult,evt.callbackFault);
			var getTestByIDDelegate:GetTestByIDDelegate = new GetTestByIDDelegate(responder);
			getTestByIDDelegate.getTest(evt.testID);
		}
	}
}
