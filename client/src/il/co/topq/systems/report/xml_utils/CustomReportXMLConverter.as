package il.co.topq.systems.report.xml_utils
{
	import il.co.topq.systems.report.models.valueobjects.ScenarioCustomReportVO;
	import il.co.topq.systems.report.models.valueobjects.TestCustomReportVO;
	
	import mx.collections.ArrayCollection;

	public class CustomReportXMLConverter
	{
		
		/**
		 * This method will create an array collection of test custom reports
		 */ 
		public static function getTestCustomReportsFromXMLList(xmlList:XMLList):ArrayCollection{
			var arrayCollection:ArrayCollection = new ArrayCollection();
			for(var i:int = 0; i< xmlList.length(); i++){
				var testCustomReport:TestCustomReportVO = new TestCustomReportVO (xmlList[i]); 
				arrayCollection.addItem(testCustomReport);	
			}
			return arrayCollection;
		}
		
		/**
		 * This method will create an array collection of scenario custom reports
		 */ 
		public static function getScenarioCustomReportsFromXMLList(xmlList:XMLList):ArrayCollection{
			var arrayCollection:ArrayCollection = new ArrayCollection();
			for(var i:int = 0; i< xmlList.length(); i++){
				var scenarioCustomReport:ScenarioCustomReportVO = new ScenarioCustomReportVO (xmlList[i]); 
				arrayCollection.addItem(scenarioCustomReport);	
			}
			return arrayCollection;
		}
	}
}