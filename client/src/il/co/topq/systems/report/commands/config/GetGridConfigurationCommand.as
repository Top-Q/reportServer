package il.co.topq.systems.report.commands.config
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.config.GetGridConfigurationDelegate;
	import il.co.topq.systems.report.events.config.GetGridConfigurationEvent;
	
	import mx.rpc.Responder;

	public class GetGridConfigurationCommand implements ICommand
	{
		public function execute(event:CairngormEvent): void
		{
			var evt:GetGridConfigurationEvent = event as GetGridConfigurationEvent;
			var responder:Responder = new Responder(evt.callbackResult,evt.callbackFault);
			var getGridConfigurationDelegate:GetGridConfigurationDelegate = new GetGridConfigurationDelegate(responder);
			getGridConfigurationDelegate.getGridConfiguration(evt.gridConfiguration.type,evt.gridConfiguration.id);
		}
	}
}