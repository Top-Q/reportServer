package il.co.topq.systems.report.models.valueobjects.uiConfigurations
{
	import il.co.topq.systems.report.models.valueobjects.TestGroupingPolicy;
	
	import mx.collections.ArrayCollection;
	
	import org.as3commons.collections.Map;
	
	[Bindable]
	public class UIConfiguration
	{
		public var scenarioGridConfigurations:GridUIConfiguration;
		public var testGridConfigurations:GridUIConfiguration;
		public var summaryProperties:SummaryProperties;
		public var scenarioCustomReportConfiguration:CustomReportUIConfiguration;
		public var testCustomReportConfiguration:CustomReportUIConfiguration;
		public var testGrouping:TestGroupingPolicy;
		public var xml:XML;
		
		public function UIConfiguration(xml:XML = null)
		{
			this.xml = xml;
			this.scenarioGridConfigurations = new GridUIConfiguration("scenarioReportGrid",(xml.scenarioReportGrid as XMLList)[0]);
			this.testGridConfigurations = new GridUIConfiguration("testReportGrid",(xml.testReportGrid as XMLList)[0]);
			this.summaryProperties = new SummaryProperties("summaryProperties",(xml.summaryProperties as XMLList)[0]);
			this.scenarioCustomReportConfiguration = new CustomReportUIConfiguration("scenarioCustomReports", (xml.scenarioCustomReports as XMLList)[0]);
			this.testCustomReportConfiguration = new CustomReportUIConfiguration("testCustomReports", (xml.testCustomReports as XMLList)[0]);
			this.testGrouping = new TestGroupingPolicy((xml.testGroupingPolicy as XMLList)[0]);
		}
		
		public function toXML():XML {
			var xml:XML = <uiConfigurations></uiConfigurations>;
			xml.appendChild(this.scenarioGridConfigurations.toXML());
			xml.appendChild(this.testGridConfigurations.toXML());
			xml.appendChild(this.summaryProperties.toXML());
			xml.appendChild(this.scenarioCustomReportConfiguration.toXML());
			xml.appendChild(this.testCustomReportConfiguration.toXML());
			xml.appendChild(this.testGrouping.toXML());
			return xml;
		}
		

	}
}