package il.co.topq.systems.report.events.scenario
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.models.grid.TestReportModel;
	import il.co.topq.systems.report.models.valueobjects.Chunk;
	import il.co.topq.systems.report.models.valueobjects.TestGridModel;

	public class GetScenarioTestsEvent extends CairngormEvent
	{
		public static var GET_SCENARIO_TESTS_EVENT:String = "GetScenarioTestsEvent";
		public var scenarioID:String;
		public var testGridModel:TestGridModel;
		
		public function GetScenarioTestsEvent(scenarioID:String, testGridModel:TestGridModel = null, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(GET_SCENARIO_TESTS_EVENT,bubbles,cancelable);
			this.scenarioID = scenarioID;
			this.testGridModel = testGridModel;
		}
	}
}