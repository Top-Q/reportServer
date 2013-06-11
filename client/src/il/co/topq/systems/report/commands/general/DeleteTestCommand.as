package il.co.topq.systems.report.commands.general
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.test.DeleteTestDelegate;
	import il.co.topq.systems.report.events.general.DeleteTestEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	import il.co.topq.systems.report.models.valueobjects.Chunk;
	
	import mx.controls.Alert;
	import mx.rpc.IResponder;
	import mx.rpc.Responder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	
	public class DeleteTestCommand implements ICommand
	{
		public function execute(event:CairngormEvent): void
		{
			var evt:DeleteTestEvent = event as DeleteTestEvent;		
			var responder:Responder = new Responder(evt.callbackResult, evt.callbackFault);
			var deleteTestDelegate:DeleteTestDelegate = new DeleteTestDelegate(responder);
			deleteTestDelegate.deleteTest(evt.testID);
		}
	}
}