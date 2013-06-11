package il.co.topq.systems.report.events.config
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.models.valueobjects.GridConfiguration;

	public class GetGridConfigurationEvent extends CairngormEvent	
	{
		public static var GET_GRID_CONFIGURATION_EVENT:String = "getGridConfigurationEvent";
		
		public var callbackResult:Function;
		public var callbackFault:Function;
		public var gridConfiguration:GridConfiguration;
		
		public function GetGridConfigurationEvent( gridConfiguration:GridConfiguration, callbackResult:Function, callbackFault:Function, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(GET_GRID_CONFIGURATION_EVENT,bubbles,cancelable);
			this.callbackFault = callbackFault;
			this.callbackResult = callbackResult;
			this.gridConfiguration = gridConfiguration;
		}
	}
}