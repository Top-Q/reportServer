package il.co.topq.systems.report.events.scenario
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	public class UpdateScenarioEvent extends CairngormEvent
	{
		public static var UPDATE_SCENARIO_EVENT:String = "updateScenarioEvent";
		public var scenarioXML:XML;
		public var callbackResult:Function;
		public var callbackFault:Function;
		
		public function UpdateScenarioEvent(scenarioXML:XML, callbackResult:Function, callbackFault:Function, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(UPDATE_SCENARIO_EVENT,bubbles,cancelable);
			this.scenarioXML = scenarioXML;
			this.callbackResult = callbackResult;
			this.callbackFault = callbackFault;
		}
	}
}