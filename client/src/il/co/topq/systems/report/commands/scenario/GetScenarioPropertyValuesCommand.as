package il.co.topq.systems.report.commands.scenario
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.scenario.CompareScenarioDelegate;
	import il.co.topq.systems.report.business.scenario.GetScenarioPropertiesValuesDelegate;
	import il.co.topq.systems.report.events.GetSummaryPageEvent;
	import il.co.topq.systems.report.events.scenario.GetScenarioPropertiesValuesEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	import il.co.topq.systems.report.views.components.Summary;
	
	import mx.controls.Alert;
	import mx.rpc.IResponder;
	import mx.rpc.events.ResultEvent;

	public class GetScenarioPropertyValuesCommand implements ICommand, IResponder
	{
		private var model: ReportModelLocator = ReportModelLocator.getInstance();
		private var summary:Summary;
		
		public function execute(event:CairngormEvent): void
		{
			var evt:GetScenarioPropertiesValuesEvent = event as GetScenarioPropertiesValuesEvent;	
			this.summary = evt.summary;
			var getScenarioPropertiesValuesDelegate:GetScenarioPropertiesValuesDelegate = new GetScenarioPropertiesValuesDelegate(this);
			getScenarioPropertiesValuesDelegate.getScenarioPropertiesValues(evt.propertyKeys);
		}
		public function result(data:Object): void
		{
			var evt:ResultEvent = data as ResultEvent;
			if (summary != null){
				summary.scenarioPropertyValues = new XML(evt.message.body);
				summary.setStatisticsProperties();
				new GetSummaryPageEvent(summary.scenarioGridModel.scenarioQuery.toXML()).dispatch();
			}else{
				Alert.show("summary is null");
			}
		}
		
		public function fault(info:Object): void
		{
			Alert.show("GetScenarioPropertyValuesCommand service was not initialized correctly");
		}
	}
}