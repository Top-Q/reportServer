package il.co.topq.systems.report.events.test
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.views.components.reports.tools.TestDetail;
	
	public class GetTestScenarioEvent extends CairngormEvent
	{
		public static var GET_TEST_SCENARIO_EVENT:String = "getTestScenarioEvent";
		public var testID:String;
//		private var testDetailWindow:TestDetail;
		public var callbackResult:Function;
		public var callbackFault:Function;
		
//		public function GetTestScenarioEvent(testID : String, testDetailWindow : TestDetail = null, bubbles : Boolean = false, cancelable : Boolean = false) {
		public function GetTestScenarioEvent(testID : String, callbackResult:Function, callbackFault:Function, bubbles : Boolean = false, cancelable : Boolean = false) {
			super(GET_TEST_SCENARIO_EVENT,bubbles,cancelable);
			this.testID = testID;
			this.callbackFault = callbackFault;
			this.callbackResult = callbackResult;
//			this.testDetailWindow = testDetailWindow;
		}
//		public function getTestDetailWindow():TestDetail{
//			return this.testDetailWindow;
//		}
	}
}
