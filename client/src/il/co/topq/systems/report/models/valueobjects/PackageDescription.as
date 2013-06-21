package il.co.topq.systems.report.models.valueobjects
{
	import flash.globalization.StringTools;
	
	import mx.utils.StringUtil;

	[Bindable]
	public class PackageDescription
	{
		public var packageName:String;
		
		public var packageDescription:String;
		
		public function PackageDescription()
		{
		}
		
		public function toXML():XML {
			var xml:XML = <packageDescription></packageDescription>;
			var str:String = "<packageName>" + packageName + "</packageName>";
			str += "<packageDescription>" + packageDescription + "</packageDescription>";
			var xMLList:XMLList = XMLList(str);
			xml.appendChild(xMLList);
			return xml;
		}
	}
}