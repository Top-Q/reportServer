package il.co.topq.systems.report.events.general
{
	import com.adobe.cairngorm.control.CairngormEvent;

	public class DeleteScenarioEvent extends CairngormEvent
	{
		public static var DELETE_SCENARIO_EVENT:String = "deleteScenarioEventType";
		public var scenarioID:String;
		public var callbackResult:Function;
		public var callbackFault:Function;
		
		public function DeleteScenarioEvent(scenarioID: String, callbackResult:Function, callbackFault:Function, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(DELETE_SCENARIO_EVENT,bubbles,cancelable);
			this.scenarioID = scenarioID;
			this.callbackFault = callbackFault;
			this.callbackResult = callbackResult;
		}
	}
}