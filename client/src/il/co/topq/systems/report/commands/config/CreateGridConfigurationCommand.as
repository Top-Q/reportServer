package il.co.topq.systems.report.commands.config
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.config.CreateGridConfigurationDelegate;
	import il.co.topq.systems.report.events.config.CreateGridConfigurationEvent;
	
	import mx.controls.Alert;
	import mx.rpc.IResponder;
	
	public class CreateGridConfigurationCommand implements ICommand, IResponder
	{
		public function execute(event:CairngormEvent): void
		{
			var evt:CreateGridConfigurationEvent = event as CreateGridConfigurationEvent;
			var createGridConfigurationDelegate:CreateGridConfigurationDelegate
			= new CreateGridConfigurationDelegate(this);			
			
			createGridConfigurationDelegate.createGridConfiguration(evt.gridConfiguration);				
		}
		
		public function result(data:Object): void
		{		
			//success.
		}
		
		public function fault(info:Object): void
		{
			Alert.show("CreateGridConfigurationCommand service was not initialized correctly");
		}
		
	}
	
}