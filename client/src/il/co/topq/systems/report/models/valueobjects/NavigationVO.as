package il.co.topq.systems.report.models.valueobjects
{
	[Bindable]
	public class NavigationVO
	{
		public var isPrevEnable:Boolean = false;
		public var isNextEnable:Boolean = false;
		public var index:Number = 0;
		
		public function NavigationVO()
		{
		}
	}
}