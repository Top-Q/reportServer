package il.co.topq.systems.report.commands.test
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.test.GetTestByIDDelegate;
	import il.co.topq.systems.report.business.test.GetTestScenarioDelegate;
	import il.co.topq.systems.report.events.scenario.GetScenarioByIDEvent;
	import il.co.topq.systems.report.events.test.GetTestByIDEvent;
	import il.co.topq.systems.report.events.test.GetTestScenarioEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	import il.co.topq.systems.report.views.components.reports.tools.TestDetail;
	
	import mx.controls.Alert;
	import mx.rpc.IResponder;
	import mx.rpc.events.FaultEvent;
	
	public class GetTestScenarioCommand implements ICommand, IResponder
	{
		private var model: ReportModelLocator = ReportModelLocator.getInstance();
		private var testDetail:TestDetail;
		public var callbackResult:Function;
		public var callbackFault:Function;
		
		public function execute(event:CairngormEvent): void
		{
			var evt:GetTestScenarioEvent = event as GetTestScenarioEvent;
			this.callbackFault = evt.callbackFault;
			this.callbackResult = evt.callbackResult;
			var getTestScenarioDelegate:GetTestScenarioDelegate = new GetTestScenarioDelegate(this);
			getTestScenarioDelegate.getTestScenario(evt.testID);
		}
		public function result(data:Object): void
		{
			new GetScenarioByIDEvent(new XML(data.message.body),callbackResult,callbackFault).dispatch();
		}
		public function fault(info:Object): void
		{
			Alert.show("Could not retrieve test scenario");
		}
	}
}
