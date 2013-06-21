package il.co.topq.systems.report.models.valueobjects
{
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	
	public class CustomReport
	{
		protected var xml:XML;
		
		public var id:String;
		public var name:String;
		public var description:String;
		public var dateOfCreation:String;
		public var properties:ArrayCollection;
		
		public function CustomReport(xml:XML = null)
		{
			this.properties = new ArrayCollection();
			if(xml != null){
				this.xml = xml;
				populateTestCustomReportFromXML();
			}	
		}
		
		private function populateTestCustomReportFromXML():void
		{
			this.name = xml.name;
			this.description = xml.description;
			this.id = xml.@id;
			this.dateOfCreation = xml.dateOfCreation;
			setPropertiesFromXML();
		}
		
		/**
		 * This method will parse the customReport xml and will retrieve
		 * The Properties of this object.
		 * Will assign the values into properties:arrayCollection.
		 * @return void;
		 */
		protected function setPropertiesFromXML():void
		{
			Alert.show("This method must be implemented in inherited class");
		}
	}
}