package il.co.topq.systems.report.commands.test
{	
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.test.GetTestPropertiesKeysEventDelegate;
	import il.co.topq.systems.report.business.test.GetTestPropertiesKeysInEditModeDelegate;
	import il.co.topq.systems.report.events.test.GetTestPropertiesKeysEvent;
	import il.co.topq.systems.report.events.test.GetTestPropertiesKeysInEditModeEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	
	import mx.controls.Alert;
	import mx.rpc.IResponder;
	import mx.rpc.events.ResultEvent;
	
	public class GetTestPropertiesKeyInEditModeCommand implements ICommand, IResponder
	{
		private var model: ReportModelLocator = ReportModelLocator.getInstance();
		
		public function execute(event:CairngormEvent): void
		{
			var evt:GetTestPropertiesKeysInEditModeEvent = event as GetTestPropertiesKeysInEditModeEvent;			
			var getTestPropertiesKeysInEditModeEventDelegate:GetTestPropertiesKeysInEditModeDelegate = new GetTestPropertiesKeysInEditModeDelegate(this);
			getTestPropertiesKeysInEditModeEventDelegate.getTestsProperties(evt.testCustomReportID);			
		}
		
		public function result(data:Object): void
		{			
			var evt:ResultEvent = data as ResultEvent;			
			model.scenarioProperties = new XML(evt.message.body);
		}
		
		public function fault(info:Object): void
		{
			Alert.show("GetTestPropertiesKeyInEditModeCommand is not working properly");
		}
	}
}