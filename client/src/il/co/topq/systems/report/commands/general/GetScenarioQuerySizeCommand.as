package il.co.topq.systems.report.commands.general
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.general.GetQuerySizeDelegate;
	import il.co.topq.systems.report.models.ReportModelLocator;
	import il.co.topq.systems.report.models.valueobjects.ScenarioGridModel;
	
	import mx.rpc.IResponder;
	import mx.rpc.events.FaultEvent;
	
	public class GetScenarioQuerySizeCommand implements ICommand, IResponder
	{
		private var model: ReportModelLocator = ReportModelLocator.getInstance();
		public var scenarioGridModel:ScenarioGridModel;
		
		public function execute(event:CairngormEvent): void
		{
			var evt:GetScenarioQuerySizeEvent = event as GetScenarioQuerySizeEvent;
			this.scenarioGridModel = evt.scenarioGridModel;
			var getQuerySizeDelegate: GetQuerySizeDelegate = new GetQuerySizeDelegate(this);
			getQuerySizeDelegate.getSizeOfQuery(scenarioGridModel.scenarioQuery.toXML(),evt.serviceLocator);
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
			var event:FaultEvent = FaultEvent(info);
			model.error.setMessage(event);
			model.applicationState.currentState = "ErrorPage";	
		}

	}
}