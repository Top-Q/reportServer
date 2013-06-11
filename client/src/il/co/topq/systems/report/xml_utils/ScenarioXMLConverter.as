package il.co.topq.systems.report.xml_utils
{
	import mx.collections.ArrayCollection;
	import il.co.topq.systems.report.models.valueobjects.Scenario;

	public class ScenarioXMLConverter
	{
		public static function fromXMLList(xmlList:XMLList):ArrayCollection {
			var arrayCollection:ArrayCollection = new ArrayCollection();
			for(var i:int = 0; i< xmlList.length(); i++){
				var scenario:Scenario = new Scenario (xmlList[i]); 
				arrayCollection.addItem(scenario);	
			}
			return arrayCollection;
		}
	}
}