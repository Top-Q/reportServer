package il.co.topq.systems.report.events.scenario
{
	import com.adobe.cairngorm.control.CairngormEvent;
	import il.co.topq.systems.report.models.valueobjects.Filter;
	
	public class RefreshScenarioReportEvent extends CairngormEvent
	{
		public static var REFRESH_SCENARIO_REPORT:String = "RefreshScenarioReport";		
		public var filter:Filter;
		
		public function RefreshScenarioReportEvent(bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(REFRESH_SCENARIO_REPORT,bubbles,cancelable);
			this.filter = filter;
		}

	}
}

