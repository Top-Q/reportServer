package il.co.topq.systems.report.events.scenario
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	public class ExportScenarioDetailsEvent extends CairngormEvent
	{
		public static var EXPORT_SCENARIO_DETAILS_EVENT:String = "ExportScenarioDetailsReport";		
		
		public var fileType:String;
		public var fileName:String;
		public var scenarioID:String;
		
		public function ExportScenarioDetailsEvent(fileName:String, fileType:String,scenarioID:String,bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(EXPORT_SCENARIO_DETAILS_EVENT,bubbles,cancelable);
			this.fileType = fileType;
			this.fileName = fileName;
			this.scenarioID = scenarioID;
		}		
	}
}