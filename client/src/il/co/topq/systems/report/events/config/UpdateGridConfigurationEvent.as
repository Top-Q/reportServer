package il.co.topq.systems.report.events.config
{
	import flash.events.Event;

	public class UpdateGridConfigurationEvent extends Event
	{
		public static var 	UPDATE_GRID_CONFIGURATION_EVENT:String = "updateGridConfigurationEvent";
		
		public function UpdateGridConfigurationEvent(bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(UPDATE_GRID_CONFIGURATION_EVENT,bubbles,cancelable);
		}
	}
}