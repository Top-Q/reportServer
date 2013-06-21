package il.co.topq.systems.report.commands.general
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.scenario.DeleteScenarioDelegate;
	import il.co.topq.systems.report.events.general.DeleteScenarioEvent;
	
	import mx.rpc.Responder;

	public class DeleteScenarioCommand implements ICommand
	{
		
		public function execute(event:CairngormEvent): void
		{
			var evt:DeleteScenarioEvent = event as DeleteScenarioEvent;
			var responder:Responder = new Responder(evt.callbackResult,evt.callbackFault);
			var deleteScenarioDelegate:DeleteScenarioDelegate = new DeleteScenarioDelegate(responder);
			deleteScenarioDelegate.deleteScenario(evt.scenarioID);
		}
	}

}