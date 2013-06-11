package il.co.topq.systems.report.commands.scenario
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.scenario.GetScenariosByFilterEventDelegate;
	import il.co.topq.systems.report.events.scenario.GetScenariosByFilterEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	import il.co.topq.systems.report.models.valueobjects.ScenarioCustomReportGridModel;
	import il.co.topq.systems.report.xml_utils.ScenarioXMLConverter;
	
	import mx.controls.Alert;
	import mx.rpc.IResponder;
	import mx.rpc.events.ResultEvent;
	
	public class GetScenariosByFilterCommand implements ICommand, IResponder
	{
		private var model: ReportModelLocator = ReportModelLocator.getInstance();	
		private var scenarioCustomReportGridModel:ScenarioCustomReportGridModel;
		
		public function execute(event:CairngormEvent): void
		{
			var evt:GetScenariosByFilterEvent = event as GetScenariosByFilterEvent;
			scenarioCustomReportGridModel = evt.scenarioCustomReportGridModel;
			var getScenariosByFilterEventDelegate:GetScenariosByFilterEventDelegate = new GetScenariosByFilterEventDelegate(this);
			getScenariosByFilterEventDelegate.getScenarios(evt.xml);
			
		}
		public function result(data:Object): void
		{
			var evt:ResultEvent = data as ResultEvent;
			var xmlList:XMLList = (new XML(evt.message.body)).scenarios;
			scenarioCustomReportGridModel.gridData.items = ScenarioXMLConverter.fromXMLList(xmlList); 
		}
		
		public function fault(info:Object): void
		{
			Alert.show("ExecutionGetScenarioCommand service was not initialized correctly");
		}
	}
}