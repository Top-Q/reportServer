package il.co.topq.systems.report.commands.scenario
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.scenario.GetScenarioSizeEventDelegate;
	import il.co.topq.systems.report.events.scenario.GetScenarioSizeEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	import il.co.topq.systems.report.models.valueobjects.ScenarioGridModel;
	
	import mx.controls.Alert;
	import mx.rpc.IResponder;
	
	public class GetScenarioSizeCommand implements ICommand, IResponder
	{
		public var scenarioGridModel:ScenarioGridModel;
		
		public function execute(event:CairngormEvent): void
		{
			var evt:GetScenarioSizeEvent = event as GetScenarioSizeEvent;
			this.scenarioGridModel = evt.scenarioGridModel;
			var getScenarioSizeEventDelegate:GetScenarioSizeEventDelegate= new GetScenarioSizeEventDelegate(this);
			getScenarioSizeEventDelegate.getScenarioSize(evt.filter);
		}
		
		private function setNavigation():void{
			if (scenarioGridModel.navigation.index == 0){
				scenarioGridModel.navigation.isPrevEnable = false;
			}else{
				scenarioGridModel.navigation.isPrevEnable = true;
			}
			
			if ((scenarioGridModel.gridData.numberOfPages  == scenarioGridModel.navigation.index + 1) || (scenarioGridModel.gridData.numberOfPages == 0)){
				scenarioGridModel.navigation.isNextEnable = false;
			}
			else{
				scenarioGridModel.navigation.isNextEnable = true;
			}
		}
		
		public function result(data:Object): void
		{		
			var xml:XML = new XML(data.message.body);
			scenarioGridModel.gridData.totalItems = xml.toString();
			scenarioGridModel.gridData.numberOfPages = scenarioGridModel.gridData.totalItems / scenarioGridModel.gridConfiguration.numberOfRows ;
			if ( ( scenarioGridModel.gridData.totalItems % scenarioGridModel.gridConfiguration.numberOfRows ) > 0){
				scenarioGridModel.gridData.numberOfPages++;
			}
			setNavigation();
		}
		
		public function fault(info:Object): void
		{
			Alert.show("GetScenarioSizeCommand service was not initialized correctly");
		}
	}
}