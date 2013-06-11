package il.co.topq.systems.report.events
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.models.valueobjects.User;

	public class LoginEvent extends CairngormEvent
	{
		public static var LOGIN_EVENT:String = "loginEventType";
		
		public var user:User;
		public var username:String;
		public var password:String;
		public var callbackResult:Function;
		public var callbackFault:Function;
		
//		public function LoginEvent(user:User, callbackResult:Function, callbackFault:Function, bubbles : Boolean = false, cancelable : Boolean = false)
		public function LoginEvent(username:String, password:String, callbackResult:Function, callbackFault:Function, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(LOGIN_EVENT,bubbles,cancelable);
//			this.user = user;
			this.username = username;
			this.password = password;
			this.callbackFault = callbackFault;
			this.callbackResult = callbackResult;
		}
	}
}