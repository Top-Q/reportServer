package il.co.topq.systems.report.events
{
	import com.adobe.cairngorm.control.CairngormEvent;

	public class LogoutEvent extends CairngormEvent
	{
		public static var LOGOUT_EVENT:String = "logoutEventType";
		
		public function LogoutEvent(bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(LOGOUT_EVENT,bubbles,cancelable);
		}
	}
}