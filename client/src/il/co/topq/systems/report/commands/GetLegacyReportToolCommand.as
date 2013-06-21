package il.co.topq.systems.report.commands
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.general.GetBuildDelegate;
	import il.co.topq.systems.report.business.general.GetScenarioNamesDelegate;
	import il.co.topq.systems.report.business.general.GetSutDelegate;
	import il.co.topq.systems.report.business.general.GetUserDelegate;
	import il.co.topq.systems.report.business.general.GetVersionsDelegate;
	import il.co.topq.systems.report.models.ReportModelLocator;
	
	import mx.rpc.IResponder;

	public class GetLegacyReportToolCommand implements ICommand
	{
		private var model: ReportModelLocator = ReportModelLocator.getInstance();
		
		
		public function execute(event:CairngormEvent): void
		{
			var evt:GetLegacyReportToolCommand = event as GetLegacyReportToolCommand;
			var getVersionsDelegate:GetVersionsDelegate = new GetVersionsDelegate();
			getVersionsDelegate.get();
			var getBuildDelegate:GetBuildDelegate = new GetBuildDelegate();
			getBuildDelegate.get();
			var getScenarioNamesDelegate:GetScenarioNamesDelegate = new GetScenarioNamesDelegate();
			getScenarioNamesDelegate.get();
			var getUserDelegate:GetUserDelegate = new GetUserDelegate();
			getUserDelegate.get();
			var getSutDelegate:GetSutDelegate = new GetSutDelegate();
			getSutDelegate.get();
		}

	}
}



