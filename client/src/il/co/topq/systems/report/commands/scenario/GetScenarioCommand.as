package il.co.topq.systems.report.commands.scenario
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.mock.GetScenarioDelegateMock;
	import il.co.topq.systems.report.business.scenario.GetScenarioEventDelegate;
	import il.co.topq.systems.report.events.scenario.GetScenarioEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	import il.co.topq.systems.report.models.valueobjects.Chunk;
	import il.co.topq.systems.report.models.valueobjects.ScenarioGridModel;
	import il.co.topq.systems.report.models.valueobjects.ScenarioQuery;
	import il.co.topq.systems.report.xml_utils.ScenarioXMLConverter;
	
	import mx.controls.Alert;
	import mx.rpc.IResponder;
	import mx.rpc.Responder;
	import mx.rpc.events.ResultEvent;

	public class GetScenarioCommand implements ICommand
	{
		private var model: ReportModelLocator = ReportModelLocator.getInstance();		
		
		public function execute(event:CairngormEvent): void
		{
			var evt:GetScenarioEvent = event as GetScenarioEvent;
			var responder:Responder = new Responder(evt.callbackResult,evt.callbackFault);
			var legacyGetScenarioEventDelegate:GetScenarioEventDelegate = new GetScenarioEventDelegate(responder);
			legacyGetScenarioEventDelegate.getScenarios(evt.scenarioQuery);

		}
	}
}