package il.co.topq.systems.report.models.valueobjects
{
	import mx.collections.ArrayCollection;
	
	import org.as3commons.collections.Map;

	[Bindable]
	public class TestQuery
	{
		public var chunk:Chunk;
		public var timeRange:TimeRange;
		public var testProperties:ArrayCollection;
		public var sortingColumn:SortingColumn;
		/**
		 * 0 = None; //will not group by!<br>
		 * 1 = test name; //will group tests with same name<br>
		 * 2 = test name + scenario; //will group tests with same name under the same scenario.<br>
		 * 3 = test name + parameters; //will group tests with same name and with the same parameters.<br>
		 * 4 = test name + scenario + parameters; //will group tests with same name and parameters under the same scenario.<br>
		 */ 
		public var groupBy:int = 0;
		
		public function TestQuery()
		{
			timeRange = new TimeRange();
			chunk = new Chunk(0,30);//need to change this. chunk size should be customized.
			sortingColumn = new SortingColumn();
			testProperties = new ArrayCollection();
		}
		
		public function toXML():XML {
			var xml:XML = <testQuery></testQuery>; //will be used  as the header of the xml created;
			xml.appendChild(getGroupByXML());
			if (chunk != null){
				xml.appendChild(chunk.toXML());
			}
			if (timeRange != null){
				xml.appendChild(timeRange.toXML());	
			}
			if (sortingColumn != null){
				xml.appendChild(sortingColumn.toXML());
			}
			xml.appendChild(testPropertiesToXML());
			return xml;
		}
		
		private function getGroupByXML():XML{
			var xml:XML = <groupBy></groupBy>;
			xml.appendChild(XMLList(groupBy));
			return xml;
		}
		
		private function testPropertiesToXML():XML {
			var propertiesXML:XML = <testProperties></testProperties>;
			for (var i:int=0; i<testProperties.length; i++){	
				var testProperty:TestProperty = testProperties[i] as TestProperty;
				var testPropertyXML:XML = testProperty.toXML();
				propertiesXML.appendChild(testPropertyXML);
			}
			return propertiesXML;
		}
	}
	
}