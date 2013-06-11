package il.co.topq.systems.report.views.components.reports.common
{
	import flash.events.Event;
	import flash.events.IOErrorEvent;
	import flash.events.ProgressEvent;
	import flash.net.FileReference;
	import flash.net.URLRequest;
	
	import il.co.topq.systems.report.views.components.reports.tools.FileDownloadWindow;
	
	import mx.core.FlexGlobals;
	import mx.core.UIComponent;
	import mx.managers.PopUpManager;
	
	public class FileDownloadWindowViewModel extends FileDownloadWindow
	{
		private var fileRef:FileReference;
		private var urlReq:URLRequest;
		private var fileName:String;
		
		public function FileDownloadWindowViewModel(fileRef:FileReference, urlReq:URLRequest, fileName:String)
		{
			super();
			this.fileName = fileName;
			this.urlReq = urlReq;
			this.fileRef = fileRef;
			AddListners();
			download();
		}
		
		private function download():void
		{
			fileRef.download(urlReq,fileName);			
		}
		
		private function AddListners():void
		{
			fileRef.addEventListener(Event.SELECT, fileRef_select);
			fileRef.addEventListener(Event.COMPLETE, completeHandler);
			fileRef.addEventListener(IOErrorEvent.IO_ERROR, fileRef_ioError);	
			fileRef.addEventListener(ProgressEvent.PROGRESS,setDownloadProgress);
		}
		override protected function fileRef_ioError(event:IOErrorEvent):void
		{
			fileRef.cancel();
			textAreaStatus.text = "Download Error";
			isCloseButtonVisible = true;				
		}
		
		override protected function fileRef_select(event:Event):void
		{
			PopUpManager.addPopUp(this,(UIComponent)(FlexGlobals.topLevelApplication),false);
			PopUpManager.centerPopUp(this);
		}
		
		override protected function setDownloadProgress(event:ProgressEvent):void{
			pbProgress.setProgress(event.bytesLoaded, event.bytesTotal);
			pbProgress.label = "Downloading [" + Math.round(event.bytesLoaded / event.bytesTotal * 100).toString() + "%]";
		}
		
		override protected function completeHandler(event:Event):void{
			textAreaStatus.text = "Downloading Completed";					
			pbProgress.indeterminate = false;
			isCloseButtonVisible = true;
		}   
		
	}
}