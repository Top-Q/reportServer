package il.co.topq.systems.report.models.valueobjects
{
	import mx.collections.ArrayCollection;
	
	[Bindable]
	public class GridData
	{
		/**
		 * This represents the total number of items of the query in DB.
		 * Ex: DB total number of items: 1000;
		 * Chunk: size = 100;
		 * --> number of pages: 10;
		 **/		
		public var totalItems:int;
		public var numberOfPages:int;
		public var items:ArrayCollection = new ArrayCollection();
		
	}
}