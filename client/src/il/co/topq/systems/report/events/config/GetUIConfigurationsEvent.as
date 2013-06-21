package il.co.topq.systems.report.events.config
{
	import com.adobe.cairngorm.control.CairngormEvent;

	public class GetUIConfigurationsEvent extends CairngormEvent
	{
		public static var GET_SYSTEM_SETTINGS_EVENT:String = "getSystemSettingEvent";
		
		public var callbackResult:Function;
		public var callbackFault:Function;
		
		public function GetUIConfigurationsEvent(callbackResult:Function, callbackFault:Function, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(GET_SYSTEM_SETTINGS_EVENT,bubbles,cancelable);
			this.callbackFault = callbackFault;
			this.callbackResult = callbackResult;
		}
	}
}