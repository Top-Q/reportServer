package il.co.topq.systems.report.renderers
{
	import flash.events.MouseEvent;
	
	import il.co.topq.systems.report.events.general.UpdateEvent;
	import il.co.topq.systems.report.events.test.GetTestByIDEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	import il.co.topq.systems.report.models.grid.ComparedScenariosGridColumn;
	import il.co.topq.systems.report.models.valueobjects.Test;
	import il.co.topq.systems.report.views.components.reports.common.TestDetailsViewModel;
	import il.co.topq.systems.report.views.components.reports.tools.TestDetail;
	
	import mx.containers.HBox;
	import mx.controls.DataGrid;
	import mx.controls.Image;
	import mx.controls.dataGridClasses.MXDataGridItemRenderer;
	import mx.core.FlexGlobals;
	import mx.core.IFactory;
	import mx.core.UIComponent;
	import mx.managers.PopUpManager;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	
	public class ComparedScenarioColumnGridItemRenderer extends MXDataGridItemRenderer implements IFactory
	{
		private var hbox:HBox;
		private var img:Image;
		private var imgTooltip:String;
		
		[Embed(source="/assets/images/pass.png")]
		[Bindable]
		public var pass:Class;
		
		[Embed(source="/assets/images/fail.gif")]
		[Bindable]
		public var fail:Class;
		
		[Embed(source="/assets/images/warning.png")]
		[Bindable]
		public var warning:Class;
		
		protected override function createChildren():void{
			super.createChildren();
			this.hbox = new HBox;
			this.hbox.percentWidth = 100;
			this.hbox.setStyle("horizontalAlign","center");
			this.hbox.setStyle("verticalAlign","middle");
			img = new Image();
			img.height = 20;
			
			img.autoLoad = true;
			img.maintainAspectRatio = true;
			img.scaleContent = true;
			img.useHandCursor = true;
			img.buttonMode = true;
			img.mouseChildren = false;
			
			img.addEventListener(MouseEvent.CLICK,imgClicked);
			hbox.addChild(img);
			addElement(hbox);
		}
		
		/**
		 * This method will be called upon a click within the compared Scenario component on a test status Img.<br>
		 * It will result the fetching of the test details, and showing the testDetails component.
		 */ 
		protected function imgClicked(event:MouseEvent):void
		{
			var testID:String = getClickedTestID();	
			if (testID != null){
				new GetTestByIDEvent(testID,getTestByIDCallbackResult,getTestByIDCallbackFault).dispatch();
			}
		}
		
		/**
		 * This method will be called upon completion of fetching test by it ID.
		 * It will create a testDetail component and display it using popmanager.
		 */ 
		protected function getTestByIDCallbackResult(data:ResultEvent): void
		{
			var test:Test = new Test(new XML(data.message.body));
			var testDetail:TestDetail = new TestDetailsViewModel(test);
			testDetail.addEventListener(UpdateEvent.UPDATE_EVENT,updateHandler);
			PopUpManager.addPopUp(testDetail, UIComponent(FlexGlobals.topLevelApplication), false);
			PopUpManager.centerPopUp(testDetail);
		}
		
		protected function updateHandler(event:UpdateEvent):void
		{
			dispatchEvent(new UpdateEvent(true));
		}
		
		/**
		 * This method will be called upon the failure of the retrieval of test by ID.
		 */ 
		protected function getTestByIDCallbackFault(info:Object): void
		{
			var model:ReportModelLocator = ReportModelLocator.getInstance();
			var event:FaultEvent = FaultEvent(info);
			model.error.setMessage(event,"Could not retrieve test details");
			model.applicationState.currentState = "ErrorPage";	
		}
		
		
		private function getClickedTestID():String{
			var dataGrid:DataGrid = listData.owner as DataGrid;
			var columnScenarioID:String = ComparedScenariosGridColumn(dataGrid.columns[listData.columnIndex]).getScenarioID();
			var tests:XMLList = data.tests as XMLList;
			for (var j:int = 0; j<tests.length(); j++){
				if (columnScenarioID == tests[j].scenarioID){
					return data.tests[j].testID;
				}
			}
			return null;
		}
		
		protected override function commitProperties():void
		{
			super.commitProperties();
			
			var imgType:Class = getImageType();
			
			img.toolTip = this.imgTooltip;
			img.source = imgType;
		}
		
		public function newInstance():*{
			return new ComparedScenarioColumnGridItemRenderer();
		}
		
		public function ComparedScenarioColumnGridItemRenderer(){
			super();
		}
		
		public function getImageType():Class{			
			
			var dataGrid:DataGrid = listData.owner as DataGrid;
		
			var columnScenarioID:String = ComparedScenariosGridColumn(dataGrid.columns[listData.columnIndex]).getScenarioID();
			
			var tests:XMLList = data.tests as XMLList;
			
			for (var j:int = 0; j<tests.length(); j++){
				if (columnScenarioID == tests[j].scenarioID){
					
					var status:int = tests[j].status;
					
					if (status == 0) {
						this.imgTooltip = "Passed";
						return pass;
					}else if (status == 1){ 
						this.imgTooltip = "Failed";
						return fail;
					}else if (status == 2){
						this.imgTooltip = "Warned";
						return warning;
					}
				}
			}
			return null;
		}
	}
}