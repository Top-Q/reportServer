package il.co.topq.systems.report.business.scenarioCustomReport
{
	import flash.net.FileReference;
	import flash.net.URLRequest;
	import flash.net.URLRequestMethod;
	import flash.net.URLVariables;
	
	import il.co.topq.systems.report.models.valueobjects.ScenarioProperty;
	import il.co.topq.systems.report.models.valueobjects.ScenarioQuery;
	import il.co.topq.systems.report.services.ReportServiceLocator;
	import il.co.topq.systems.report.views.components.reports.common.FileDownloadWindowViewModel;
	import il.co.topq.systems.report.views.components.reports.tools.FileDownloadWindow;
	
	public class ExportScenarioCustomReportDelegate
	{
		public const FILE_URL:String = ReportServiceLocator.domain+"/report-service/export-service/export/scenarioCustomReport/";	
		private var fileRef:FileReference;
		private var urlReq:URLRequest;		
		private var urlVars:URLVariables;		
		private var scenarioQuery:ScenarioQuery;		
		private var scenarioCustomReportID : String;
		private var fileDownloadWindow:FileDownloadWindow;
		
		public function exportScenarioCustomReport(fileName:String,fileType:String,scenarioQuery:ScenarioQuery, scenarioCustomReportID:String) : void{
			/* Set up the URL request to download the file specified by the FILE_URL variable. */
			urlReq = new URLRequest(FILE_URL+fileType);
			fileRef = new FileReference();
			urlVars = new URLVariables();	
			this.scenarioQuery = scenarioQuery;
			this.scenarioCustomReportID = scenarioCustomReportID;
			urlReq.method = URLRequestMethod.POST;			
			setURLVeriables();
			urlReq.data = urlVars;
			fileDownloadWindow = new FileDownloadWindowViewModel(fileRef,urlReq,fileName);
		}
		private function setURLVeriables():void{
			
			setTimeRangeVeriables();
			setProperties();
			setScenarioCustomReportID();
			setSortingColumn();
		}
		
		private function setScenarioCustomReportID():void
		{
			urlVars.scenarioCustomReportID = this.scenarioCustomReportID;
		}
		
		private function setProperties():void
		{
			var propValue:String = "";
			
			if (scenarioQuery.scenarioProperties.length != 0){
				for each (var scenarioProperty:ScenarioProperty in scenarioQuery.scenarioProperties) 
				{
					propValue += scenarioProperty.propertyKey + "=" + scenarioProperty.propertyValue + ";";	
				}
				urlVars.properties = propValue;	
			}
		}
		
		private function setSortingColumn():void{
			if (scenarioQuery.sortingColumn != null){
				urlVars.sortingColumnName = scenarioQuery.sortingColumn.name;
				urlVars.sortingColumnAsc = scenarioQuery.sortingColumn.asc;
			}
		}
		
		private function setTimeRangeVeriables():void{
			
			if (scenarioQuery.timeRange != null){
				urlVars.lowBoundDate = scenarioQuery.timeRange.lowBoundDate;
				urlVars.upBoundDate = scenarioQuery.timeRange.upBoundDate;
			}
		}
	}
}