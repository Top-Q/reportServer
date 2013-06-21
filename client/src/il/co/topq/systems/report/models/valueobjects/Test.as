package il.co.topq.systems.report.models.valueobjects
{
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.formatters.DateFormatter;
	
	import net.customware.flex.util.xml.XMLConverter;

	[Bindable]	
	public class Test
	{

		//Copy of the test data model in the server side
	
		private var xml:XML;
		
		public var status:Number;
		public var failCause:String;
		public var message:String;
		public var packageDescription:PackageDescription;
		public var testDescription:TestDescription;
		public var documentation:String;
		public var count:String;//deprecated.
		public var id:String;
		public var testProperties:ArrayCollection;
		public var duration:String;
		public var startTime:String;
		public var params:String;
		public var endTime:String;
		public var scenarioName:String;
		
		public var isSelected:Boolean = false;
		
		public function clone():Test {
			return new Test(xml);
		}
		
		public function Test(xml:XML = null)
		{
			testProperties = new ArrayCollection();

			packageDescription = new PackageDescription();
			testDescription = new TestDescription();
			if(xml != null){
				this.xml = xml;
				populateTestFromXML();
			}	
		}
		private function populateTestFromXML():void{
			this.duration = xml.duration;
			this.status = xml.status;
			this.startTime = xml.startTime;
			this.endTime = xml.endTime;
			this.params = xml.params;
			this.count = xml.count;
			this.message = xml.message;
			this.scenarioName = xml.scenarioName;
			this.id = xml.@id;
			this.failCause = xml.failCause;
			this.testDescription.testDescription = xml.testDescription.testDescription;
			this.testDescription.testName = xml.testDescription.testName;
			this.packageDescription.packageDescription = xml.packageDescription.packageDescription;
			this.packageDescription.packageName = xml.packageDescription.packageName;
			getTestPropertiesFromXML();
		}
		
		private function getTestPropertiesFromXML():void {
			var properties:XMLList = xml.testProperties.properties as XMLList ;
			for (var i:int = 0; i<properties.length(); i++){
				var tp:TestProperty = new TestProperty(properties[i].propertyKey,properties[i].propertyValue,properties[i].@index1);
				this.testProperties.addItem(tp);
			}
		}
		
		private function testPropertiesToXML():XML{
			var xml:XML = <testProperties></testProperties>;
			var str:String ="";
			for (var i:int = 0; i<testProperties.length; i++){
				xml.appendChild(testProperties[i].toXML());
			}
			return xml;
		}
		
		public function toXML ():XML {
			
			var xml:XML = <test id={id}></test>;
			xml.appendChild(packageDescription.toXML());
			xml.appendChild(testDescription.toXML());
			var str:String = "<message>" + message + "</message>";
			str += "<status>" + status + "</status>";
			str += "<startTime>" + startTime + "</startTime>";
			str += "<endTime>" + endTime + "</endTime>";
			str += "<failCause>" + failCause + "</failCause>";
			str += "<documentation>" + documentation + "</documentation>";
			str += "<params>" + params + "</params>";
			str += "<count>" + count + "</count>";
			str += "<duration>" + duration + "</duration>";
			xml.appendChild(testPropertiesToXML());
			var xMLList:XMLList = XMLList(str);
			xml.appendChild(xMLList);
			return xml;
		}
	}
}