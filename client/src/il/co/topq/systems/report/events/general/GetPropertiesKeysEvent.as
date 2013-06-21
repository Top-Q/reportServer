package il.co.topq.systems.report.events.general
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	public class GetPropertiesKeysEvent extends CairngormEvent
	{
		public var serviceLocator:String ;
		public var callbackResult:Function;
		public var callbackFault:Function;
		
		public function GetPropertiesKeysEvent(type:String, serviceLocator:String, callbackResult:Function, callbackFault:Function, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
			this.callbackFault = callbackFault;
			this.callbackResult = callbackResult;
			this.serviceLocator = serviceLocator;
		}
	}
}