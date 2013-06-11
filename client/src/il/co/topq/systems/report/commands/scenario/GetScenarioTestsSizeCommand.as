package il.co.topq.systems.report.commands.scenario
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.scenario.GetScenarioTestsSizeEventDelegate;
	import il.co.topq.systems.report.events.scenario.GetScenarioTestsSizeEvent;
	import il.co.topq.systems.report.models.valueobjects.TestGridModel;
	
	import mx.controls.Alert;
	import mx.rpc.IResponder;
	
	public class GetScenarioTestsSizeCommand implements ICommand, IResponder
	{
		public var testGridModel:TestGridModel;
		
		public function execute(event:CairngormEvent): void
		{
			var evt:GetScenarioTestsSizeEvent = event as GetScenarioTestsSizeEvent;
			testGridModel = evt.testGridModel;
			var getScenarioTestsSizeEventDelegate:GetScenarioTestsSizeEventDelegate= new GetScenarioTestsSizeEventDelegate(this);
			getScenarioTestsSizeEventDelegate.getScenarioTestsSize(evt.scenarioID);
			
		}

		private function setNavigation():void{
			if (testGridModel.navigation.index == 0){
				testGridModel.navigation.isPrevEnable = false;
			}else{
				testGridModel.navigation.isPrevEnable = true;
			}
			
			if ((testGridModel.gridData.numberOfPages  == testGridModel.navigation.index + 1) 
				|| (testGridModel.gridData.numberOfPages == 0)){
				testGridModel.navigation.isNextEnable = false;
			}
			else{
				testGridModel.navigation.isNextEnable = true;
			}
		}
		
		public function result(data:Object): void
		{		
			var xml:XML = new XML(data.message.body);
			testGridModel.gridData.totalItems = xml.toString();
			
			testGridModel.gridData.numberOfPages = testGridModel.gridData.totalItems / testGridModel.gridConfiguration.numberOfRows ;
			if ( ( testGridModel.gridData.totalItems % testGridModel.gridConfiguration.numberOfRows ) > 0){
				testGridModel.gridData.numberOfPages++;
			}
			setNavigation();
		}
		
		public function fault(info:Object): void
		{
			Alert.show("GetScenarioTestsSizeCommand service was not initialized correctly");
		}
	}
}