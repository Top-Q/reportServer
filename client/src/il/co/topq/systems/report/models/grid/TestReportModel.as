package il.co.topq.systems.report.models.grid
{
	import il.co.topq.systems.report.models.valueobjects.TestQuery;

	public class TestReportModel
	{
		[Bindable]
		public var testQuery:TestQuery;
		
		[Bindable]
		public var currentData:XML;
		
		
		public function TestReportModel()
		{
			testQuery = new TestQuery();
		}
	}
}