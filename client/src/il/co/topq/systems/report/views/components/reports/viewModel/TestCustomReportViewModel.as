package il.co.topq.systems.report.views.components.reports.viewModel
{
	import flash.events.MouseEvent;
	
	import il.co.topq.systems.report.events.general.RefreshCustomReportGridEvent;
	import il.co.topq.systems.report.events.testCustomReport.GetTestCustomReportByChunkEvent;
	import il.co.topq.systems.report.events.testCustomReport.GetTestCustomReportSizeEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	import il.co.topq.systems.report.views.components.reports.TestCustomReport;
	import il.co.topq.systems.report.xml_utils.CustomReportXMLConverter;
	import il.co.topq.systems.report.xml_utils.TestXMLConverter;
	
	import mx.controls.Alert;
	import mx.rpc.events.ResultEvent;
	
	public class TestCustomReportViewModel extends TestCustomReport
	{
		public function TestCustomReportViewModel()
		{
			super();
			this.addEventListener(RefreshCustomReportGridEvent.REFRESH_CUSTOM_REPORT_GRID_EVENT,refreshCustomReportHandler);
			getResults();
		}
		
		public function getResults():void {
			new GetTestCustomReportSizeEvent(testGridModel).dispatch();
			new GetTestCustomReportByChunkEvent(testGridModel,GetTestCustomReportCallbackResult, GetTestCustomReportCallbackFault).dispatch();			
		}
		
		
		protected function refreshCustomReportHandler(event:RefreshCustomReportGridEvent):void
		{
			if (event.customReportType == "test"){
				getResults();
			}
		}
		
		public function GetTestCustomReportCallbackResult(data:Object): void
		{			
			var evt:ResultEvent = data as ResultEvent;
			var xmlList:XMLList = (new XML(evt.message.body)).customReports;
			testGridModel.gridData.items = CustomReportXMLConverter.getTestCustomReportsFromXMLList(xmlList); 
			testCustomReportGrid.dataProvider = testGridModel.gridData.items;
		}
		
		public function GetTestCustomReportCallbackFault(info:Object): void
		{
			Alert.show("GetCustomReportCommand service was not initialized correctly");
		}
		
		/**
		 * This method will be called uppon a click in a NEXT componenet in the navigation componenet composed in the testCustomReport.<br>
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
		 * This method will be called uppon a click in a PREVIOUS componenet in the navigation componenet composed in the testCustomReport.<br>
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