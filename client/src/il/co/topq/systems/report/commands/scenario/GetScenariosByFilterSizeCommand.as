package il.co.topq.systems.report.commands.scenario
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.scenario.GetScenariosByFilterSizeEventDelegate;
	import il.co.topq.systems.report.events.scenario.GetScenariosByFilterSizeEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	
	import mx.controls.Alert;
	import mx.rpc.IResponder;
	import il.co.topq.systems.report.models.valueobjects.ScenarioCustomReportGridModel;
	
	public class GetScenariosByFilterSizeCommand implements ICommand, IResponder
	{
		private var model: ReportModelLocator = ReportModelLocator.getInstance();		
		private var scenarioCustomReportGridModel:ScenarioCustomReportGridModel;
		
		public function execute(event:CairngormEvent): void
		{
			var evt:GetScenariosByFilterSizeEvent = event as GetScenariosByFilterSizeEvent;
			scenarioCustomReportGridModel = evt.scenarioCustomReportGridModel;
			var getScenariosByFilterSizeEventDelegate:GetScenariosByFilterSizeEventDelegate= new GetScenariosByFilterSizeEventDelegate(this);
			getScenariosByFilterSizeEventDelegate.getScenariosByFilterSize(evt.xml);
			
		}
		
		private function setNavigation():void{
			if (scenarioCustomReportGridModel.navigation.index == 0){
				scenarioCustomReportGridModel.navigation.isPrevEnable = false;
			}else{
				scenarioCustomReportGridModel.navigation.isPrevEnable = true;
			}
			
			if ((scenarioCustomReportGridModel.gridData.numberOfPages  == scenarioCustomReportGridModel.navigation.index + 1) || (scenarioCustomReportGridModel.gridData.numberOfPages == 0)){
				scenarioCustomReportGridModel.navigation.isNextEnable = false;
			}
			else{
				scenarioCustomReportGridModel.navigation.isNextEnable = true;
			}
		}
		
		public function result(data:Object): void
		{		
			var xml:XML = new XML(data.message.body);
			scenarioCustomReportGridModel.gridData.totalItems = xml.toString();
			scenarioCustomReportGridModel.gridData.numberOfPages = scenarioCustomReportGridModel.gridData.totalItems / scenarioCustomReportGridModel.gridConfiguration.numberOfRows ;
			if ( ( scenarioCustomReportGridModel.gridData.totalItems % scenarioCustomReportGridModel.gridConfiguration.numberOfRows ) > 0){
				scenarioCustomReportGridModel.gridData.numberOfPages++;
			}
			setNavigation();
		}
		
		public function fault(info:Object): void
		{
			Alert.show("ExecutionGetScenarioCommand service was not initialized correctly");
		}
	}
}