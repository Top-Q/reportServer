package il.co.topq.systems.report.views.components.reports.grids
{
	import flash.events.Event;
	import flash.events.MouseEvent;
	
	import il.co.topq.systems.report.events.scenario.GetScenarioTestsEvent;
	import il.co.topq.systems.report.events.scenario.GetScenarioTestsSizeEvent;
	import il.co.topq.systems.report.models.valueobjects.Scenario;

	public class ScenarioTestsViewModel extends ScenarioTests
	{
		public var scenario:Scenario
		
		public function ScenarioTestsViewModel(scenario:Scenario)
		{
			super();
			this.scenario = scenario;
			this.title = setScenarioTitle();
			getResults();
			
		}
		
		/**
		 * This method will set the title for the scope of the scenario Tests in the following pattern:<br>
		 * Scenario Tests<br>
		 * Scenario Name: "scenarioName"<br>
		 */
		private function setScenarioTitle():String
		{
			return "Scenario Tests \n" + "Scenario Name: " + scenario.scenarioName;
		}
		
		override protected function updateTestHandler(event:Event):void {
			getResults();
			dispatchEvent(event);
		}
		
		/**
		 * This method will fetsch the result according to the current chunk.<br>
		 * It will fetch the tests of the specific scenario using the scenario ID.
		 */ 
		override protected function getResults():void {
			new GetScenarioTestsSizeEvent(scenario.id,testGridModel).dispatch();
			new GetScenarioTestsEvent(scenario.id,testGridModel).dispatch();
		}
		
		/**
		 * This method will be called uppon a click in a NEXT componenet in the navigation componenet composed in the scenario Tests.<br>
		 * It will update the chunk length and start index.<br>
		 */ 
		override protected function handleNextClicked(event:MouseEvent):void{
			if (testGridModel.navigation.index < testGridModel.gridData.numberOfPages){
				testGridModel.navigation.index++;
				testGridModel.testQuery.chunk.length = testGridModel.gridConfiguration.numberOfRows;
				testGridModel.testQuery.chunk.startIndex = testGridModel.navigation.index * testGridModel.gridConfiguration.numberOfRows;
				getResults();
			}
		}
		
		/**
		 * This method will be called uppon a click in a PREVIOUS componenet in the navigation componenet composed in the scenario Tests.<br>
		 * It will update the chunk length and start index.<br>
		 */
		override protected function handlePreviousClicked(event:MouseEvent):void{
			if (testGridModel.navigation.index > 0){
				testGridModel.navigation.index--;
				testGridModel.testQuery.chunk.length = testGridModel.gridConfiguration.numberOfRows;
				testGridModel.testQuery.chunk.startIndex = testGridModel.navigation.index * testGridModel.gridConfiguration.numberOfRows;
				getResults();
			}
		}
	}
}