package il.co.topq.systems.report.commands.test
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.test.GetGroupTestsDelegate;
	import il.co.topq.systems.report.events.test.GetGroupTestsEvent;
	
	import mx.rpc.Responder;

	public class GetGroupTestsCommand implements ICommand
	{
		public function execute(event:CairngormEvent): void{
			var evt:GetGroupTestsEvent = event as GetGroupTestsEvent;
			var responder:Responder = new Responder(evt.callbackResult,evt.callbackFault);
			var getGroupTestsDelegate :GetGroupTestsDelegate = new GetGroupTestsDelegate(responder);
			getGroupTestsDelegate.getGroupTests(evt.testSummaryQuery);
		}
		
			
	}
}