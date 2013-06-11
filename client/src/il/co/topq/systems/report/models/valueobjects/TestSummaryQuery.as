package il.co.topq.systems.report.models.valueobjects
{
	public class TestSummaryQuery
	{
		public var testQuery:TestQuery;
		public var testSummary:TestSummary;
		
		public function TestSummaryQuery(testQuery:TestQuery, testSummary:TestSummary){
			this.testQuery = testQuery;
			this.testSummary = testSummary;
		}
		
		public function toXML():XML{
			var xml:XML = <testSummaryQuery></testSummaryQuery>;
			xml.appendChild(testQuery.toXML());
			xml.appendChild(testSummary.toXML());
			return xml;
		}
	}
}