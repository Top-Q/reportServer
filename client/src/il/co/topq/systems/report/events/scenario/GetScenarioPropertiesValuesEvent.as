package il.co.topq.systems.report.events.scenario
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.views.components.Summary;
	
	public class GetScenarioPropertiesValuesEvent extends CairngormEvent
	{
		public static var GET_SCENARIO_PROPERTIES_VALUES:String = "getScenarioPropertiesValues";		
		private var scenarioPropertyKeys:String;
		private var summaryWindow:Summary;
		
		public function GetScenarioPropertiesValuesEvent(scenarioPropertyKeys : String, summary : Summary = null, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(GET_SCENARIO_PROPERTIES_VALUES,bubbles,cancelable);
			this.scenarioPropertyKeys = scenarioPropertyKeys;
			this.summaryWindow = summary;
		}
		
		public function get propertyKeys():String{
			return this.scenarioPropertyKeys;
		}
		public function get summary():Summary{
			return this.summaryWindow;
		}
	}
}