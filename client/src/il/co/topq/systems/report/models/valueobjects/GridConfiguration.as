package il.co.topq.systems.report.models.valueobjects
{
	import mx.collections.ArrayCollection;
	
	[Bindable]
	public class GridConfiguration
	{
		public static const NO_ID:int = -1;
		public var id:int;
		public var type:TypeEnum;
		public var gridColumns:ArrayCollection; //set of strings (columns header)
		public var sizeOfChunk:int;
		public var numberOfRows:int = 30;
		
		public function GridConfiguration()
		{
			this.gridColumns = new ArrayCollection();
		}
		
		/**
		 * This method will create an XML object represents the grid configuration object;
		 */ 
		public function toXML():XML {
			var xml:XML = <gridConfiguration></gridConfiguration>;
			var str:String = "";
			str += "<id>" + id + "</id>";
			str += "<gridConfigurationType>" + type.valueOf() + "</gridConfigurationType>";
			str += "<chunkSize>" +sizeOfChunk+ "</chunkSize>";
			
			xml.appendChild(gridColumnsToXML());
			var xMLList:XMLList = XMLList(str);
			xml.appendChild(xMLList);
			return xml;
		}
		
		private function gridColumnsToXML():XML{
			var xml:XML = <gridColumns></gridColumns>;
			var str:String ="";
			for (var i:int = 0; i<gridColumns.length; i++){
				str += "<columns>" + gridColumns.getItemAt(i) + "</columns>";
			}
			var xMLList:XMLList = XMLList(str);
			xml.appendChild(xMLList);
			return xml;
		}
	}
}
