package il.co.topq.systems.report.views.components.reports.common
{
	import flash.events.MouseEvent;
	
	import il.co.topq.systems.report.events.local.SaveTestPropertyEvent;
	import il.co.topq.systems.report.models.valueobjects.TestProperty;
	
	[Event(name="saveTestPropertyEvent", type="il.co.topq.systems.report.events.local.SaveTestPropertyEvent")]
	
	public class TestPropertyEditor extends PropertyEditor
	{
		
		[Bindable]
		private var _testProperty:TestProperty;				
		
		public function TestPropertyEditor(testProperty:TestProperty = null)
		{
			super();
			if(testProperty == null){
				_testProperty = new TestProperty();	
			}
			else {
				_testProperty = testProperty.clone();
			}
		}
		
		override protected function saveButton_clickHandler(event:MouseEvent):void
		{
			var saveTestPropertyEvent:SaveTestPropertyEvent = new SaveTestPropertyEvent(_testProperty);
			dispatchEvent(saveTestPropertyEvent);
			closePropertyEditor(event);
		}
		
		override public function set key(key:String):void{
			_testProperty.propertyKey = key;
		} 
		
		[Bindable]
		override public function get key():String{
			return _testProperty.propertyKey;
		}
		
		
		override public function set value(value:String):void{
			_testProperty.propertyValue = value;
		}
		
		[Bindable]
		override public function get value():String{
			return _testProperty.propertyValue;
		}
	}
}