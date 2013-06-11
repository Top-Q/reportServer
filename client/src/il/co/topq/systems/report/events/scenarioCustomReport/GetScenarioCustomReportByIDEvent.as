package il.co.topq.systems.report.events.scenarioCustomReport
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	public class GetScenarioCustomReportByIDEvent extends CairngormEvent
	{
		public static var GET_SCENARIO_CUSTOM_REPORT_BY_ID_EVENT:String = "GetScenarioCustomReportByIDEventType";
		public var id:String;
		
		public function GetScenarioCustomReportByIDEvent(id: String, bubbles : Boolean = false, cancelable : Boolean = false)
			{			
				super(GET_SCENARIO_CUSTOM_REPORT_BY_ID_EVENT,bubbles,cancelable);		
				this.id = id;
			}
	}
}