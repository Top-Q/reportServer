package il.co.topq.systems.report.xml_utils
{
	import il.co.topq.systems.report.models.valueobjects.Test;
	
	import mx.collections.ArrayCollection;
	
	import net.customware.flex.util.xml.XMLConverter;

	public class TestXMLConverter
	{
		public static function fromXMLList(xmlList:XMLList):ArrayCollection{
			var arrayCollection:ArrayCollection = new ArrayCollection();
			for(var i:int = 0; i< xmlList.length(); i++){
				var test:Test = new Test (xmlList[i]); 
				arrayCollection.addItem(test);	
			}
			return arrayCollection;
		}
	}
}