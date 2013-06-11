package il.co.topq.systems.report.events.scenario
{
	import com.adobe.cairngorm.control.CairngormEvent;

	public class CompareScenarioEvent extends CairngormEvent
	{
		public static var COMPARE_SCENARIO_EVENT:String = "CompareScenarioEvent";		
		public var comparedScenarios:String;
		public var callbackResult:Function;
		public var callbackFault:Function;
		
		public function CompareScenarioEvent(comparedScenarios : String, callbackResult:Function, callbackFault:Function, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(COMPARE_SCENARIO_EVENT,bubbles,cancelable);
			this.comparedScenarios = comparedScenarios;
			this.callbackFault = callbackFault;
			this.callbackResult = callbackResult;
		}
	}
}