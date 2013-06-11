package il.co.topq.systems.report.views.components.reports.scenario
{
	import il.co.topq.systems.report.models.valueobjects.Scenario;
	import il.co.topq.systems.report.views.components.reports.tools.ScenarioDetail;
	
	public class ScenarioDetailsViewModel extends ScenarioDetail
	{
		public function ScenarioDetailsViewModel(scenario:Scenario)
		{
			super();
			this._scenario = scenario;
			this._scenarioClone = scenario.clone();
		}
	}
}