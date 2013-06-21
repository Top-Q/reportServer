/**
 * This command is responsible to set the first configuration of the report server.
 * If it succeed the next time the report server is called, the login state will appear.
 */ 
package il.co.topq.systems.report.commands.config
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import flash.xml.XMLNode;
	
	import il.co.topq.systems.report.business.config.ConfigurationDelegate;
	import il.co.topq.systems.report.events.config.SetConfigEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	
	import mx.controls.Alert;
	import mx.rpc.IResponder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;

	public class SetConfigCommand implements ICommand,IResponder
	{
		private var model: ReportModelLocator = ReportModelLocator.getInstance();
		
		public function execute(event:CairngormEvent):void
		{
			var configurationDelegate:ConfigurationDelegate = new ConfigurationDelegate(this);
			var e:SetConfigEvent = event as SetConfigEvent;
			configurationDelegate.setSetupConfig(e.username,e.password);
		}
		
		public function result(data:Object):void
		{
			var event:ResultEvent = data as ResultEvent;
			if((event.result as XMLNode).firstChild.nodeValue == "false"){
				model.error.setMessage(null,"Severe Error: " +
					"Cannot Set the configuration. " +
					"Please contact report server vendor.");
			}
			else{
				model.applicationState.currentState = "LogIn";
			}
		}
		
		public function fault(info:Object):void
		{
			var event:FaultEvent = FaultEvent(info);
			model.error.setMessage(event);
			model.applicationState.currentState = "ErrorPage";	
		}
		
	}
}