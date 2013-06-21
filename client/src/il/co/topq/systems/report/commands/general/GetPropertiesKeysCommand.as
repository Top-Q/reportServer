package il.co.topq.systems.report.commands.general
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.general.GetPropertiesKeysDelegate;
	import il.co.topq.systems.report.events.general.GetPropertiesKeysEvent;
	
	import mx.rpc.Responder;

	public class GetPropertiesKeysCommand implements ICommand
	{
		public function execute(event:CairngormEvent): void
		{
			var evt:GetPropertiesKeysEvent = event as GetPropertiesKeysEvent;
			var responder:Responder = new Responder(evt.callbackResult,evt.callbackFault);
			var getPropertiesKeysDelegate:GetPropertiesKeysDelegate = new GetPropertiesKeysDelegate(responder);
			getPropertiesKeysDelegate.getPropertiesKeys(evt.serviceLocator);
		}
	}
}