package il.co.topq.systems.report.models.valueobjects
{
	[Bindable]
	public class TimeRange
	{
		public function TimeRange(){}
		
		public var lowBoundDate:Number = NaN;
		public var upBoundDate:Number = NaN;
		
		public function toXML():XML {
			var xml:XML = <timeRange></timeRange>;
			var str:String = "";
			if ((!isNaN(lowBoundDate)) && (!isNaN(upBoundDate))){
				str += "<lowBoundDate>" + lowBoundDate + "</lowBoundDate>";
				str += "<upBoundDate>" + upBoundDate + "</upBoundDate>";
				var xMLList:XMLList = XMLList(str);
				xml.appendChild(xMLList);
			}
			return xml;
		}
	}
}