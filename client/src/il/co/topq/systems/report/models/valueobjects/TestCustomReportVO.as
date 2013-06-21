package il.co.topq.systems.report.models.valueobjects
{
	import il.co.topq.systems.report.views.components.reports.TestCustomReport;
	
	import mx.collections.ArrayCollection;

	[Bindable]
	public class TestCustomReportVO extends CustomReport
	{
		
		public function TestCustomReportVO (xml:XML = null){
			super(xml);
		}
		
		/**
		 * This method will parse the customReport xml and will retrieve
		 * The Properties of this object.
		 * Will assign the values into properties:arrayCollection.
		 * @return void;
		 */
		override protected function setPropertiesFromXML():void
		{
			var testCustomReportProperties:XMLList = xml.properties as XMLList ;
			for (var i:int = 0; i<testCustomReportProperties.length(); i++){
				var tp:TestProperty = new TestProperty(testCustomReportProperties[i].propertyKey,testCustomReportProperties[i].propertyValue);
				this.properties.addItem(tp);			
			}
		}
		
		public function clone():TestCustomReportVO {
			return new TestCustomReportVO(xml);
		}
	}
}