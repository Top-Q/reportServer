package il.co.topq.systems.report.business.scenario
{
	import flash.net.FileReference;
	import flash.net.URLRequest;
	import flash.net.URLRequestMethod;
	import flash.net.URLVariables;
	
	import il.co.topq.systems.report.services.ReportServiceLocator;
	import il.co.topq.systems.report.views.components.reports.common.FileDownloadWindowViewModel;
	import il.co.topq.systems.report.views.components.reports.tools.FileDownloadWindow;

	public class ExportScenarioDetailsDelegate
	{
//		public const FILE_URL:String = ReportServiceLocator.domain+"/report-service/report/export/scenarioDetailedReport/";
		public const FILE_URL:String = ReportServiceLocator.domain+"/report-service/export-service/export/scenarioDetailedReport/";
		
		private var fileRef:FileReference;		
		private var urlReq:URLRequest;		
		private var urlVars:URLVariables;
		private var scenarioID:String;
		private var fileDownloadWindow:FileDownloadWindow;
		
		public function exportScenarioDetails(fileName:String,fileType:String,scenarioID:String) : void{
			/* Set up the URL request to download the file specified by the FILE_URL variable. */
			urlReq = new URLRequest(FILE_URL+fileType);
			fileRef = new FileReference();				
			urlVars = new URLVariables();	
			this.scenarioID = scenarioID;
			urlReq.method = URLRequestMethod.POST;			
			setURLVeriables();
			urlReq.data = urlVars;
			fileDownloadWindow = new FileDownloadWindowViewModel(fileRef,urlReq,fileName);
		}
		
		/**
		 * This method retrieves the neccecery data from the scenarioQuery object<br>
		 * and attaches it to the urlRequest as veriables.
		 * server will get a multivaluedmap <string,string>.
		 **/
		private function setURLVeriables():void{
			setScenarioID();
		}
		
		private function setScenarioID():void
		{
			urlVars.scenarioID = this.scenarioID;
		}		
	}
}