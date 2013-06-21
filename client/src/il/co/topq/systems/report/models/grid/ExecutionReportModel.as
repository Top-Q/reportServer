package il.co.topq.systems.report.models.grid
{
	import il.co.topq.systems.report.models.valueobjects.ScenarioQuery;

	public class ExecutionReportModel
	{
		[Bindable]
		public var scenarioQuery:ScenarioQuery;
		
		[Bindable]
		public var currentData:XML;
		
		public function ExecutionReportModel()
		{
			scenarioQuery = new ScenarioQuery();
		}
	}
}