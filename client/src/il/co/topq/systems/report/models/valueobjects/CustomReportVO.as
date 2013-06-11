package il.co.topq.systems.report.models.valueobjects
{
	import flash.utils.Dictionary;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ArrayList;
	import mx.collections.XMLListCollection;
	import mx.controls.List;
	import mx.utils.ArrayUtil;
	
	import org.as3commons.collections.Set;
	

	//This class needs to be deleted once refactor of custom report scope in application is done.
	public class CustomReportVO
	{
		public function CustomReportVO()
		{
			initCustomReportVO();
			
		}
		
		public function initCustomReportVO():void{
			propertiesSet = new Set();
		}
		[Bindable]
		public var type:String;
		
		[Bindable]
		public var name:String;
		
		[Bindable]
		public var description:String;
		
		[Bindable][Transient]
		public var propertiesSet:Set;
		
		[Bindable]
		public var properties:Array = new Array();
		
		[Bindable]		
		public var dateOfCreation:Date;
		
		[Bindable][Transient]	
		public var maxProperties:Number = 9;
		
		[Bindable]
		public var propertiesList:XMLList = new XMLList;
		
		
		
		
		

	}
}