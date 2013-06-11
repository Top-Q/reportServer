package il.co.topq.systems.report.events.test
{
	import il.co.topq.systems.report.events.general.GetPropertiesKeysEvent;

	public class GetTestPropertiesKeysEvent extends GetPropertiesKeysEvent 
	{
		public static var GET_TEST_PROPERTIES_VALUES_EVENT:String = "getTestPropertiesValueEventType";	
		public var eventServiceLocator:String = "getTestsPropertiesValues";
		
		public function GetTestPropertiesKeysEvent(callbackResult:Function, callbackFault:Function, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(GET_TEST_PROPERTIES_VALUES_EVENT, eventServiceLocator, callbackResult, callbackFault, bubbles,cancelable);
		}
	}
}
