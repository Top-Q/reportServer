package il.co.topq.systems.report.events.scenario
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.models.valueobjects.TestGridModel;
	
	public class GetScenarioTestsSizeEvent extends CairngormEvent
	{
		public static var GET_SCENARIO_TESTS_SIZE_EVENT:String = "GetScenarioTestsSizeEvent";
		public var scenarioID:String;
		public var testGridModel:TestGridModel;
		
		public function GetScenarioTestsSizeEvent(id:String, testGridModel:TestGridModel, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(GET_SCENARIO_TESTS_SIZE_EVENT,bubbles,cancelable);
			this.scenarioID = id;
			this.testGridModel = testGridModel;
		}
	}
}