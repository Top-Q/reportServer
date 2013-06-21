package il.co.topq.systems.report.events.config
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.models.valueobjects.TestGroupingPolicy;

	public class SetSystemSettingEvent extends CairngormEvent
	{
		public static var SET_SYSTEM_SETTINGS_EVENT:String = "setSystemSettingEvent";
		
		public var callbackResult:Function;
		public var callbackFault:Function;
		public var systemSettings:TestGroupingPolicy;
		
		public function SetSystemSettingEvent(systemSettings:TestGroupingPolicy, callbackResult:Function, callbackFault:Function, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(SET_SYSTEM_SETTINGS_EVENT,bubbles,cancelable);
			this.callbackFault = callbackFault;
			this.callbackResult = callbackResult;
			this.systemSettings = systemSettings;
		}
	}
}