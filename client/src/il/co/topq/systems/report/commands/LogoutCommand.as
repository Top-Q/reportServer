package il.co.topq.systems.report.commands
{
	import com.adobe.cairngorm.business.ServiceLocator;
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import flash.profiler.showRedrawRegions;
	
	import il.co.topq.systems.report.events.LoginEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	import il.co.topq.systems.report.services.ReportServiceLocator;
	
	import mx.controls.Alert;
	import mx.core.FlexGlobals;
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.http.HTTPService;
	
	import org.osmf.utils.URL;

		public class LogoutCommand implements ICommand
		{
			
			private var model: ReportModelLocator = ReportModelLocator.getInstance();
			
			
			public function execute(event:CairngormEvent): void
			{
				model.applicationState.currentState = "LogIn";
			}
			
		}
}