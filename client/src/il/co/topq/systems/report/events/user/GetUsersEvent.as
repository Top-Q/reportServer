package il.co.topq.systems.report.events.user
{
	import com.adobe.cairngorm.control.CairngormEvent;

	public class GetUsersEvent extends CairngormEvent
	{
		
		public static var GET_USERS_EVENT:String = "GetUsersEvent";
		public var callbackResult:Function;
		public var callbackFault:Function;
		
		public function GetUsersEvent(callbackResult:Function, callbackFault:Function, bubbles : Boolean = false, cancelable : Boolean = false)
		{			
			super(GET_USERS_EVENT,bubbles,cancelable);		
			this.callbackFault =callbackFault;
			this.callbackResult = callbackResult;
		}
		
	}
}