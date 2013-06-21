package il.co.topq.systems.report.events.user
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.models.valueobjects.User;
	
	public class DeleteUserEvent extends CairngormEvent
	{
		public static var DELETE_USER_EVENT:String = "DeleteUserEvent";
		public var callbackResult:Function;
		public var callbackFault:Function;
		public var user:User;
		
		public function DeleteUserEvent(callbackResult:Function, callbackFault:Function,user:User, bubbles : Boolean = false, cancelable : Boolean = false)
		{			
			super(DELETE_USER_EVENT,bubbles,cancelable);		
			this.callbackFault =callbackFault;
			this.callbackResult = callbackResult;
			this.user = user;
		}
		
	}
}