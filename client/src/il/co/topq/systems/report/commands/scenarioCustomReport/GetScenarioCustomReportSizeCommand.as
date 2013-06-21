//package il.co.topq.systems.report.commands.scenarioCustomReport
//{
//	import com.adobe.cairngorm.commands.ICommand;
//	import com.adobe.cairngorm.control.CairngormEvent;
//	
//	import il.co.topq.systems.report.business.scenarioCustomReport.GetScenarioCustomReportSizeEventDelegate;
//	import il.co.topq.systems.report.events.scenarioCustomReport.GetScenarioCustomReportSizeEvent;
//	import il.co.topq.systems.report.models.ReportModelLocator;
//	
//	import mx.controls.Alert;
//	import mx.formatters.NumberFormatter;
//	import mx.rpc.IResponder;
//	
//	public class GetScenarioCustomReportSizeCommand implements ICommand, IResponder
//	{
//		public var numberFormatter:NumberFormatter;
//		
//		private var model: ReportModelLocator = ReportModelLocator.getInstance();
//		
//		public function execute(event:CairngormEvent): void
//		{
//			var evt:GetScenarioCustomReportSizeEvent = event as GetScenarioCustomReportSizeEvent;
//			var getScenarioCustomReportSizeEventDelegate:GetScenarioCustomReportSizeEventDelegate 
//			= new GetScenarioCustomReportSizeEventDelegate(this);			
//			
//			getScenarioCustomReportSizeEventDelegate.getScenarioCustomReportSize(evt.filter);				
//		}
//		private function setNavigation():void{
//			if (model.navigation.index == 0){
//				model.navigation.isPrevEnable = false;
//			}else{
//				model.navigation.isPrevEnable = true;
//			}
//			
//			if ((model.numberOfPages  == model.navigation.index + 1) || (model.numberOfPages == 0)){
//				model.navigation.isNextEnable = false;
//			}
//			else{
//				model.navigation.isNextEnable = true;
//			}
//		}
//		
//		public function result(data:Object): void
//		{				
//			var xml:XML = new XML(data.message.body);
//			var sizeOfQuery:int;
//			var numberOfPages:int;			
//			sizeOfQuery = xml.toString();
//			numberOfPages = sizeOfQuery / model.gridNumberOfRows;	
//			if ( ( sizeOfQuery % model.gridNumberOfRows ) > 0){
//				numberOfPages++;
//			}
//			model.numberOfPages = numberOfPages;
//			setNavigation(); 
//		}
//		
//		public function fault(info:Object): void
//		{
//			Alert.show("GetScenarioCustomReportSizeCommand service was not initialized correctly");
//		}
//		
//	}
//}
