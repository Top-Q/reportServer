package il.co.topq.systems.report.views.components.reports.viewModel
{
	import flash.events.MouseEvent;
	
	import il.co.topq.systems.report.events.general.RefreshCustomReportGridEvent;
	import il.co.topq.systems.report.events.scenarioCustomReport.GetScenarioCustomReportByChunkEvent;
	import il.co.topq.systems.report.events.scenarioCustomReport.GetScenarioCustomReportSizeEvent;
	import il.co.topq.systems.report.views.components.reports.ScenarioCustomReport;
	import il.co.topq.systems.report.xml_utils.CustomReportXMLConverter;
	
	import mx.controls.Alert;
	import mx.rpc.events.ResultEvent;
	
	public class ScenarioCustomReportViewModel extends ScenarioCustomReport
	{
		public function ScenarioCustomReportViewModel()
		{
			super();
			this.addEventListener(RefreshCustomReportGridEvent.REFRESH_CUSTOM_REPORT_GRID_EVENT,refreshCustomReportGridHandler);
			getResults();
		}
		
		protected function refreshCustomReportGridHandler(event:RefreshCustomReportGridEvent):void
		{
			if (event.customReportType == "execution"){
				getResults();	
			}
		}
		
		public function getResults():void {
			new GetScenarioCustomReportSizeEvent(scenarioGridModel).dispatch();
			new GetScenarioCustomReportByChunkEvent(scenarioGridModel, GetScenarioCustomReportCallbackResult, GetScenarioCustomReportCallbackFault).dispatch();				
		}
		
		public function GetScenarioCustomReportCallbackResult(data:Object): void
		{			
			var evt:ResultEvent = data as ResultEvent;
			var xmlList:XMLList = (new XML(evt.message.body)).customReports;
			scenarioGridModel.gridData.items = CustomReportXMLConverter.getScenarioCustomReportsFromXMLList(xmlList); 
			scenarioCustomReportGrid.dataProvider = scenarioGridModel.gridData.items;
		}
		
		public function GetScenarioCustomReportCallbackFault(info:Object): void
		{
			Alert.show("GetCustomReportCommand service was not initialized correctly");
		}
		
		/**
		 * This method will be called uppon a click in a NEXT componenet in the navigation componenet composed in the testCustomReport.<br>
		 * It will update the chunk length and start index.<br>
		 */ 
		override protected function handleNextClicked(event:MouseEvent):void{
			if (scenarioGridModel.navigation.index < scenarioGridModel.gridData.numberOfPages){
				scenarioGridModel.navigation.index++;
				scenarioGridModel.scenarioQuery.chunk.length = scenarioGridModel.gridConfiguration.numberOfRows;
				scenarioGridModel.scenarioQuery.chunk.startIndex = scenarioGridModel.navigation.index * scenarioGridModel.gridConfiguration.numberOfRows;
				getResults();
			}
		}
		
		/**
		 * This method will be called uppon a click in a PREVIOUS componenet in the navigation componenet composed in the testCustomReport.<br>
		 * It will update the chunk length and start index.<br>
		 */
		override protected function handlePreviousClicked(event:MouseEvent):void{
			if (scenarioGridModel.navigation.index > 0){
				scenarioGridModel.navigation.index--;
				scenarioGridModel.scenarioQuery.chunk.length = scenarioGridModel.gridConfiguration.numberOfRows;
				scenarioGridModel.scenarioQuery.chunk.startIndex = scenarioGridModel.navigation.index * scenarioGridModel.gridConfiguration.numberOfRows;
				getResults();
			}
		}
	}
}