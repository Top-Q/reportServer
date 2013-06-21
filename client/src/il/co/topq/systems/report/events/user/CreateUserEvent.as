package il.co.topq.systems.report.events.user
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.models.valueobjects.User;
	
	public class CreateUserEvent extends CairngormEvent
	{
		public static var CREATE_USER_EVENT:String = "CreateUserEvent";
		public var callbackResult:Function;
		public var callbackFault:Function;
		public var user:User;
		
		public function CreateUserEvent(callbackResult:Function, callbackFault:Function,user:User, bubbles : Boolean = false, cancelable : Boolean = false)
		{			
			super(CREATE_USER_EVENT,bubbles,cancelable);		
			this.callbackFault =callbackFault;
			this.callbackResult = callbackResult;
			this.user = user;
		}
		
	}
}