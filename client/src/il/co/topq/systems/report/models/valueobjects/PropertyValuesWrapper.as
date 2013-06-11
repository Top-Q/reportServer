package il.co.topq.systems.report.models.valueobjects
{
	import mx.collections.ArrayCollection;

	[Bindable]
	public class PropertyValuesWrapper
	{
		public var propertyKey:String;
		public var propertyValues:ArrayCollection;
		
		public function PropertyValuesWrapper(propertyValuesWrapperXML:XML):void
		{
			this.propertyValues = new ArrayCollection();
			this.propertyKey = propertyValuesWrapperXML.propertyKey;
			setPropertyValuesFromXMLList(propertyValuesWrapperXML.propertyKeyValueList as XMLList);
		}
		
		public function setPropertyValuesFromXMLList(propertyKeyValueList:XMLList):void {
			for (var i:int = 0; i<propertyKeyValueList.length(); i++){
				propertyValues.addItem(new String(propertyKeyValueList[i]));
			}
		}
		
	}
}