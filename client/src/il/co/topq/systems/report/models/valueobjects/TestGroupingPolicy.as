package il.co.topq.systems.report.models.valueobjects
{
	import flash.xml.XMLNode;
	
	import mx.rpc.events.XMLLoadEvent;

	/**
	 *This class holds the system settings data.
	 */
	[Bindable]
	public class TestGroupingPolicy
	{
		/**
		 ***** defaultTestGrouping *****
		 * 0 = None; //will not group by!
		 * 1 = test name; //will group tests with same name
		 * 2 = test name + scenario; //will group tests with same name under the same scenario.
		 * 3 = test name + parameters; //will group tests with same name and with the same parameters.
		 * 4 = test name + scenario + parameters; //will group tests with same name and parameters under the same scenario.
		 */ 
		public var groupBy : int;
		
		/**
		 * object's xml representation.
		 */ 
		private var xml:XML;
		
		public function TestGroupingPolicy(systemSettingXML:XML)
		{
			this.xml = systemSettingXML;
			fromXML(systemSettingXML);
		}
		
		public function toXML():XML {
//			var xml:XML = <systemSettings></systemSettings>; //will be used  as the header of the xml created;
			var xml:XML = <testGroupingPolicy></testGroupingPolicy>; //will be used  as the header of the xml created;
			xml.appendChild(getTestGroupingXML());
			return xml;
		}
		
		private function getTestGroupingXML():XML {
			var xml:XML = <testGrouping></testGrouping>;
			xml.appendChild(XMLList(groupBy));
			return xml;
		}
		
		private function fromXML(systemSettingXML:XML):void {
			if (this.xml != null){
				groupBy = systemSettingXML.testGrouping;
//				defaultTestGrouping = systemSettingXML.defaultTestGrouping;
			}
		}
		
		public function clone():TestGroupingPolicy {
			return new TestGroupingPolicy(xml);
		}
	}
}