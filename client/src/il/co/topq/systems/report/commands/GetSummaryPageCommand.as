package il.co.topq.systems.report.commands
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.scenario.GetScenariosByFilterEventDelegate;
	import il.co.topq.systems.report.business.summary.GetExecutionStatisticsDelegate;
	import il.co.topq.systems.report.business.summary.GetLastExecutionVersionDelegate;
	import il.co.topq.systems.report.business.summary.GetScenarioStatisticsByTimeRangeDelegate;
	import il.co.topq.systems.report.business.summary.GetTotalNumberOfScenarioDelegate;
	import il.co.topq.systems.report.business.summary.GetTotalNumberOfTestsDelegate;
	import il.co.topq.systems.report.events.GetSummaryPageEvent;
	import il.co.topq.systems.report.events.scenario.GetScenariosByFilterEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	
	import mx.controls.Alert;
	import mx.rpc.events.FaultEvent;

	public class GetSummaryPageCommand implements ICommand
	{
		public function execute(event:CairngormEvent): void
		{
			var evt:GetSummaryPageEvent = event as GetSummaryPageEvent;
			var getTotalScenarioDelegate:GetTotalNumberOfScenarioDelegate = new GetTotalNumberOfScenarioDelegate();
			getTotalScenarioDelegate.getTotalNumberOfScenarios(evt.scenarioQuery);
			var getTotalNumberOfTestsDelegate:GetTotalNumberOfTestsDelegate = new GetTotalNumberOfTestsDelegate();
			getTotalNumberOfTestsDelegate.getTotalNumberOfTests(evt.scenarioQuery);
			var getExecutionStatisticsDelegate :GetExecutionStatisticsDelegate = new GetExecutionStatisticsDelegate();
			getExecutionStatisticsDelegate.getExecutionStatistics(evt.scenarioQuery);
			var getScenarioStatisticsByTimeRangeDelegate:GetScenarioStatisticsByTimeRangeDelegate = new GetScenarioStatisticsByTimeRangeDelegate();
			getScenarioStatisticsByTimeRangeDelegate.getScenarioStatisticsByTimeRange(evt.scenarioQuery);
/*			var getLastExecutionVersionDelegate:GetLastExecutionVersionDelegate = new GetLastExecutionVersionDelegate();
			getLastExecutionVersionDelegate.getLastExecutionVersion();	*/		
		}
		

	}
}