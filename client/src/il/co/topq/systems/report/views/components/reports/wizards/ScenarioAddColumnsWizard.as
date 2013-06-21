package il.co.topq.systems.report.views.components.reports.wizards
{
	import il.co.topq.systems.report.events.scenario.GetScenarioPropertiesKeysEvent;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.rpc.events.ResultEvent;
	
	import org.as3commons.collections.Set;
	import org.as3commons.collections.framework.IIterator;

	public class ScenarioAddColumnsWizard extends PropertiesWizard
	{
		protected var defaultScenarioColumns:ArrayCollection;
		protected var configuredColumns:Set;
		
		public function ScenarioAddColumnsWizard(defaultScenarioColumns:ArrayCollection)
		{
			super();
			this.defaultScenarioColumns = defaultScenarioColumns;
			properties = new ArrayCollection();
			_target = new ArrayCollection();
			available = new ArrayCollection();
			configuredColumns = new Set();
			setWizardTitle();
			setWizardDescription();
			//Get Execution Grid configuration from server. if not exist use default scenario columns.
			new GetScenarioPropertiesKeysEvent(getScenarioPropertiesKeysCallbackResult, getScenarioPropertiesKeysCallbackFault).dispatch();
		}
		
		/**
		 * This method will set the Wizard's title.
		 */ 
		private function setWizardTitle():void{
			title = "Add or Remove columns from Executions report"; 		
		}
		
		/**
		 * This method will set the wizard's description.
		 */ 
		private function setWizardDescription():void{
			description = "Customize your  grid, drag and drop properties / available columns";
		}
		
		/**
		 * This method will get the properties from server and will put them into properties Set.
		 */ 
		protected function getScenarioPropertiesKeysCallbackResult(data:ResultEvent): void
		{			
			setPropertiesFromXML(new XML(data.message.body));
			setAvailableList();
			setTargetList();
		}
		
		/**
		 * This method will retrieve the properties keys from xml and will put them into propertiesSet;
		 * @parameters: XML - properties keys xml.
		 */ 
		private function setPropertiesFromXML(propertiesKeysXML:XML): void {

			var xmllist:XMLList = (propertiesKeysXML.property) ;
			if (xmllist!=null){
				for (var i:int=0; i<xmllist.length(); i++){
					properties.addItem(new String(xmllist[i].propertyKey)); 
				}
			}		
		}
		
		/**
		 * This method will put all the properties that were fetched into the available list.<br>
		 * It will also put the scenario grid columns that were not in the configured columns.<br>
		 * Example: if user configured a grid without the scenario duration column, than the duration will apear 
		 * in the available list.<br>;
		 * This is to support the customization of the grid.
		 */ 
		protected function setAvailableList():void {
			//Put the properties in the available.
			available.addAll(properties);
			
			//If there is a configuration for the grid.
			if (configuredColumns.size > 0){
				//Will put the default columns that are not in the configured grid into available list.
				for (var i:int =0; i<defaultScenarioColumns.length; i++){
					if (!(configuredColumns.has(defaultScenarioColumns[i]))){
						available.addItem(defaultScenarioColumns[i]);
					}
				}
			}
		}
		
		/**
		 * This method will set the target list.<br>
		 * If there is a previous configuration than all of its data will be put into the target list.<Br>
		 * If there is NO previous configuration than will put all the default values into target list.<br>
		 */
		protected function setTargetList():void {
			if (configuredColumns.size > 0){
				var configuredIterator:IIterator = configuredColumns.iterator();
				while (configuredIterator.hasNext()){
					_target.addItem(configuredIterator.next());
				}
			}else {
				_target.addAll(defaultScenarioColumns);
			}
		}
		
		protected function getScenarioPropertiesKeysCallbackFault(info:Object): void
		{
			Alert.show("GetScenarioPropertiesKeysCommand is not working properly");
		}
	}
}