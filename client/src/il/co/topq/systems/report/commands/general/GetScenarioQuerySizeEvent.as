package il.co.topq.systems.report.commands.general
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.models.valueobjects.ScenarioGridModel;
	
	public class GetScenarioQuerySizeEvent extends CairngormEvent
	{
		public var serviceLocator:String ;
		public var scenarioGridModel:ScenarioGridModel;
		
		public function GetScenarioQuerySizeEvent(type:String, scenarioGridModel:ScenarioGridModel, serviceLocator:String, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(type,bubbles,cancelable);
			this.serviceLocator = serviceLocator;
			this.scenarioGridModel = scenarioGridModel;
		}
	}
}