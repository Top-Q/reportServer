package il.co.topq.systems.report.views.components.reports.wizards
{
	import il.co.topq.systems.report.events.scenario.GetScenarioPropertiesKeysEvent;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.rpc.events.ResultEvent;

	public class StatisticsPropertiesWizard extends PropertiesWizard
	{
		public function StatisticsPropertiesWizard()
		{
			super();
			properties = new ArrayCollection();
			_target = new ArrayCollection();
			available = new ArrayCollection();
			setWizardTitle();
			setWizardDescription();
			//Get Execution Grid configuration from server. if not exist use default scenario columns.
			new GetScenarioPropertiesKeysEvent(getScenarioPropertiesKeysCallbackResult, getScenarioPropertiesKeysCallbackFault).dispatch();
		}
		
		/**
		 * This method will set the Wizard's title.
		 */ 
		private function setWizardTitle():void{
			title = "Statistics Properties"; 		
		}
		
		/**
		 * This method will set the wizard's description.
		 */ 
		private function setWizardDescription():void{
			description = "Drag and drop properties";
		}
		
		/**
		 * This method will get the properties from server and will put them into properties Set.
		 */ 
		protected function getScenarioPropertiesKeysCallbackResult(data:ResultEvent): void
		{			
			setPropertiesFromXML(new XML(data.message.body));
		}
		
		protected function getScenarioPropertiesKeysCallbackFault(info:Object): void
		{
			Alert.show("GetScenarioPropertiesKeysCommand is not working properly");
		}
		
		/**
		 * This method will retrieve the properties keys from xml and will put them into propertiesSet;
		 * @parameters: XML - properties keys xml.
		 */ 
		private function setPropertiesFromXML(propertiesKeysXML:XML): void {
			
			var xmllist:XMLList = (propertiesKeysXML.property) ;
			if (xmllist!=null){
				for (var i:int=0; i<xmllist.length(); i++){
					available.addItem(new String(xmllist[i].propertyKey)); 
				}
			}		
		}
	}
}