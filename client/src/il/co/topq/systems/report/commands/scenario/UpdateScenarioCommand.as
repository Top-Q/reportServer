package il.co.topq.systems.report.commands.scenario
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.scenario.UpdateScenarioDelegate;
	import il.co.topq.systems.report.events.scenario.UpdateScenarioEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	
	import mx.rpc.Responder;
	
	public class UpdateScenarioCommand implements ICommand
	{
		public var scenarioXML:XML;
		
		public function execute(event:CairngormEvent): void
		{
			var evt:UpdateScenarioEvent = event as UpdateScenarioEvent;
			this.scenarioXML = evt.scenarioXML;
			var responder:Responder = new Responder(evt.callbackResult, evt.callbackFault);
			var updateScenarioDelegate:UpdateScenarioDelegate = new UpdateScenarioDelegate(responder);
			updateScenarioDelegate.updateScenario(scenarioXML);
		}
		
//		public function result(data:Object): void
//		{
//			Alert.show("Scenario updated successfully");
//		}
//		
//		public function fault(info:Object): void
//		{
//			var event:FaultEvent = FaultEvent(info);			
//			model.error.setMessage(event,"Could not update scenario");
//			model.applicationState.currentState = "ErrorPage";	
//		}
	}
}