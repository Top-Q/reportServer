package il.co.topq.systems.report.views.components.reports.common
{
	import flash.events.Event;
	import flash.events.MouseEvent;
	
	import il.co.topq.systems.report.events.general.DeleteTestEvent;
	import il.co.topq.systems.report.events.general.UpdateEvent;
	import il.co.topq.systems.report.events.scenario.GetScenarioTestsEvent;
	import il.co.topq.systems.report.events.scenario.GetScenarioTestsSizeEvent;
	import il.co.topq.systems.report.events.test.DeleteTestButtonClickEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	import il.co.topq.systems.report.models.valueobjects.Scenario;
	import il.co.topq.systems.report.views.components.reports.tools.ScenarioTests;
	
	import mx.controls.Alert;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	
	public class ScenarioTestsViewModel extends ScenarioTests
	{
		[Bindable]
		private var scenario:Scenario; //this parameter will indicate which scenario tests to fetch.
		
		public function ScenarioTestsViewModel(scenario:Scenario)
		{
			super();
			this.scenario = scenario;
			testGridModel.testQuery.sortingColumn.asc=true;//when fetching scenario's tests the default sorting mode is start time asc.
			getResults();
			
		}
		
		override public function getResults():void {
			new GetScenarioTestsSizeEvent(scenario.id,testGridModel).dispatch();
			new GetScenarioTestsEvent(scenario.id,testGridModel).dispatch();
		}
		
		override public function handleNextClicked(event:MouseEvent):void{
			if (testGridModel.navigation.index < testGridModel.gridData.numberOfPages){
				testGridModel.navigation.index++;
				testGridModel.testQuery.chunk.length = testGridModel.gridConfiguration.numberOfRows;
				testGridModel.testQuery.chunk.startIndex = testGridModel.navigation.index * testGridModel.gridConfiguration.numberOfRows;
				getResults();
			}
		}
		
		override public function handlePreviousClicked(event:MouseEvent):void{
			if (testGridModel.navigation.index > 0){
				testGridModel.navigation.index--;
				testGridModel.testQuery.chunk.length = testGridModel.gridConfiguration.numberOfRows;
				testGridModel.testQuery.chunk.startIndex = testGridModel.navigation.index * testGridModel.gridConfiguration.numberOfRows;
				getResults();
			}
		}
		
		/**
		 * This method is a handler for the updateTest event.<br>
		 * if an updateTest event is dispatched, it means the grid's data is no longer
		 * up to date, there for fetching the last query from DB is needed.<br>;
		 * It will call the getResults method and bubble up the event for the parent container.<Br>;
		 */
		override protected function updateTestHandler(event:Event):void
		{
			getResults();
			dispatchEvent(event);
		}
		
		/**
		 * This method will be called upon the click on the delete test button,
		 * in testsGrid, after the confirmation of the deletion operation in 
		 * the dialog box.<br>;
		 * This method will dispatch a delete test event.
		 */ 
		override protected function deleteTestButtonClickHandler(event:DeleteTestButtonClickEvent):void
		{
			new DeleteTestEvent(event.testID,deleteTestCallbackResult,deleteTestCallbackFault).dispatch();
		}
		
		/**
		 * This method will be called upon the completion of the deleteTest event.<br>
		 * The deletion is successful and will refresh the grid's data, and will <br>
		 * dispatch an update event for the parent container.
		 */ 
		protected function deleteTestCallbackResult(data:ResultEvent): void
		{
			Alert.show('Test was deleted successfully', '', mx.controls.Alert.OK);
			getResults();
			var updateEvent:UpdateEvent = new UpdateEvent(true);
			dispatchEvent(updateEvent);
		}
		
		/**
		 * This method will be called if the deleteTest event fails to delete the test.<br>;
		 */ 
		protected function deleteTestCallbackFault(info:Object): void
		{
			var model:ReportModelLocator = ReportModelLocator.getInstance();
			var event:FaultEvent = FaultEvent(info);
			model.error.setMessage(event,"Test could not be deleted - internal error");
			model.applicationState.currentState = "ErrorPage";	
		}
	}
}