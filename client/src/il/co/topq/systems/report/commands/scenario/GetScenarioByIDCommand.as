package il.co.topq.systems.report.commands.scenario
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.scenario.GetScenarioByIDDelegate;
	import il.co.topq.systems.report.events.scenario.GetScenarioByIDEvent;
	import il.co.topq.systems.report.views.components.reports.tools.TestDetail;
	
	import mx.rpc.Responder;
	
	public class GetScenarioByIDCommand implements ICommand
	{
		public function execute(event:CairngormEvent): void
		{
			var evt:GetScenarioByIDEvent = event as GetScenarioByIDEvent;
			var responder:Responder = new Responder(evt.callbackResult, evt.callbackFault);
			var getScenarioByIDDelegate:GetScenarioByIDDelegate = new GetScenarioByIDDelegate(responder);
			getScenarioByIDDelegate.getScenario(evt.scenarioID);
		}
	}
}
