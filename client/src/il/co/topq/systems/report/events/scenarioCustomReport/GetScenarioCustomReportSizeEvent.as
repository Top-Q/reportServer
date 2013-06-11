package il.co.topq.systems.report.events.scenarioCustomReport
{
	import il.co.topq.systems.report.commands.general.GetScenarioQuerySizeEvent;
	import il.co.topq.systems.report.models.valueobjects.ScenarioGridModel;
	
	public class GetScenarioCustomReportSizeEvent extends GetScenarioQuerySizeEvent
	{
		public static var GET_SCENARIO_CUSTOM_REPORT_SIZE_EVENT:String = "GetScenarioCustomReportSizeEventType";
		private var eventServiceLocator:String = "getScenarioCustomReportSize";
		
		public function GetScenarioCustomReportSizeEvent(scenarioGridModel:ScenarioGridModel,bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(GET_SCENARIO_CUSTOM_REPORT_SIZE_EVENT,scenarioGridModel,eventServiceLocator,bubbles, cancelable);
		}
	}
}