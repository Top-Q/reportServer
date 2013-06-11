package il.co.topq.systems.report.events.general
{
	import flash.events.Event;
	
	public class ShowCustomReportsEvent extends Event
	{
		public static var SHOW_CUSTOM_REPORTS_EVENT:String = "showCustomReportsEvent";
		public function ShowCustomReportsEvent(bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(SHOW_CUSTOM_REPORTS_EVENT, bubbles, cancelable);
		}
	}
}