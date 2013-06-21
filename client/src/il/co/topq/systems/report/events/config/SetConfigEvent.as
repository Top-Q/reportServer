package il.co.topq.systems.report.events.config
{
	import com.adobe.cairngorm.control.CairngormEvent;

	public class SetConfigEvent extends CairngormEvent
	{
		
		public static var SET_CONFIG_EVENT:String = "setConfig";
		
		public var username:String;
		
		public var password:String;
		
		
		public function SetConfigEvent(username:String, password:String, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(SET_CONFIG_EVENT,bubbles,cancelable);
			this.username = username;
			this.password = password;
		}
	}
}