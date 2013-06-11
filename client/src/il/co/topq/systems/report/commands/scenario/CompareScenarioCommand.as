package il.co.topq.systems.report.commands.scenario
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.scenario.CompareScenarioDelegate;
	import il.co.topq.systems.report.events.scenario.CompareScenarioEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	
	import mx.rpc.Responder;
	
	public class CompareScenarioCommand implements ICommand
	{
		private var model: ReportModelLocator = ReportModelLocator.getInstance();
		
		public function execute(event:CairngormEvent): void
		{
			var evt:CompareScenarioEvent = event as CompareScenarioEvent;	
			var responder:Responder = new Responder(evt.callbackResult, evt.callbackFault);
			var compareScenarioDelegate:CompareScenarioDelegate = new CompareScenarioDelegate(responder);
			compareScenarioDelegate.compareScenarios(evt.comparedScenarios);
		}
	}
}