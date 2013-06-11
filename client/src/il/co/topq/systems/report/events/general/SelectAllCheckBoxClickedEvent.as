package il.co.topq.systems.report.events.general
{
	import flash.events.Event;
	
	public class SelectAllCheckBoxClickedEvent extends Event
	{
		public var select:Boolean;
		
		public static var SELECT_ALL_CHECK_BOX_CLICKED_EVENT:String = "selectAllCheckBoxClickedEvent";
		public function SelectAllCheckBoxClickedEvent(select:Boolean, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(SELECT_ALL_CHECK_BOX_CLICKED_EVENT, bubbles, cancelable);
			this.select = select;
		}
	}
}