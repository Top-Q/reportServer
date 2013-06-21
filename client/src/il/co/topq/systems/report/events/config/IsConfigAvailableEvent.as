package il.co.topq.systems.report.events.config
{
	import com.adobe.cairngorm.control.CairngormEvent;

	public class IsConfigAvailableEvent extends CairngormEvent
	{
		
		public static var IS_CONFIG_AVAILABLE_EVENT:String = "isConfigAvailable";
		
		public function IsConfigAvailableEvent(bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(IS_CONFIG_AVAILABLE_EVENT,bubbles,cancelable);
		}
	}
}