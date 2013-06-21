package il.co.topq.systems.report.events.test
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	public class GetScenarioByTestIdEvent  extends CairngormEvent
	{
	
		public static var GET_SCENARIO_BY_TEST_ID_EVENT:String = "getScenarioByTestIdEvent";
		public var callbackFault : Function;
		public var callbackResult : Function;
		public var testId : String;
	
		public function GetScenarioByTestIdEvent(testId:String, callbackResult:Function, callbackFault:Function, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(GET_SCENARIO_BY_TEST_ID_EVENT,bubbles,cancelable);
			this.callbackFault = callbackFault;
			this.callbackResult = callbackResult;
			this.testId = testId;
		}
	}
}
