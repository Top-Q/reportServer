package il.co.topq.systems.report.views.components.reports.common
{
	import il.co.topq.systems.report.models.valueobjects.ScenarioProperty;
	import il.co.topq.systems.report.events.local.SaveScenarioPropertyEvent;
	import flash.events.MouseEvent;
	
	[Event(name="saveScenarioPropertyEvent", type="il.co.topq.systems.report.events.local.SaveScenarioPropertyEvent")]
	public class ScenarioPropertyEditor extends PropertyEditor
	{
		[Bindable]
		private var _scenarioProperty:ScenarioProperty ;  
		
		public function ScenarioPropertyEditor(scenarioProperty:ScenarioProperty = null)		
		{
			super();
			if(scenarioProperty == null){
				_scenarioProperty = new ScenarioProperty();	
			}
			else {
				_scenarioProperty = scenarioProperty.clone();
			}
		}
		
		override protected function saveButton_clickHandler(event:MouseEvent):void
		{
			var saveScenarioPropertyEvent:SaveScenarioPropertyEvent = new SaveScenarioPropertyEvent(_scenarioProperty);
			dispatchEvent(saveScenarioPropertyEvent);
			closePropertyEditor(event);
		}
		
		override public function set key(key:String):void{
			_scenarioProperty.propertyKey = key;
		} 
		
		[Bindable]
		override public function get key():String{
			return _scenarioProperty.propertyKey;
		}
		
		
		override public function set value(value:String):void{
			_scenarioProperty.propertyValue = value;
		}
		
		[Bindable]
		override public function get value():String{
			return _scenarioProperty.propertyValue;
		}
	}
}