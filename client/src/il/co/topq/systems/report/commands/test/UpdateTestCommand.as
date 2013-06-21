package il.co.topq.systems.report.commands.test
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.test.UpdateTestDelegate;
	import il.co.topq.systems.report.events.test.UpdateTestEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	
	import mx.rpc.Responder;
	
	public class UpdateTestCommand implements ICommand
	{
		public var testXML:XML;
		
		public function execute(event:CairngormEvent): void
		{
			var evt:UpdateTestEvent = event as UpdateTestEvent;
			this.testXML = evt.testXML;
			var responder:Responder = new Responder(evt.callbackResult, evt.callbackFault);
			var updateTestDelegate:UpdateTestDelegate = new UpdateTestDelegate(responder);
			updateTestDelegate.updateTest(testXML);
		}
	}
}