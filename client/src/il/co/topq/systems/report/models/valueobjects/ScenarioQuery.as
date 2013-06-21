/**
 * This objects represents the state of a specific grid containing a list of scenarios
 */
package il.co.topq.systems.report.models.valueobjects
{
	import il.co.topq.systems.report.models.ReportModelLocator;
	
	import mx.collections.ArrayCollection;
	
	import org.as3commons.collections.Map;

	public class ScenarioQuery
	{
		public function ScenarioQuery(){
			timeRange = new TimeRange();
			chunk = new Chunk(0,30);			
			sortingColumn = new SortingColumn();
			scenarioProperties = new ArrayCollection();
		}
	
		public function toXML():XML {
			var xml:XML = <scenarioQuery></scenarioQuery>; //will be used  as the header of the xml created;
			if (chunk != null){
				xml.appendChild(chunk.toXML());
			}
			if (timeRange != null){
				xml.appendChild(timeRange.toXML());	
			}
			if (sortingColumn != null){
				xml.appendChild(sortingColumn.toXML());
			}
			xml.appendChild(scenarioPropertiesToXML());
			return xml;
		}
		
		private function scenarioPropertiesToXML():XML {
			var propertiesXML:XML = <scenarioProperties></scenarioProperties>;
			for (var i:int=0; i<scenarioProperties.length; i++){	
				var scenarioProperty:ScenarioProperty = scenarioProperties[i] as ScenarioProperty;
				var scenarioPropertyXML:XML = scenarioProperty.toXML();
				propertiesXML.appendChild(scenarioPropertyXML);
			}
			return propertiesXML;
		}
		
		//The amount of scenarios to be returned
		public var chunk:Chunk;
		
		//The timerange of the selected scenarios
		public var timeRange:TimeRange;
		
		public var scenarioProperties:ArrayCollection;
		
		
		//When selecting a scenario they will be order using this object which contains 
		//the name of the column and the type of ordering (asc=true/false)
		public var sortingColumn:SortingColumn;
		
		
		
	}
}