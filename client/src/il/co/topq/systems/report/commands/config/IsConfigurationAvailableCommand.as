package il.co.topq.systems.report.commands.config
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import flash.xml.XMLNode;
	
	import il.co.topq.systems.report.business.config.ConfigurationDelegate;
	import il.co.topq.systems.report.models.ReportModelLocator;
	
	import mx.controls.Alert;
	import mx.rpc.IResponder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;

	public class IsConfigurationAvailableCommand implements ICommand, IResponder
	{

		private var model: ReportModelLocator = ReportModelLocator.getInstance();

		public function execute(event:CairngormEvent): void
		{
			var configurationDelegate:ConfigurationDelegate = new ConfigurationDelegate(this);
			configurationDelegate.isAvailable();
		}
		
		
		public function result(data:Object):void
		{
			/**
			 * The result from the server should be of an xml like the following:
			 * <bool>false</bool>
			 * or
			 * <bool>true</bool>
			 * If false is recieved that means that no configuration is available and therefore 
			 * the page for configuring should be displayed
			 */  
			var event:ResultEvent = data as ResultEvent;
			if ((event.result as XMLNode).nodeValue == "true"){
				model.applicationState.currentState = "LogIn";		
			}
//			if((event.result as XMLNode).firstChild.nodeValue == "false"){
//				model.applicationState.currentState = "SetupConfiguration"	
//			}
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