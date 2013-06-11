package il.co.topq.systems.report.models.valueobjects
{
	import flash.utils.Dictionary;
	
	import mx.controls.List;

	public class SelectionCriteria
	{
		public function SelectionCriteria()
		{
		}
		
		private var timeAscending:Boolean;
		
		private var chunk:Chunk;
		
		private var sortingColumn:String;
		
		private var timeRange:TimeRange;
		
		private var propertyMap:Dictionary;
		
	}
}