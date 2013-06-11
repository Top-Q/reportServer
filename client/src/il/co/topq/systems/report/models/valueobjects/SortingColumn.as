package il.co.topq.systems.report.models.valueobjects
{
	[Bindable]
	public class SortingColumn
	{
		public var name:String = "startTime";
		public var asc:Boolean = false;//default value == false
		
		public function SortingColumn()
		{
		}
		
		public function toXML():XML {
			var xml:XML = <sortingColumn></sortingColumn>;
			var str:String = "";
			str += "<name>" + name + "</name>";
			str += "<asc>" + asc + "</asc>";
			var xMLList:XMLList = XMLList(str);
			xml.appendChild(xMLList);
			return xml;				
		}
	}
}