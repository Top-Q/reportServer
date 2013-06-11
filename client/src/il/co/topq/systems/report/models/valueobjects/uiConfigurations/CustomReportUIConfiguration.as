package il.co.topq.systems.report.models.valueobjects.uiConfigurations
{
	import org.as3commons.collections.Map;
	import org.as3commons.collections.framework.IIterator;

	public class CustomReportUIConfiguration
	{
		public var customReportConfigurationMap:Map;
		public var xml:XML;
		public var name:String;
		public function CustomReportUIConfiguration(name:String, xml:XML = null)
		{
			this.name = name;
			this.xml = xml;
			this.customReportConfigurationMap = new Map();
			fromXML();
		}
		
		public function fromXML():void{
			if (this.xml != null){
				var customReports:XMLList = xml.uiConfiguration as XMLList;
				for (var i:int =0; i<customReports.length(); i++){
					var gridUIConfiguration:GridUIConfiguration = new GridUIConfiguration("uiConfiguration",customReports[i]);
					this.customReportConfigurationMap.add(gridUIConfiguration.id, gridUIConfiguration);
				}
			}
		}
		
		public function toXML():XML{
			
			var xml:XML = <{this.name}></{this.name}>;
			var keyArr:Array = this.customReportConfigurationMap.toArray();
			for (var i:int =0; i<keyArr.length;i ++){
				xml.appendChild((keyArr[i] as GridUIConfiguration).toXML());
			}
			return xml;
		}
	}
}