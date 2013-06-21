package il.co.topq.systems.report.events.states
{
	import com.adobe.cairngorm.control.CairngormEvent;

	public class ChangeSettingStateEvent extends CairngormEvent
	{
		public static var CHANGE_SETTINGS_STATE_EVENT:String = "changeSettingsStateEventType";
		
		public var targetState:String;
		
		public function ChangeSettingStateEvent(targetState:String , bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(CHANGE_SETTINGS_STATE_EVENT,bubbles,cancelable);
			this.targetState = targetState;	
		}
	}
}