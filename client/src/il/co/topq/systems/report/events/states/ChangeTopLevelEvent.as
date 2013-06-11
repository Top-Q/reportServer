package il.co.topq.systems.report.events.states
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.models.valueobjects.Chunk;
	import il.co.topq.systems.report.models.valueobjects.User;

	public class ChangeTopLevelEvent extends CairngormEvent
	{
		public static var CHANGE_TOP_LEVEL_EVENT:String = "changeTopLevelEventType";
		
		public var targetState:String;
		
		public function ChangeTopLevelEvent(targetState:String , bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(CHANGE_TOP_LEVEL_EVENT,bubbles,cancelable);
			this.targetState = targetState;	
		}
	}
}