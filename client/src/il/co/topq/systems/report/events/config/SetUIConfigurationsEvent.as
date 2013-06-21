package il.co.topq.systems.report.events.config
{
	import com.adobe.cairngorm.control.CairngormEvent;

	public class SetUIConfigurationsEvent extends CairngormEvent
	{
		public static var SET_UI_CONFIGURATIONS_EVENT:String = "setUIConfigurationsEvent";
		
		public function SetUIConfigurationsEvent(bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(SET_UI_CONFIGURATIONS_EVENT,bubbles,cancelable);
		}
	}
}