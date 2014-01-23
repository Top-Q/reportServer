package il.co.topq.systems.report.business.scenario
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
	
	public class ExportScenarioDelegate
	{
		public const FILE_URL:String = ReportServiceLocator.domain + "/" + ReportServiceLocator.appName + "/export-service/export/scenario/";
		
		public var fileDownloadWindow :FileDownloadWindow;
		private var fileRef:FileReference;
		private var urlReq:URLRequest;
		private var urlVars:URLVariables;
		private var scenarioQuery:ScenarioQuery;
		
		/**
		 * 
		 * @param fileName
		 * @param fileType
		 * 
		 */
		public function exportScenario(fileName:String,fileType:String,scenarioQuery:ScenarioQuery) : void{
				/* Set up the URL request to download the file specified by the FILE_URL variable. */
				urlReq = new URLRequest(FILE_URL+fileType);
				fileRef = new FileReference();				
				urlVars = new URLVariables();	
				this.scenarioQuery = scenarioQuery;
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
			
			setTimeRangeVeriables();
			setProperties();
			setSortingColumn();
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