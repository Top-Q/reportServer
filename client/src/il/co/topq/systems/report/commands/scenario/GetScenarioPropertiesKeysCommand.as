//package il.co.topq.systems.report.commands.scenario
//{	
//	import com.adobe.cairngorm.commands.ICommand;
//	import com.adobe.cairngorm.control.CairngormEvent;
//	
//	import il.co.topq.systems.report.business.scenario.GetScenarioPropertiesKeysEventDelegate;
//	import il.co.topq.systems.report.events.scenario.GetScenarioPropertiesKeysEvent;
//	
//	import mx.rpc.IResponder;
//	
//	public class GetScenarioPropertiesKeysCommand implements ICommand, IResponder
//	{
//		public function execute(event:CairngormEvent): void
//		{
//			var evt:GetScenarioPropertiesKeysEvent = event as GetScenarioPropertiesKeysEvent;
//			propertiesWizard = evt.propertiesWizard;
//			var getScenarioPropertiesKeysEventDelegate:GetScenarioPropertiesKeysEventDelegate = new GetScenarioPropertiesKeysEventDelegate(this);
//			getScenarioPropertiesKeysEventDelegate.getScenarioProperties();			
//		}
//		
//	}
//}
