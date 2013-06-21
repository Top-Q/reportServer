package il.co.topq.systems.report.events
{
	import com.adobe.cairngorm.control.CairngormEvent;

	public class GetSummaryPageEvent extends CairngormEvent
	{
		public static var GET_SUMMARY_PAGE_EVENT:String = "getSummaryPageEventType";
		private var scenarioQueryVar:XML;
		
		public function GetSummaryPageEvent(scenarioQuery : XML = null, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(GET_SUMMARY_PAGE_EVENT,bubbles,cancelable);
			this.scenarioQueryVar = scenarioQuery;
		}
		public function get scenarioQuery():XML {
			return this.scenarioQueryVar;
		}

	}
}