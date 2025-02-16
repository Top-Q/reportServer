package il.co.topq.systems.report.business.test
{
	
	import flash.net.FileReference;
	import flash.net.URLRequest;
	import flash.net.URLRequestMethod;
	import flash.net.URLVariables;
	
	import il.co.topq.systems.report.models.valueobjects.TestProperty;
	import il.co.topq.systems.report.models.valueobjects.TestQuery;
	import il.co.topq.systems.report.services.ReportServiceLocator;
	import il.co.topq.systems.report.views.components.reports.common.FileDownloadWindowViewModel;
	import il.co.topq.systems.report.views.components.reports.tools.FileDownloadWindow;
	
	public class ExportTestDelegate
	{
		
		public const FILE_URL:String = ReportServiceLocator.domain + "/" + ReportServiceLocator.appName + "/export-service/export/test/";
		private var fileRef:FileReference;
		private var urlReq:URLRequest;
		private var urlVars:URLVariables;
		private var testQuery:TestQuery;
		private var fileDownloadWindow:FileDownloadWindow;
		
		public function exportTest(fileName:String,fileType:String,testQuery:TestQuery) : void{
			/* Set up the URL request to download the file specified by the FILE_URL variable. */
			urlReq = new URLRequest(FILE_URL+fileType);
			fileRef = new FileReference();
			urlVars = new URLVariables();	
			this.testQuery = testQuery;
			urlReq.method = URLRequestMethod.POST;			
			setURLVeriables();
			urlReq.data = urlVars;
			fileDownloadWindow = new FileDownloadWindowViewModel(fileRef,urlReq,fileName);
			
		}
		private function setURLVeriables():void{
			
			setTimeRangeVeriables();
			setProperties();
			setSortingColumn();
		}
		
		private function setProperties():void
		{
			var propValue:String = "";
			if (testQuery.testProperties.length != 0){
				for each (var testProperty:TestProperty in testQuery.testProperties) 
				{
					propValue += testProperty.propertyKey + "=" + testProperty.propertyValue + ";";	
				}
				urlVars.properties = propValue;	
			}
		}
		
		private function setSortingColumn():void{
			if (testQuery.sortingColumn != null){
				urlVars.sortingColumnName = testQuery.sortingColumn.name;
				urlVars.sortingColumnAsc = testQuery.sortingColumn.asc;
			}
		}
		
		private function setTimeRangeVeriables():void{
			
			if (testQuery.timeRange != null){
				urlVars.lowBoundDate = testQuery.timeRange.lowBoundDate;
				urlVars.upBoundDate = testQuery.timeRange.upBoundDate;
			}
			
		}
	}
}


