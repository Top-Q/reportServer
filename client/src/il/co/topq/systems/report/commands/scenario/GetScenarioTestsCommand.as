package il.co.topq.systems.report.commands.scenario
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.mock.GetScenarioDelegateMock;
	import il.co.topq.systems.report.business.scenario.GetScenarioEventDelegate;
	import il.co.topq.systems.report.business.scenario.GetScenarioTestsEventDelegate;
	import il.co.topq.systems.report.events.scenario.GetScenarioEvent;
	import il.co.topq.systems.report.events.scenario.GetScenarioTestsEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	import il.co.topq.systems.report.models.grid.TestReportModel;
	import il.co.topq.systems.report.models.valueobjects.Chunk;
	import il.co.topq.systems.report.models.valueobjects.TestGridModel;
	import il.co.topq.systems.report.xml_utils.TestXMLConverter;
	
	import mx.controls.Alert;
	import mx.rpc.IResponder;
	import mx.rpc.events.ResultEvent;
	
	public class GetScenarioTestsCommand implements ICommand, IResponder
	{
		private var model: ReportModelLocator = ReportModelLocator.getInstance();	
		public var testGridModel:TestGridModel;
		
		public function execute(event:CairngormEvent): void
		{
			var evt:GetScenarioTestsEvent = event as GetScenarioTestsEvent;
			this.testGridModel = evt.testGridModel;
			var getScenarioTestsEventDelegate:GetScenarioTestsEventDelegate = new GetScenarioTestsEventDelegate(this);
			getScenarioTestsEventDelegate.getScenarioTests(evt.scenarioID,evt.testGridModel.testQuery);
		}
		
		public function result(data:Object): void
		{
			var evt:ResultEvent = data as ResultEvent;
			var xmlList:XMLList = (new XML(evt.message.body)).tests;
			testGridModel.gridData.items = TestXMLConverter.fromXMLList(xmlList); 
		}
		
		public function fault(info:Object): void
		{
			Alert.show("GetScenarioTestsCommand service was not initialized correctly");
		}
	}
}