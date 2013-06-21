package il.co.topq.systems.report.events.legacy
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.models.valueobjects.Filter;

	public class LegacyGetScenarioEvent  extends CairngormEvent
	{
		public static var LEGACY_GET_SCENARIO_EVENT:String = "legacyGetScenarioEventType";
		
		public var filter:Filter;
		
		public function LegacyGetScenarioEvent(filter: Filter,bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(LEGACY_GET_SCENARIO_EVENT,bubbles,cancelable);
			this.filter = filter;
		}
	}
}