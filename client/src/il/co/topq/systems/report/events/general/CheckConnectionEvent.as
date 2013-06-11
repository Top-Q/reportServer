package il.co.topq.systems.report.events.general
{
	import com.adobe.cairngorm.control.CairngormEvent;

	public class CheckConnectionEvent extends CairngormEvent
	{
		public static var CHECK_CONNECTION_EVENT:String = "checkConnectionEventType";
		
		
		public function CheckConnectionEvent(bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(CHECK_CONNECTION_EVENT,bubbles,cancelable);
		}
	}
}