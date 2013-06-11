package il.co.topq.systems.report.xml_utils
{
	import il.co.topq.systems.report.models.ReportModelLocator;
	import il.co.topq.systems.report.models.valueobjects.PackageDescription;
	import il.co.topq.systems.report.models.valueobjects.Test;
	import il.co.topq.systems.report.models.valueobjects.TestDescription;
	import il.co.topq.systems.report.models.valueobjects.TestProperty;
	
	import mx.collections.ArrayCollection;
	
	import net.customware.flex.util.xml.XMLConverter;
	import net.customware.flex.util.xml.converter.ArrayCollectionConverter;

	public class XMLConverterUtil
	{
		private static var testXMLConverter:XMLConverter;
		
		public static function getTestXMLConverter():XMLConverter{
			if (testXMLConverter == null){
				testXMLConverter = XMLConverter.getInstance();
				testXMLConverter.registerAlias("test", Test);
				testXMLConverter.registerAlias("testProperties", TestProperty);
				testXMLConverter.registerAlias("packageDescription", PackageDescription);
				testXMLConverter.registerAlias("testDescription", TestDescription);
			}
			return testXMLConverter ;			
		}
		
		
	}
}