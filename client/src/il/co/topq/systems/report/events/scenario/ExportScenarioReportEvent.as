package il.co.topq.systems.report.events.scenario
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.models.valueobjects.Filter;
	import il.co.topq.systems.report.models.valueobjects.ScenarioQuery;
	
	public class ExportScenarioReportEvent extends CairngormEvent
	{
		public static var EXPORT_SCENARIO_REPORT_EVENT:String = "ExportScenarioReport";		

		public var fileType:String;
		public var fileName:String;
		public var scenarioQuery:ScenarioQuery;
		
		public function ExportScenarioReportEvent(fileName:String, fileType:String,scenarioQuery:ScenarioQuery, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(EXPORT_SCENARIO_REPORT_EVENT,bubbles,cancelable);
			this.fileType = fileType;
			this.fileName = fileName;
			this.scenarioQuery = scenarioQuery;
		}		
	}
}