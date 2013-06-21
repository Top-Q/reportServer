package il.co.topq.systems.report.events.scenario
{
	import com.adobe.cairngorm.control.CairngormEvent;
	import il.co.topq.systems.report.models.valueobjects.ScenarioCustomReportGridModel;
	
	public class GetScenariosByFilterEvent extends CairngormEvent
	{
		public static var GET_SCENARIOS_BY_FILTER_EVENT:String = "GetScenariosByFilterEvent";
		public var xml:XML;
		public var scenarioCustomReportGridModel:ScenarioCustomReportGridModel;
		
		public function GetScenariosByFilterEvent(xml: XML,scenarioCustomReportGridModel:ScenarioCustomReportGridModel, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(GET_SCENARIOS_BY_FILTER_EVENT, bubbles,cancelable);		
			this.xml = xml;
			this.scenarioCustomReportGridModel = scenarioCustomReportGridModel;
		}
	}
}	