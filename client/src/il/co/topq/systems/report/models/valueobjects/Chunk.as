package il.co.topq.systems.report.models.valueobjects
{
	[Bindable]
	public class Chunk
	{
		public var startIndex:Number;
		public var length:Number = 30;// need to change this to be customizeable
		
		public function Chunk(startIndex:Number, length:Number){
			this.length = length;
			this.startIndex = startIndex;
		}
		
		public function toXML():XML {
			var xml:XML = <chunk></chunk>;
			var str:String = "";
			str += "<length>" + length + "</length>";
			str += "<startIndex>" + startIndex + "</startIndex>";
			var xMLList:XMLList = XMLList(str);
			xml.appendChild(xMLList);
			return xml;
		}
	}
}