package il.co.topq.systems.report.commands.test
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.scenario.GetScenariosByFilterEventDelegate;
	import il.co.topq.systems.report.business.test.GetTestsByFilterEventDelegate;
	import il.co.topq.systems.report.events.scenario.GetScenariosByFilterEvent;
	import il.co.topq.systems.report.events.test.GetTestsByFilterEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	import il.co.topq.systems.report.models.valueobjects.TestCustomReportGridModel;
	import il.co.topq.systems.report.xml_utils.TestXMLConverter;
	
	import mx.controls.Alert;
	import mx.rpc.IResponder;
	import mx.rpc.events.ResultEvent;
	
	public class GetTestsByFilterCommand implements ICommand, IResponder
	{
		private var model: ReportModelLocator = ReportModelLocator.getInstance();	
		private var testCustomReportGridModel:TestCustomReportGridModel;
		
		public function execute(event:CairngormEvent): void
		{
			var evt:GetTestsByFilterEvent = event as GetTestsByFilterEvent;
			testCustomReportGridModel = evt.testCustomReportGridModel;
			var getTestsByFilterEventDelegate:GetTestsByFilterEventDelegate = new GetTestsByFilterEventDelegate(this);
			getTestsByFilterEventDelegate.getTests(evt.xml);
			
		}
		public function result(data:Object): void
		{
			var evt:ResultEvent = data as ResultEvent;
			var xmlList:XMLList = (new XML(evt.message.body)).tests;
			testCustomReportGridModel.gridData.items = TestXMLConverter.fromXMLList(xmlList); 
		}
		
		public function fault(info:Object): void
		{
			Alert.show("GetTestsByFilterCommand service was not initialized correctly");
		}
	}
}