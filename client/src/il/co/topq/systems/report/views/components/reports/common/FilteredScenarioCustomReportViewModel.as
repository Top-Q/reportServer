package il.co.topq.systems.report.views.components.reports.common
{
	import flash.events.Event;
	import flash.events.MouseEvent;
	
	import il.co.topq.systems.report.controllers.ReportController;
	import il.co.topq.systems.report.events.general.ScenarioDetailsGetScenarioTestsEvent;
	import il.co.topq.systems.report.events.general.SelectAllCheckBoxClickedEvent;
	import il.co.topq.systems.report.events.general.UpdateEvent;
	import il.co.topq.systems.report.events.scenario.GetScenariosByFilterEvent;
	import il.co.topq.systems.report.events.scenario.GetScenariosByFilterSizeEvent;
	import il.co.topq.systems.report.events.scenarioCustomReport.ExportScenarioCustomReportEvent;
	import il.co.topq.systems.report.events.testCustomReport.GetScenarioCustomReportPropertiesValuesEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	import il.co.topq.systems.report.models.valueobjects.PropertyValuesWrapper;
	import il.co.topq.systems.report.models.valueobjects.ScenarioProperty;
	import il.co.topq.systems.report.models.valueobjects.uiConfigurations.GridUIConfiguration;
	import il.co.topq.systems.report.views.components.reports.scenarioCustomReport.ScenarioFilteredCustomReportContainer;
	import il.co.topq.systems.report.views.components.reports.tools.FilterdCustomReportTools;
	import il.co.topq.systems.report.views.components.reports.tools.TimeRangeChooser;
	import il.co.topq.systems.report.views.components.reports.viewModel.ComparedScenariosViewModel;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.controls.ComboBox;
	import mx.events.FlexEvent;
	import mx.events.ListEvent;
	import mx.managers.PopUpManager;
	import mx.rpc.events.ResultEvent;
	
	import org.as3commons.collections.framework.IIterator;
	
	import spark.events.IndexChangeEvent;
	
	public class FilteredScenarioCustomReportViewModel extends ScenarioFilteredCustomReportContainer
	{
		public var scenarioCustomReportID:String; //custom report id;
		
		public function FilteredScenarioCustomReportViewModel(scenarioCustomReportID:String)
		{
			super();
			this.addEventListener(UpdateEvent.UPDATE_EVENT,updateHandler);
			this.scenarioCustomReportID = scenarioCustomReportID;
		}
		
		protected function updateHandler(event:Event):void
		{
			getResults();			
		}
		
		override protected function onCreationComplete(event:FlexEvent):void
		{
			addNavigationListners();
			this.addEventListener(SelectAllCheckBoxClickedEvent.SELECT_ALL_CHECK_BOX_CLICKED_EVENT,selectAllCheckBoxClicked);
			new GetScenarioCustomReportPropertiesValuesEvent(scenarioCustomReportID, getPropertiesValuesCallbackResult, getPropertiesValuesCallbackFault).dispatch();
			executionGrid.addEventListener(ScenarioDetailsGetScenarioTestsEvent.SCENARIO_DETAILS_GET_SCENARIO_TESTS_EVENT, getScenarioTestsClicked);
			var scenarioCustomReportUIConfiguration:GridUIConfiguration = ReportModelLocator.getInstance().uiConfigurations.scenarioCustomReportConfiguration.customReportConfigurationMap.itemFor(this.scenarioCustomReportID);
			if (scenarioCustomReportUIConfiguration == null){
				scenarioCustomReportUIConfiguration = new GridUIConfiguration("uiConfiguration", null, this.scenarioCustomReportID);
				ReportModelLocator.getInstance().uiConfigurations.scenarioCustomReportConfiguration.customReportConfigurationMap.add(this.scenarioCustomReportID, scenarioCustomReportUIConfiguration);
			}
			executionGrid.rebuildGridUsingUIConfiguration(scenarioCustomReportUIConfiguration);
		}
		
		protected function getScenarioTestsClicked(event:ScenarioDetailsGetScenarioTestsEvent):void
		{
			dispatchEvent(new ScenarioDetailsGetScenarioTestsEvent(event.scenario));
		}
		
		public function getPropertiesValuesCallbackResult(data:ResultEvent): void
		{		
			var xml:XML = new XML(data.message.body);
			propertiesValuesWrappersToCustomReport(xml);
			
			populatePropertValuesComboBox();
			setPropertiesInScenarioQuery();
			getResults();
		}
		
		private function propertiesValuesWrappersToCustomReport(propertiesValuesWrapperXML:XML):void {
			var propertiesValuesWrappers:XMLList = propertiesValuesWrapperXML.propertyValuesWrappers as XMLList;
			for (var i:int=0; i<propertiesValuesWrappers.length(); i++){
				scenarioCustomReportGridModel.scenarioCustomReportPropertiesValues.addItem(new PropertyValuesWrapper(propertiesValuesWrappers[i]));
			}
		}
		
		public function getPropertiesValuesCallbackFault(info:Object): void
		{
			Alert.show("GetScenarioCustomReportPropertiesValuesCommand service was not initialized correctly");
		}
		
		override protected function getResults():void{
			new GetScenariosByFilterSizeEvent(scenarioCustomReportGridModel.scenarioQuery.toXML(),scenarioCustomReportGridModel).dispatch();
			new GetScenariosByFilterEvent(scenarioCustomReportGridModel.scenarioQuery.toXML(),scenarioCustomReportGridModel).dispatch();
			setCustomReportTitle();
		}
		
		override protected function handlePreviousClicked(event:MouseEvent):void{
			if (scenarioCustomReportGridModel.navigation.index > 0){
				if (!executionGrid.areAllScenariosSelected){//next chunk will not be selected in case not all scenarios from db are selected.
					executionGrid.headerCheckBoxSelected = false;
					currentState = "Normal";
				}
				scenarioCustomReportGridModel.navigation.index--;
				scenarioCustomReportGridModel.scenarioQuery.chunk.length = scenarioCustomReportGridModel.gridConfiguration.numberOfRows;
				scenarioCustomReportGridModel.scenarioQuery.chunk.startIndex = scenarioCustomReportGridModel.navigation.index * scenarioCustomReportGridModel.gridConfiguration.numberOfRows;
				getResults();
			}
		}
		
		override protected function handleNextClicked(event:MouseEvent):void{
			if (scenarioCustomReportGridModel.navigation.index < scenarioCustomReportGridModel.gridData.numberOfPages){
				if (!executionGrid.areAllScenariosSelected){//next chunk will not be selected in case not all scenarios from db are selected.
					executionGrid.headerCheckBoxSelected = false;
					currentState = "Normal";
				}
				scenarioCustomReportGridModel.navigation.index++;
				scenarioCustomReportGridModel.scenarioQuery.chunk.length = scenarioCustomReportGridModel.gridConfiguration.numberOfRows;
				scenarioCustomReportGridModel.scenarioQuery.chunk.startIndex = scenarioCustomReportGridModel.navigation.index * scenarioCustomReportGridModel.gridConfiguration.numberOfRows;
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
				setPropertiesInScenarioQuery();
				new ExportScenarioCustomReportEvent("jsystem_scenario_custom_report.xls","xls",scenarioCustomReportGridModel.scenarioQuery, scenarioCustomReportID).dispatch();
			}else if (data == "Export to PDF"){
				setPropertiesInScenarioQuery();
				new ExportScenarioCustomReportEvent("jsystem_scenario_custom_report.pdf","pdf",scenarioCustomReportGridModel.scenarioQuery, scenarioCustomReportID).dispatch();
			}else if (data == "Compare"){
				var keyIterator:IIterator = executionGrid.selectedScenarios.keyIterator();
				var comparedScenarios:String = "";
				while(keyIterator.hasNext()){
					var key:String = keyIterator.next() as String;
					comparedScenarios += key + ",";
				}
//				new CompareScenarioEvent(comparedScenarios).dispatch();
				new ComparedScenariosViewModel(comparedScenarios);
			}else if (data == "Add Columns"){
				executionGrid.addGridColumns();
			}
			tools.selectedItem="Tools";
		}
		
		override protected function combobox_changeHandler(event:ListEvent):void
		{
			scenarioCustomReportGridModel.scenarioQuery.scenarioProperties = new ArrayCollection();			
			for (var i:int=0; i<comboBoxPropertyValueArr.length; i++){
				var c:ComboBox = comboBoxPropertyValueArr[i] as ComboBox;
				if (c.selectedIndex != ANY_VALUE_INDEX){
					scenarioCustomReportGridModel.scenarioQuery.scenarioProperties.addItem(new ScenarioProperty(c.name as String,c.selectedItem as String));
				}else{
					scenarioCustomReportGridModel.scenarioQuery.scenarioProperties.addItem(new ScenarioProperty(c.name as String,""));
				}
			}
			getResults();
		}
			
	}
}