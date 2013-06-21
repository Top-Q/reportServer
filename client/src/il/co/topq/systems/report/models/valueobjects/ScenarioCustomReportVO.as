package il.co.topq.systems.report.models.valueobjects
{
	public class ScenarioCustomReportVO extends CustomReport
	{
		public function ScenarioCustomReportVO(xml:XML=null)
		{
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
			var scenarioCustomReportProperties:XMLList = xml.properties as XMLList ;
			for (var i:int = 0; i<scenarioCustomReportProperties.length(); i++){
				var sp:ScenarioProperty = new ScenarioProperty(scenarioCustomReportProperties[i].propertyKey,scenarioCustomReportProperties[i].propertyValue);
				this.properties.addItem(sp);			
			}
		}
		
		public function clone():ScenarioCustomReportVO {
			return new ScenarioCustomReportVO(xml);
		}
	}
}