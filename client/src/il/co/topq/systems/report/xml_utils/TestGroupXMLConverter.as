package il.co.topq.systems.report.xml_utils
{
	import il.co.topq.systems.report.models.valueobjects.TestSummary;
	
	import mx.collections.ArrayCollection;

	public class TestGroupXMLConverter
	{
		public static function fromXMLList(xmlList:XMLList, isScenarioSelected:Boolean):ArrayCollection{
			var arrayCollection:ArrayCollection = new ArrayCollection();
			for(var i:int = 0; i< xmlList.length(); i++){
				var testSummary:TestSummary = new TestSummary(xmlList[i]); 
				if (!isScenarioSelected){
					testSummary.scenarioName = "";//delete scenario as it is not configured.
				}
				arrayCollection.addItem(testSummary);	
			}
			return arrayCollection;
	}
}
}