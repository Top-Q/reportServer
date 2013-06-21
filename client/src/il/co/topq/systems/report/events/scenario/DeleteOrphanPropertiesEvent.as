package il.co.topq.systems.report.events.scenario
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	public class DeleteOrphanPropertiesEvent extends CairngormEvent
	{
		public static var DELETE_ORPHAN_PROPERTIES_EVENT:String = "deleteOrphanPropertiesEvent";
		public function DeleteOrphanPropertiesEvent(bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(DELETE_ORPHAN_PROPERTIES_EVENT, bubbles, cancelable);
		}
	}
}