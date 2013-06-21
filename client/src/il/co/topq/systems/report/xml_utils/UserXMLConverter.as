package il.co.topq.systems.report.xml_utils
{
	import il.co.topq.systems.report.models.valueobjects.User;
	
	import mx.collections.ArrayCollection;

	public class UserXMLConverter
	{
		public static function fromXMLList(xmlList:XMLList):ArrayCollection {
			var arrayCollection:ArrayCollection = new ArrayCollection();
			for(var i:int = 0; i< xmlList.length(); i++){
				var user:User= new User(xmlList[i]); 
				arrayCollection.addItem(user);	
			}
			return arrayCollection;
		}
	}
}