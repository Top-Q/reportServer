package il.co.topq.systems.report.events
{
	import com.adobe.cairngorm.control.CairngormEvent;

	
	
	public class GetLegacyReportToolEvent extends CairngormEvent
	{
		public static var GET_LEGACY_REPORT_TOOL_EVENT:String = "getLegacyReportToolEvent";
		
		public function GetLegacyReportToolEvent(bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(GET_LEGACY_REPORT_TOOL_EVENT,bubbles,cancelable);
		}
	}
	

}