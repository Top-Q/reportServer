package il.co.topq.systems.report.business.scenarioCustomReport
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import il.co.topq.systems.report.models.ReportModelLocator;
	import il.co.topq.systems.report.models.valueobjects.Property;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;
	
	public class CreateScenarioCustomReportEventDelegate
	{
		private var responder:IResponder;
		private var model:ReportModelLocator = ReportModelLocator.getInstance();
		
		public function CreateScenarioCustomReportEventDelegate(responder : IResponder)
		{
			this.responder = responder;
		}
		
		public function createCustomReport(): void
		{  			
			var xml:XML = createXML();	//will get the xml build for the request;
			
			var service:HTTPService = 
				ServiceLocator.getInstance().getHTTPService("createScenarioCustomReport");
			service.request = xml;
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
		
		/**
		 * this method will create the xml for the create custom report request
		 */ 
		private function createXML(): XML
		{
			var xm:XML = <customReport></customReport>; //will be used  as the header of the xml created;
			var xmlStr:String; //will be used as the xml body;
			var xmlList:XMLList; //will get the xmlStr as the object in c'tor time;
			//Node names
			var nodeName:String 		 = "name";
			var nodeDescription:String	 = "description";
			var nodeProperties:String	 = "properties";
			var nodePropertyKey:String 	 = "propertyKey";
			//Node names
			
			//"static" values of the xml should not be in the following loop;
			xmlStr = (	
				"<" +nodeName+ ">" + model.customReportVO.name + "</" +nodeName+ ">" + 
				"<" + nodeDescription + ">" + model.customReportVO.description + "</" + nodeDescription + ">" );
			//"static" values of the xml should not be in the following loop;
			
			
			//handling the properties part in the created xml
			var arr:Array = model.customReportVO.propertiesSet.toArray();
			var property:Property;
			
			for (var i:int = 0; i < arr.length; i++){
				
				property = new Property;
				property.propertyKey = arr[i];			
				xmlStr += (	
					"<" +nodeProperties+ " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"scenarioProperty\">" 
					+ "<" +nodePropertyKey+ ">" 
					+ property.propertyKey.toString() 
					+ "</" +nodePropertyKey+ ">" 
					+ "</" +nodeProperties+ ">" );
				
			}
			//handling the properties part in the created xml
			
			xmlList = XMLList(xmlStr);
			xm.appendChild(xmlList);
			return xm;
		}
		
	}
}