package il.co.topq.systems.report.events.test
{
	import flash.events.Event;

	public class DeleteTestButtonClickEvent extends Event
	{
		public var testID:String;
		public static var DELETE_TEST_BUTTON_CLICK_EVENT:String = "deleteTestButtonClickEvent";

		public function DeleteTestButtonClickEvent(testID:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(DELETE_TEST_BUTTON_CLICK_EVENT, bubbles, cancelable);
			this.testID = testID;
		}
	}
}
