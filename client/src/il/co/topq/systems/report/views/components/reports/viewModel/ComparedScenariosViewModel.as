package il.co.topq.systems.report.views.components.reports.viewModel
{
	import flash.events.Event;
	
	import il.co.topq.systems.report.events.general.UpdateEvent;
	import il.co.topq.systems.report.events.scenario.CompareScenarioEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	import il.co.topq.systems.report.views.components.reports.tools.ComparedScenarios;
	
	import mx.controls.Alert;
	import mx.core.FlexGlobals;
	import mx.core.UIComponent;
	import mx.events.ResizeEvent;
	import mx.managers.PopUpManager;
	import mx.rpc.events.ResultEvent;
	
	public class ComparedScenariosViewModel extends ComparedScenarios
	{
		private var comparedScenarios:String
		private var isPopupVisible:Boolean = false;
		public function ComparedScenariosViewModel(comparedScenarios:String)
		{
			super();
			this.addEventListener(UpdateEvent.UPDATE_EVENT,updateHandler);
			this.comparedScenarios = comparedScenarios;
			getResults();
		}
		
		protected function updateHandler(event:Event):void
		{
			getResults();			
		}
		
		override protected function cancel_clickHandler(event:Event):void
		{
			PopUpManager.removePopUp(this);
		}
		
		protected function getResults():void {
			new CompareScenarioEvent(comparedScenarios,getComparedScenariosCallbackResult, getComparedScenariosCallbackFault).dispatch();
		}
		
		public function getComparedScenariosCallbackResult(evt:ResultEvent): void
		{
			var model:ReportModelLocator = ReportModelLocator.getInstance();
			model.comparedScenarios = new XML(evt.message.body);
			
			if (!isPopupVisible){
				PopUpManager.addPopUp(this, UIComponent(FlexGlobals.topLevelApplication),true);
				PopUpManager.centerPopUp(this);
				isPopupVisible = true;
			}
		}
		
		public function getComparedScenariosCallbackFault(info:Object): void
		{
			Alert.show("Compare Scenario service Failed");
		}
	}
}