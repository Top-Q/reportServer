package il.co.topq.systems.report.commands.general
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.models.valueobjects.TestGridModel;

	public class GetTestQuerySizeEvent extends CairngormEvent
	{
		public var serviceLocator:String ;
		public var testGridModel:TestGridModel;
		
		public function GetTestQuerySizeEvent(type:String, testGridModel:TestGridModel, serviceLocator:String, bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(type,bubbles,cancelable);
			this.serviceLocator = serviceLocator;
			this.testGridModel = testGridModel;
		}
	}
}