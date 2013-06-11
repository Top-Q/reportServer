package il.co.topq.systems.report.models.valueobjects
{
	import mx.collections.ArrayCollection;

	[Bindable]
	public class TestCustomReportGridModel extends TestGridModel
	{
		/**
		 * This will represent an array of propertyValuesWrapper.
		 */
		public var testCustomReportPropertiesValues:ArrayCollection; 
		
		public function TestCustomReportGridModel()
		{
			super();
			testCustomReportPropertiesValues = new ArrayCollection();
		}
	}
}