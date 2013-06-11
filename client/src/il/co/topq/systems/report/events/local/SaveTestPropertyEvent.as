package il.co.topq.systems.report.events.local
{
	import flash.events.Event;
	
	import il.co.topq.systems.report.models.valueobjects.TestProperty;
	
	public class SaveTestPropertyEvent extends Event
	{
		public var testProperty:TestProperty;
		
		public static var SAVE_TEST_PROPERTY_EVENT:String = "saveTestPropertyEvent";
		
		public function SaveTestPropertyEvent(testProperty:TestProperty = null, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(SAVE_TEST_PROPERTY_EVENT, bubbles, cancelable);
			this.testProperty = testProperty;			
		}
	}
}