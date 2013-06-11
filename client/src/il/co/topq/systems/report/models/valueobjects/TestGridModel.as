package il.co.topq.systems.report.models.valueobjects
{
	import mx.collections.ArrayCollection;

	[Bindable]
	public class TestGridModel
	{
		public var navigation:NavigationVO;
		public var testQuery:TestQuery;
		public var gridConfiguration:GridConfiguration;
		public var gridData:GridData;
		
		public function TestGridModel()
		{
			this.navigation = new NavigationVO();
			this.testQuery = new TestQuery();
			this.gridConfiguration = new GridConfiguration();
			this.gridData = new GridData();
		}
	}
}