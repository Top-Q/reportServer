package il.co.topq.systems.report.commands.config
{
	import com.adobe.cairngorm.business.Responder;
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.config.GetUIConfigurationsDelegate;
	import il.co.topq.systems.report.business.config.SetUIConfigurationsDelegate;
	import il.co.topq.systems.report.events.config.GetUIConfigurationsEvent;
	import il.co.topq.systems.report.events.config.SetUIConfigurationsEvent;
	
	import mx.rpc.IResponder;
	
	public class SetUIConfigurationsCommand implements ICommand, IResponder
	{
		public function execute(event:CairngormEvent): void
		{
			var evt:SetUIConfigurationsEvent = event as SetUIConfigurationsEvent;
			var setUIConfigurationsDelegate:SetUIConfigurationsDelegate = new SetUIConfigurationsDelegate(this);
			setUIConfigurationsDelegate.setUIConfigurations();
		}
		
		public function result(data:Object): void
		{		
		}
		
		public function fault(info:Object): void
		{
		}
	}
}