package il.co.topq.systems.report.commands.test
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.test.GetTestDelegate;
	import il.co.topq.systems.report.events.test.GetTestEvent;
	
	import mx.rpc.Responder;

	public class GetTestCommand implements ICommand
	{
		public function execute(event:CairngormEvent): void
		{
			var evt:GetTestEvent = event as GetTestEvent;
			var responder:Responder = new Responder(evt.callbackResult,evt.callbackResult);
			var getTestSummaryDelegate:GetTestDelegate = new GetTestDelegate(responder);
			getTestSummaryDelegate.getTests(evt.testQuery);
		}
	}
}