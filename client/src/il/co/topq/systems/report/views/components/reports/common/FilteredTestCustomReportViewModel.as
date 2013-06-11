package il.co.topq.systems.report.views.components.reports.common
{
	import flash.events.Event;
	import flash.events.MouseEvent;
	
	import il.co.topq.systems.report.events.general.UpdateEvent;
	import il.co.topq.systems.report.events.test.GetTestsByFilterEvent;
	import il.co.topq.systems.report.events.test.GetTestsByFilterSizeEvent;
	import il.co.topq.systems.report.events.testCustomReport.ExportTestCustomReportEvent;
	import il.co.topq.systems.report.events.testCustomReport.GetTestCustomReportPropertiesValuesEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	import il.co.topq.systems.report.models.valueobjects.PropertyValuesWrapper;
	import il.co.topq.systems.report.models.valueobjects.TestProperty;
	import il.co.topq.systems.report.models.valueobjects.uiConfigurations.GridUIConfiguration;
	import il.co.topq.systems.report.models.valueobjects.uiConfigurations.UIConfiguration;
	import il.co.topq.systems.report.views.components.reports.testCustomReport.TestFilteredCustomReportContainer;
	import il.co.topq.systems.report.views.components.reports.tools.FilterdCustomReportTools;
	import il.co.topq.systems.report.views.components.reports.tools.FilteredTestCustomReportTool;
	import il.co.topq.systems.report.views.components.reports.tools.TimeRangeChooser;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.controls.ComboBox;
	import mx.events.FlexEvent;
	import mx.events.ListEvent;
	import mx.managers.PopUpManager;
	import mx.rpc.events.ResultEvent;
	
	import spark.events.IndexChangeEvent;

	public class FilteredTestCustomReportViewModel extends TestFilteredCustomReportContainer
	{
		public var testCustomReportID:String;
		
		public function FilteredTestCustomReportViewModel(testCustomReportID:String)
		{
			super();
			this.addEventListener(UpdateEvent.UPDATE_EVENT,updateEventHandler);
			this.testCustomReportID = testCustomReportID;
		}
		
		protected function updateEventHandler(event:Event):void
		{
			getResults();			
		}
		
		override protected function onCreationComplete(event:FlexEvent):void
		{
			addNavigationListners();
			new GetTestCustomReportPropertiesValuesEvent(testCustomReportID,getPropertiesValuesCallbackResult,getPropertiesValuesCallbackFault).dispatch();
			var uiConf:UIConfiguration = ReportModelLocator.getInstance().uiConfigurations;
			var testCustomReportUIConfiguration:GridUIConfiguration = ReportModelLocator.getInstance().uiConfigurations.testCustomReportConfiguration.customReportConfigurationMap.itemFor(this.testCustomReportID);
			if (testCustomReportUIConfiguration == null){
				testCustomReportUIConfiguration = new GridUIConfiguration("uiConfiguration", null, this.testCustomReportID);
				ReportModelLocator.getInstance().uiConfigurations.testCustomReportConfiguration.customReportConfigurationMap.add(this.testCustomReportID, testCustomReportUIConfiguration);
			}
			testReportGrid.rebuildGridUsingUIConfiguration(testCustomReportUIConfiguration);
		}
		
		public function getPropertiesValuesCallbackResult(data:ResultEvent): void
		{		
			var xml:XML = new XML(data.message.body);
			propertiesValuesWrappersToCustomReport(xml);
			
			populatePropertValuesComboBox();
			setPropertiesInTestQuery();
			getResults();
		}
		
		private function propertiesValuesWrappersToCustomReport(propertiesValuesWrapperXML:XML):void {
			var propertiesValuesWrappers:XMLList = propertiesValuesWrapperXML.propertyValuesWrappers as XMLList;
			for (var i:int=0; i<propertiesValuesWrappers.length(); i++){
				testCustomReportGridModel.testCustomReportPropertiesValues.addItem(new PropertyValuesWrapper(propertiesValuesWrappers[i]));
			}
		}
		
		public function getPropertiesValuesCallbackFault(info:Object): void
		{
			Alert.show("GetScenarioCustomReportPropertiesValuesCommand service was not initialized correctly");
		}
		
		override protected function getResults():void{
			
			new GetTestsByFilterSizeEvent(testCustomReportGridModel.testQuery.toXML(),testCustomReportGridModel).dispatch();
			new GetTestsByFilterEvent(testCustomReportGridModel.testQuery.toXML(),testCustomReportGridModel).dispatch();
			setCustomReportTitle();
		}
		
		override protected function handlePreviousClicked(event:MouseEvent):void{
			if (testCustomReportGridModel.navigation.index > 0){
				testCustomReportGridModel.navigation.index--;
				testCustomReportGridModel.testQuery.chunk.length = testCustomReportGridModel.gridConfiguration.numberOfRows;
				testCustomReportGridModel.testQuery.chunk.startIndex = testCustomReportGridModel.navigation.index * testCustomReportGridModel.gridConfiguration.numberOfRows;
				getResults();
			}
		}
		
		override protected function handleNextClicked(event:MouseEvent):void{
			if (testCustomReportGridModel.navigation.index < testCustomReportGridModel.gridData.numberOfPages){
				testCustomReportGridModel.navigation.index++;
				testCustomReportGridModel.testQuery.chunk.length = testCustomReportGridModel.gridConfiguration.numberOfRows;
				testCustomReportGridModel.testQuery.chunk.startIndex = testCustomReportGridModel.navigation.index * testCustomReportGridModel.gridConfiguration.numberOfRows;
				getResults();
			}
		}
		
		override protected function tools_changeHandler(event:IndexChangeEvent):void
		{
			var data:String = (event.currentTarget as FilterdCustomReportTools).selectedItem as String;
			
			if(data == "Time Range"){
				if (timeRangeChooser == null){
					timeRangeChooser= new TimeRangeChooser();	
				}					
				PopUpManager.addPopUp(timeRangeChooser,this.parent);
				PopUpManager.centerPopUp(timeRangeChooser);				
				timeRangeChooser.saveButton.addEventListener(MouseEvent.CLICK,saveTimeRangeClicked);
				timeRangeChooser.cleanButton.addEventListener(MouseEvent.CLICK,cleanTimeRangeClicked);
			}else if (data == "Export to Excel"){
				setPropertiesInTestQuery();
				new ExportTestCustomReportEvent("jsystem_test_custom_report.xls","xls",testCustomReportGridModel.testQuery,this.testCustomReportID).dispatch();
			}else if (data == "Export to PDF"){
				setPropertiesInTestQuery();
				new ExportTestCustomReportEvent("jsystem_test_custom_report.pdf","pdf",testCustomReportGridModel.testQuery,this.testCustomReportID).dispatch();
			}else if (data == "Add Columns"){
				testReportGrid.addGridColumns();
			}else if (data == "Compare"){
				Alert.show("Test compare is currently not supported");
			}
			tools.selectedItem="Tools";
		}
		
		override protected function combobox_changeHandler(event:ListEvent):void
		{
			testCustomReportGridModel.testQuery.testProperties = new ArrayCollection();			
			for (var i:int=0; i<comboBoxPropertyValueArr.length; i++){
				var c:ComboBox = comboBoxPropertyValueArr[i] as ComboBox;
				if (c.selectedIndex != ANY_VALUE_INDEX){
					testCustomReportGridModel.testQuery.testProperties.addItem(new TestProperty(c.name as String,c.selectedItem as String));
				}else{
					testCustomReportGridModel.testQuery.testProperties.addItem(new TestProperty(c.name as String,""));
				}
			}
			getResults();
		}
	}
}