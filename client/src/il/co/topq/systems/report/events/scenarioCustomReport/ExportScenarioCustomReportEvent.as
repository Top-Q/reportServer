package il.co.topq.systems.report.events.scenarioCustomReport
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.models.valueobjects.ScenarioQuery;
	import il.co.topq.systems.report.models.valueobjects.TestQuery;
	
	import org.as3commons.collections.utils.NullComparator;
	
	public class ExportScenarioCustomReportEvent extends CairngormEvent
	{
		public static var EXPORT_SCENARIO_CUSTOM_REPORT_EVENT:String = "exportScenarioCustomReport";	
		
		public var fileType:String;
		public var fileName:String;
		public var scenarioQuery:ScenarioQuery;
		public var scenarioCustomReportID:String;
		
		public function ExportScenarioCustomReportEvent(fileName:String, fileType:String, scenarioQuery:ScenarioQuery,scenarioCustomReportID:String, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(EXPORT_SCENARIO_CUSTOM_REPORT_EVENT,bubbles,cancelable);
			this.fileType = fileType;
			this.fileName = fileName;
			this.scenarioQuery = scenarioQuery;
			this.scenarioCustomReportID = scenarioCustomReportID;
		}
	}
}