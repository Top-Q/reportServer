package il.co.topq.systems.report.events.config
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.models.valueobjects.GridConfiguration;

	public class CreateGridConfigurationEvent extends CairngormEvent
	{
		public static var CREATE_GRID_CONFIGURATION_EVENT:String = "createGridConfigurationEvent";
		public var gridConfiguration:GridConfiguration;

		public function CreateGridConfigurationEvent(gridConfiguration:GridConfiguration, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(CREATE_GRID_CONFIGURATION_EVENT,bubbles,cancelable);
			this.gridConfiguration = gridConfiguration;
		}
	}
}