package il.co.topq.systems.report.models.valueobjects.uiConfigurations
{
	import il.co.topq.systems.report.events.config.SetUIConfigurationsEvent;
	
	import mx.collections.ArrayCollection;
	import mx.controls.dataGridClasses.DataGridColumn;

	public class GridUIConfiguration
	{
		public var fieldColumns:ArrayCollection;
		public var propertiesColumns:ArrayCollection;
		public var id:String;
		private var xml:XML;
		private var name:String;
		
		public function GridUIConfiguration(name:String, xml:XML = null, id:String = ""){
			this.xml = xml;
			this.name = name;
			this.id = id;
			emptyColumns();
			fromXML();
		}
		
		private function fromXML():void{
			if (this.xml != null){
				this.id = xml.@id;
				var fields : XMLList = xml.fields.field as XMLList ;
				for (var i:int = 0; i<fields.length(); i++){
					this.fieldColumns.addItem(fields[i]);
				}
				
				var properties : XMLList = xml.properties.property as XMLList ;
				for (var j:int = 0; j<properties.length(); j++){
					this.propertiesColumns.addItem(properties[j]);
				}
			}
		}
		
		public function toXML():XML{
			var xml:XML = <{this.name} id={this.id}></{this.name}>;
			var fieldsXml:XML = <fields></fields>;
			var propertiesXml:XML = <properties></properties>;
			var xmlList:XMLList;
			var str:String = "";
			for (var i:int = 0; i<this.fieldColumns.length; i++){
				str += "<field>" + (this.fieldColumns[i]) + "</field>";
			}
			xmlList = XMLList(str);
			fieldsXml.appendChild(xmlList);
			
			str = "";
			for (var j:int = 0; j<this.propertiesColumns.length; j++){
				str += "<property>" + (this.propertiesColumns[j]) + "</property>";
			}
			xmlList = XMLList(str);
			propertiesXml.appendChild(xmlList);
			xml.appendChild(fieldsXml);
			xml.appendChild(propertiesXml);
			return xml;
		}
		
		public function update(permenantColumns:ArrayCollection, defaultColumns:ArrayCollection, currentGridColumns:Array):void {
			emptyColumns();
			
			//take out the permenant columns --> they should not be saved as part of the configurations.
			var columnsWithoutPermanantColumns:Array = new Array();
			for (var j:int = 0; j<currentGridColumns.length; j++){
				if (!(permenantColumns.contains((currentGridColumns[j] as DataGridColumn).headerText))){
					columnsWithoutPermanantColumns.push((currentGridColumns[j] as DataGridColumn).headerText);
				}
			}			
			
			//determine which column is property and which is field.
			for (var i:int = 0; i<columnsWithoutPermanantColumns.length; i++){
				if (defaultColumns.contains(columnsWithoutPermanantColumns[i])){
					this.fieldColumns.addItem(columnsWithoutPermanantColumns[i]);
				}else{
					this.propertiesColumns.addItem(columnsWithoutPermanantColumns[i]);
				}
			}
			new SetUIConfigurationsEvent().dispatch();
		}
		
		public function emptyColumns():void {
			this.fieldColumns = new ArrayCollection();
			this.propertiesColumns = new ArrayCollection();
		}
	}
}