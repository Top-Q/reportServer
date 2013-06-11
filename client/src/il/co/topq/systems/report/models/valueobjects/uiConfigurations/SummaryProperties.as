package il.co.topq.systems.report.models.valueobjects.uiConfigurations
{
	import mx.collections.ArrayCollection;
	import mx.controls.dataGridClasses.DataGridColumn;

	public class SummaryProperties
	{
		private var xml:XML;
		private var name:String;
		public var properties:ArrayCollection;
		public function SummaryProperties(name:String, xml:XML = null)
		{
			this.xml = xml;
			this.name = name;
			this.properties = new ArrayCollection();
			fromXml();
		}
		
		private function fromXml() : void 
		{
			if (this.xml != null){
				var properties : XMLList = this.xml.properties.property as XMLList;
				for (var i:int = 0; i<properties.length(); i++){
					this.properties.addItem(properties[i]);
				}
			}
		}
		
		public function toXML(): XML{
			var xml:XML = <{this.name}></{this.name}>;
			var propertiesXml:XML = <properties></properties>;
			var xmlList:XMLList;
			var str:String = "";
			
			for (var j:int = 0; j<this.properties.length; j++){
				str += "<property>" + (this.properties[j])+ "</property>";
			}
			xmlList = XMLList(str);
			propertiesXml.appendChild(xmlList);
			xml.appendChild(propertiesXml);
			return xml;
		}
		
		public function toString():String {
			var propertiesStr:String = "";
			for (var i:int =0; i<this.properties.length; i++){
				propertiesStr += this.properties[i];
				propertiesStr += ",";
			}
			return propertiesStr;
		}
	}
}