package il.co.topq.systems.report.commands.scenario
{
	import com.adobe.cairngorm.commands.Command;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.scenario.DeleteScenarioByScenarioQueryEventDelegate;
	import il.co.topq.systems.report.events.scenario.DeleteScenarioByScenarioQueryEvent;
	
	import mx.rpc.Responder;

	public class DeleteScenarioByScenarioQueryCommand implements Command
	{
		public function execute(event:CairngormEvent): void
		{
			
			var evt:DeleteScenarioByScenarioQueryEvent = event as DeleteScenarioByScenarioQueryEvent;
			
			var responder:Responder = new Responder(evt.callbackResult, evt.callbackFault);
			
			var deleteScenarioByScenarioQueryEventDelegate:DeleteScenarioByScenarioQueryEventDelegate 
			= new DeleteScenarioByScenarioQueryEventDelegate(responder);	
			
			deleteScenarioByScenarioQueryEventDelegate.deleteScenario(evt.scenarioQuery);				
		}
	}
}