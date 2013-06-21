package il.co.topq.systems.report.models.valueobjects
{
	import il.co.topq.systems.report.models.valueobjects.TestProperty;

	[Bindable]
	public class TestProperty
	{
		
		public var propertyKey:String;		
		public var propertyValue:String;
		public var index1:String;
		
		public function TestProperty(propertyKey:String ="",propertyValue:String = "",index1:String = "")
		{
			this.propertyKey = propertyKey;
			this.propertyValue = propertyValue;
			this.index1 = index1;
		}
		
		public function toString():String {
			return this.propertyKey + ": " + this.propertyValue; 
		}
		
		public function toXML():XML {
			var xml:XML;
			if (index1 == ""){ //new property created in UI. index from DB not given yet.
				xml = <properties xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="testProperty"></properties>;
			}else { //updated property or existing property.
				xml = <properties index1={index1} xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="testProperty"></properties>;
			}
			var str:String = "";
			str += "<propertyKey>" + propertyKey + "</propertyKey>";
			str += "<propertyValue>" + propertyValue + "</propertyValue>";
			var xMLList:XMLList = XMLList(str);
			xml.appendChild(xMLList);
			return xml;
		}
		
		public function clone():TestProperty{
			return new TestProperty(this.propertyKey,this.propertyValue,this.index1);
		}
	}
}