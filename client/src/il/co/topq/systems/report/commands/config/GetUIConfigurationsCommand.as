package il.co.topq.systems.report.commands.config
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.config.GetUIConfigurationsDelegate;
	import il.co.topq.systems.report.events.config.GetUIConfigurationsEvent;
	
	import mx.rpc.Responder;

	public class GetUIConfigurationsCommand implements ICommand
	{
		public function execute(event:CairngormEvent): void
		{
			var evt:GetUIConfigurationsEvent = event as GetUIConfigurationsEvent;
			var responder:Responder = new Responder(evt.callbackResult,evt.callbackFault);
			var getSystemSettingDelegate:GetUIConfigurationsDelegate = new GetUIConfigurationsDelegate(responder);
			getSystemSettingDelegate.getSystemSettings();
		}
	}
}