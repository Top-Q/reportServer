package il.co.topq.systems.report.events.general
{
	import flash.events.Event;
	
	public class RefreshCustomReportGridEvent extends Event
	{
		public static var REFRESH_CUSTOM_REPORT_GRID_EVENT:String = "refreshCustomReportGridEvent";
		public var customReportType:String;
		
		public function RefreshCustomReportGridEvent(customReportType:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(REFRESH_CUSTOM_REPORT_GRID_EVENT, bubbles, cancelable);
			this.customReportType = customReportType;
		}
	}
}