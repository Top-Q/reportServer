package il.co.topq.systems.report.models.valueobjects
{
	[Bindable]
	public class TestSummary
	{
		//the xml representation of the object.
		private var xml:XML;
		
		public var testName:String;
		public var total:String;
		public var passed:String;
		public var failed:String;
		public var warned:String;
		public var scenarioName:String;
		public var params:String;
		public var packageName:String;
		
		public function TestSummary(xml:XML)
		{
			if(xml != null){
				this.xml = xml;
				populateTestFromXML();
			}
		}
		
		public function clone():TestSummary {
			return new TestSummary(xml);
		}
		
		private function populateTestFromXML():void
		{
			this.testName = xml.testName;
			this.scenarioName = xml.scenarioName;
			this.total = xml.total;
			this.passed = xml.pass;
			this.failed = xml.fail;
			this.warned = xml.warning;
			this.params = xml.params;
			this.packageName = xml.packageName;
		}
		
		public function toXML():XML {
			var xml:XML = <testSummary></testSummary>;
			var str:String = "";
			str += "<testName>" + testName + "</testName>";str
			str += "<scenarioName>" + scenarioName + "</scenarioName>";
			str += "<total>" + total + "</total>";
			str += "<pass>" + passed + "</pass>";
			str += "<fail>" + failed + "</fail>";
			str += "<warning>" + warned + "</warning>";
			str += "<params>" + params + "</params>";
			str += "<packageName>" + packageName + "</packageName>";
			
			return xml.appendChild(XMLList(str));
		}
	}
}