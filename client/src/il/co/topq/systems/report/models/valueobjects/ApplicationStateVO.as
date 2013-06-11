package il.co.topq.systems.report.models.valueobjects
{
	import mx.states.State;

	public class ApplicationStateVO
	{
		
		[Bindable]
		public var currentState:String = "LogIn";
		
		[Bindable]
		public var topLevelState:String = "Summary";
		
		[Bindable]
		public var reportsState:String = "Execution";
		
		[Bindable]
		public var settingsState:String = "SystemConfiguration";
	}
}