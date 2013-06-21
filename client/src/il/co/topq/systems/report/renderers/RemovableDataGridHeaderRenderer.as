package il.co.topq.systems.report.renderers
{
	import flash.events.Event;
	import flash.events.MouseEvent;
	
	import mx.containers.Canvas;
	import mx.containers.HBox;
	import mx.controls.Alert;
	import mx.controls.DataGrid;
	import mx.controls.Image;
	import mx.controls.Label;
	import mx.controls.dataGridClasses.DataGridColumn;
	import mx.core.IFactory;
	import mx.states.OverrideBase;
	
	import spark.components.BorderContainer;
	
	

	
	public class RemovableDataGridHeaderRenderer extends HBox implements IFactory
	{
		private var img:Image;
		private var imgTooltip:String = "Remove Column";
		private var text:Label; //header text;
		
		[Embed(source="/assets/images/removeColumnIcon.png")]
		[Bindable]
		public var removeColumn:Class;
		
		public function get headerImg():Image {
			return img;
		}
		public function set headerImg(value:Image):void{
			this.img = value;
		}
		public function get headerText():String {
			return this.text.text;
		}
		
		public function newInstance():*{
			return new RemovableDataGridHeaderRenderer();
		}
		
		public function RemovableDataGridHeaderRenderer(){
			super();
			
			initHeaderRenderer();			
			initImg();
			
		}
		
		private function initHeaderRenderer():void
		{
			this.percentWidth = 100;
			this.setStyle("horizontalAlign","center");
			this.setStyle("verticalAlign","top");
			this.text = new Label();
			this.text.percentWidth = 100;
		}
		
		private function initImg():void{
			img = new Image();
			img.source = removeColumn;
			img.toolTip = imgTooltip;
			img.autoLoad = true;
			img.maintainAspectRatio = true;
			img.scaleContent = true;
			img.useHandCursor = true;
			img.buttonMode = true;
			img.mouseChildren = true;
		}
		
		override public function set data(value:Object):void {
			if(value is DataGridColumn)
			{
				var column:DataGridColumn = value as DataGridColumn
				
				text.text = column.headerText;
				this.addChild(text);
				this.addChild(img);
			}
		}
		
		override public function get data():Object {
			return null; 
		}
		
		override protected function createChildren():void
		{
			super.createChildren();		
			
			img.addEventListener(MouseEvent.CLICK,imgClicked);
		}
		
		override protected function updateDisplayList(unscaledWidth:Number,
													  unscaledHeight:Number):void {
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			this.img.setActualSize(this.img.getExplicitOrMeasuredWidth(),
				this.img.getExplicitOrMeasuredHeight());
		}
		
		protected function imgClicked(event:MouseEvent):void
		{
			var e : MouseEvent = new MouseEvent("removeColumnImgClick");
			dispatchEvent(e);
		}
	}
}