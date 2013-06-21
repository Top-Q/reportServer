//package il.co.topq.systems.report.commands.test
//{	
//	import com.adobe.cairngorm.commands.ICommand;
//	import com.adobe.cairngorm.control.CairngormEvent;
//	
//	import il.co.topq.systems.report.business.test.GetTestPropertiesKeysEventDelegate;
//	import il.co.topq.systems.report.events.test.GetTestPropertiesKeysEvent;
//	import il.co.topq.systems.report.models.ReportModelLocator;
//	import il.co.topq.systems.report.views.components.reports.wizards.PropertiesWizard;
//	
//	import mx.controls.Alert;
//	import mx.rpc.IResponder;
//	import mx.rpc.events.ResultEvent;
//	
//	public class GetTestPropertiesKeysCommand implements ICommand, IResponder
//	{
//		private var model: ReportModelLocator = ReportModelLocator.getInstance();
//		private var propertiesWizard:PropertiesWizard ;
//		
//		public function execute(event:CairngormEvent): void
//		{
//			var evt:GetTestPropertiesKeysEvent = event as GetTestPropertiesKeysEvent;			
//			propertiesWizard = evt.propertiesWizard;
//			var getTestPropertiesEventDelegate:GetTestPropertiesKeysEventDelegate = new GetTestPropertiesKeysEventDelegate(this);
//			getTestPropertiesEventDelegate.getTestsProperties();			
//		}
//		
//		public function result(data:Object): void
//		{			
//			var evt:ResultEvent = data as ResultEvent;			
//			model.scenarioProperties = new XML(evt.message.body);
//			if (propertiesWizard != null){
////				propertiesWizard.propertiesXml = model.scenarioProperties;
////				propertiesWizard.appendGridColumnsToWizard();
//			}
//		}
//		
//		public function fault(info:Object): void
//		{
//			Alert.show("GetTestPropertiesValuesCommand is not working properly");
//		}
//	}
//}