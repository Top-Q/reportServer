package il.co.topq.systems.report.events.scenario
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.models.valueobjects.ScenarioCustomReportGridModel;
	
	public class GetScenariosByFilterSizeEvent extends CairngormEvent
	{
		public static var GET_SCENARIOS_BY_FILTER_SIZE_EVENT:String = "GetScenariosByFilterSizeEvent";
		public var xml:XML;
		public var scenarioCustomReportGridModel:ScenarioCustomReportGridModel;
		
		public function GetScenariosByFilterSizeEvent(xml: XML, scenarioCustomReportGridModel:ScenarioCustomReportGridModel, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(GET_SCENARIOS_BY_FILTER_SIZE_EVENT, bubbles,cancelable);		
			this.xml = xml;
			this.scenarioCustomReportGridModel = scenarioCustomReportGridModel;
		}
	}
}	