package il.co.topq.systems.report.models.valueobjects
{
	[Bindable]
	public class ScenarioGridModel
	{
		
		public var navigation:NavigationVO;
		
		public var scenarioQuery:ScenarioQuery;
		
		public var gridConfiguration:GridConfiguration;
		
		public var gridData:GridData;
		
		public function ScenarioGridModel()
		{
			this.navigation = new NavigationVO();
			this.scenarioQuery = new ScenarioQuery();
			this.gridConfiguration = new GridConfiguration();
			this.gridData = new GridData();
		}
	}
}