package il.co.topq.systems.report.events.general
{
	import flash.events.Event;
	
	public class UpdateEvent extends Event
	{
		public static var UPDATE_EVENT:String = "updateEvent";
		
		public function UpdateEvent(bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(UPDATE_EVENT,bubbles,cancelable);
		}
	}
}