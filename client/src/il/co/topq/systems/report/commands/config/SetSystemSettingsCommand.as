package il.co.topq.systems.report.commands.config
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.config.SetSystemSettingsDelegate;
	import il.co.topq.systems.report.events.config.SetSystemSettingEvent;
	
	import mx.rpc.Responder;

	public class SetSystemSettingsCommand implements ICommand
	{
		public function execute(event:CairngormEvent): void
		{
			var evt:SetSystemSettingEvent = event as SetSystemSettingEvent;
			var responder:Responder = new Responder(evt.callbackResult,evt.callbackFault);
			var setSystemSettingsDelegate:SetSystemSettingsDelegate = new SetSystemSettingsDelegate(responder);
			setSystemSettingsDelegate.setSystemSettings(evt.systemSettings);
		}
	}
}