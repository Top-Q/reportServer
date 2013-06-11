package il.co.topq.systems.report.events.states
{
	import com.adobe.cairngorm.control.CairngormEvent;

	public class ChangeReportsStateEvent extends CairngormEvent
	{
		public static var CHANGE_REPORTS_STATE_EVENT:String = "changeReportsStateEventType";
		
		public var targetState:String;
		public function ChangeReportsStateEvent(targetState:String , bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(CHANGE_REPORTS_STATE_EVENT,bubbles,cancelable);
			this.targetState = targetState;	
		}
	}
}