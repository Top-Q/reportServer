package il.co.topq.systems.report.models.valueobjects
{	
	[Bindable]
	public class ScenarioProperty
	{
		public var propertyKey:String;		
		public var propertyValue:String;
		public var index1:String;
		
		public function ScenarioProperty(propertyKey:String ="",propertyValue:String = "", index1:String = "")
		{
			this.propertyKey = propertyKey;
			this.propertyValue = propertyValue;
			this.index1 = index1;
		}
		
		public function toXML():XML {
			
			var xml:XML;
			if (index1 == ""){ //new property created in UI. index from DB not given yet.
				xml = <properties xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="scenarioProperty"></properties>;
			}else { //updated property or existing property.
				xml = <properties index1={index1} xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="scenarioProperty"></properties>;
			}
			var str:String = "";
			str += "<propertyKey>" + propertyKey + "</propertyKey>";
			str += "<propertyValue>" + propertyValue + "</propertyValue>";
			var xMLList:XMLList = XMLList(str);
			xml.appendChild(xMLList);
			return xml;
		}
		
		public function toString():String {
			return this.propertyKey + ": " + this.propertyValue; 
		}
		public function clone():ScenarioProperty
		{
			return new ScenarioProperty(this.propertyKey,this.propertyValue,this.index1);
		}
	}
}