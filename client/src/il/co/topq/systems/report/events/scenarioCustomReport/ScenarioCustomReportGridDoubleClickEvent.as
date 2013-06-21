package il.co.topq.systems.report.events.scenarioCustomReport
{
	import flash.events.Event;
	
	import il.co.topq.systems.report.models.valueobjects.ScenarioCustomReportVO;
	
	public class ScenarioCustomReportGridDoubleClickEvent extends Event
	{
		public static var SCENARIO_CUSTOM_REPORT_GRID_DOUBLE_CLICK_EVENT:String = "scenarioCustomReportGridDoubleClickEvent";
		public var scenarioCustomReportVO:ScenarioCustomReportVO;
		
		public function ScenarioCustomReportGridDoubleClickEvent(scenarioCustomReportVO:ScenarioCustomReportVO, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(SCENARIO_CUSTOM_REPORT_GRID_DOUBLE_CLICK_EVENT, bubbles, cancelable);
			this.scenarioCustomReportVO = scenarioCustomReportVO;
		}
	}
}