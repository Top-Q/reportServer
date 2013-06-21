package il.co.topq.systems.report.events
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	public class NumberOfPagesChangedEvent extends CairngormEvent 
	{
		public static var NUMBER_OF_PAGES_CHANGED_EVENT:String = "numberOfPagesChangedEventType";
		
		public function NumberOfPagesChangedEvent(bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(NUMBER_OF_PAGES_CHANGED_EVENT,bubbles,cancelable);
		}
	}
}