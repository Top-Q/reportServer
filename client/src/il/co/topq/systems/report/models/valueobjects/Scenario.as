package il.co.topq.systems.report.models.valueobjects
{
	import mx.collections.ArrayCollection;
	import mx.formatters.DateFormatter;

	[Bindable]
	public class Scenario
	{
		private var xml:XML;
		public var id:String;
		public var scenarioName:String;
		public var startTime:String;
		public var duration:String;
		public var total:String;
		public var pass:String;
		public var fail:String;
		public var warn:String;
		public var description:String;
		public var htmlDir:String;
		public var scenarioProperties:ArrayCollection;
		public var tests:ArrayCollection;
		
		public var isSelected:Boolean = false;//is selected within grid.
		
		public function Scenario(xml:XML = null)
		{
			scenarioProperties = new ArrayCollection();
			tests = new ArrayCollection();
			if(xml != null){
				this.xml = xml;
				populateScenarioFromXML();
			}	
		}
		
		private function scenarioPropertiesToXML():XML{
			var xml:XML = <scenarioProperties></scenarioProperties>;
			var str:String ="";
			for (var i:int = 0; i<scenarioProperties.length; i++){
				xml.appendChild(scenarioProperties[i].toXML());
			}
			return xml;
		}
		
		public function toXML ():XML {
			
			var xml:XML = <scenario id={id}></scenario>;
			var str:String = "";
			str += "<scenarioName>" + scenarioName + "</scenarioName>";
			str += "<startTime>" + startTime + "</startTime>";
			str += "<duration>" + duration + "</duration>";
			str += "<runTests>" + total + "</runTests>";
			str += "<failTests>" + fail + "</failTests>";
			str += "<successTests>" + pass + "</successTests>";
			str += "<warningTests>" + warn + "</warningTests>";
			str += "<description>" + description + "</description>";
			str += "<htmlDir>" + htmlDir + "</htmlDir>";
			xml.appendChild(scenarioPropertiesToXML());
			var xMLList:XMLList = XMLList(str);
			xml.appendChild(xMLList);
			return xml;
		}
		
		private function populateScenarioFromXML():void
		{
			this.scenarioName = xml.scenarioName;
			this.id = xml.@id;
			this.total = xml.runTest;
			this.pass = xml.successTests;
			this.fail = xml.failTests;
			this.warn = xml.warningTests;
			this.duration = xml.duration;
			this.description = xml.description;
			this.startTime = xml.startTime;
			this.htmlDir = xml.htmlDir;
			getScenarioPropertiesFromXML();
			getScenarioTestsFromXML();
		}
		
		private function getScenarioTestsFromXML():void {
			var scenarioTests : XMLList = xml.tests as XMLList ;
			for (var i:int = 0; i<scenarioTests.length(); i++){
				this.tests.addItem(new Test(scenarioTests[i]));
			}
		}
		
		private function getScenarioPropertiesFromXML(): void {
			var properties:XMLList = xml.scenarioProperties.properties as XMLList ;
			for (var i:int = 0; i<properties.length(); i++){
				var sp:ScenarioProperty = new ScenarioProperty(properties[i].propertyKey, properties[i].propertyValue);
				this.scenarioProperties.addItem(sp);
			}
		}
		
		public function clone():Scenario {
			return new Scenario(xml);
		}
	
	}
}