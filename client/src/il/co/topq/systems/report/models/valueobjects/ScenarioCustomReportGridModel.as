package il.co.topq.systems.report.models.valueobjects
{
	import mx.collections.ArrayCollection;

	[Bindable]
	public class ScenarioCustomReportGridModel extends ScenarioGridModel
	{
		/**
		 * This will represent an array of propertyValuesWrapper.
		 */
		public var scenarioCustomReportPropertiesValues:ArrayCollection; 
		
		public function ScenarioCustomReportGridModel()
		{
			super();
			scenarioCustomReportPropertiesValues = new ArrayCollection();
		}
	}
}