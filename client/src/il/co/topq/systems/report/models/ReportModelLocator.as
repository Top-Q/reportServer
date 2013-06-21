package il.co.topq.systems.report.models
{
	import com.adobe.cairngorm.model.IModelLocator;
	
	import il.co.topq.systems.report.models.error.ReportServiceError;
	import il.co.topq.systems.report.models.grid.ExecutionReportModel;
	import il.co.topq.systems.report.models.grid.TestReportModel;
	import il.co.topq.systems.report.models.valueobjects.ApplicationStateVO;
	import il.co.topq.systems.report.models.valueobjects.CustomReportVO;
	import il.co.topq.systems.report.models.valueobjects.NavigationVO;
	import il.co.topq.systems.report.models.valueobjects.ReportServerService;
	import il.co.topq.systems.report.models.valueobjects.ScenarioCustomReportGridModel;
	import il.co.topq.systems.report.models.valueobjects.ScenarioGridModel;
	import il.co.topq.systems.report.models.valueobjects.SummaryDataVO;
	import il.co.topq.systems.report.models.valueobjects.TestGroupingPolicy;
	import il.co.topq.systems.report.models.valueobjects.TestCustomReportGridModel;
	import il.co.topq.systems.report.models.valueobjects.TestGridModel;
	import il.co.topq.systems.report.models.valueobjects.User;
	import il.co.topq.systems.report.models.valueobjects.uiConfigurations.UIConfiguration;
	import il.co.topq.systems.report.views.components.reports.CustomReports;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ArrayList;
	
	import spark.components.List;
	
	
	public class ReportModelLocator implements IModelLocator
	{
		[Bindable]
		public var uiConfigurations:UIConfiguration;
		
		[Bindable]
		public var systemSettings:TestGroupingPolicy;
		
		[Bindable]
		public  var reportServiceServer:ReportServerService = new ReportServerService();
		
		[Bindable]
		public var customReportPropertiesValuesXml:XML;
		
		[Bindable]
		public var scenarioTestsNames:ArrayList;

		
		[Bindable]
		public var navigation:NavigationVO = new NavigationVO();
		
		//Application state
		[Bindable]
		public var applicationState:ApplicationStateVO = new ApplicationStateVO(); 
		
		[Bindable]
		public var comparedScenarios:XML;
		
		//Data related to the user logged in
		[Bindable]
		public var user:User = new User();
		
		[Bindable]
		public var customReportVO:CustomReportVO = new CustomReportVO();
		
		//Data related to the summary page
		[Bindable]
		public var summaryDataVO:SummaryDataVO = new SummaryDataVO();
		
		//Temporary Data - Should be deleted at the end
		[Bindable]
		public var legacySceanarios:XML;
		
		[Bindable]
		public var scenarioProperties:XML;

		[Bindable]
		public var executionSceanarios:XML;
		
		[Bindable]
		public var scenarioTests:XML;
		
		[Bindable]
		public var testSummaries:XML;
		
		//used for a specific custom report read from server.
		[Bindable]
		public var customReport:XML;
		
		//used for a chunk of custom reports read from server;
		[Bindable]
		public var customReports:XML = new XML(<customReports></customReports>);
	
		[Bindable]
		public var scenarioUsers:XML;
		
		[Bindable]
		public var builds:XML;

		[Bindable]
		public var version:XML;
		
		[Bindable]
		public var scenarioName:XML;
		
		[Bindable]
		public var suts:XML;
		

		[Bindable]
		public var executionReportModel:ExecutionReportModel = new ExecutionReportModel();

		[Bindable]
		public var testReportModel:TestReportModel = new TestReportModel();

		[Bindable]
		public var executionReportTools:ArrayList = new ArrayList(["Time Range","Export to Excel","Export to PDF", "Compare", "Add Columns"]);
		
		[Bindable]
		public var testReportTools:ArrayList = new ArrayList(["Time Range","Export to Excel","Export to PDF", "Add Columns"]);
		
		[Bindable]
		public var executionFilteredCustomReportTool:ArrayList = new ArrayList(["Time Range","Export to Excel", "Export to PDF","Compare","Add Columns"]);
		
		[Bindable]
		public var testFilteredCustomReportTool:ArrayList = new ArrayList(["Time Range","Export to Excel", "Export to PDF","Add Columns"]);
		
		[Bindable]
		public var customReportTools:ArrayList = new ArrayList(["New Custom Report","Test Custom Report", "Execution Custom Report"]);
		
		[Bindable]
		public var customReportType:ArrayList = new ArrayList(["Test","Execution"]);
		
		[Bindable]
		public var currentCustomReportType:String = "Test";
		
		[Bindable]
		public var gridNumberOfRows:Number = 14;
		
		[Bindable]
		public static var gridNumberOfRowsStatic:Number = 14;
		
		[Bindable]
		public var numberOfPages:int = NaN; 		
		
		private static var modelLocator : ReportModelLocator;
		
		[Bindable]
		public var error:ReportServiceError = new ReportServiceError();
		
		public static function getInstance() : ReportModelLocator
		{
			if ( modelLocator == null )
			{
				modelLocator = new ReportModelLocator();
			}
			return modelLocator;
		}
		

//		[Bindable]
//		public static var testGridModel:TestGridModel = new TestGridModel();
		
		public static function getTestGridModel():TestGridModel{
			return new TestGridModel();
		}
		
		public static function getTestCustomReportGridModel():TestCustomReportGridModel{
			return new TestCustomReportGridModel(); 
		}
		
		public static function getScenarioGridModel() : ScenarioGridModel {
			return new ScenarioGridModel();
		}
		
		public static function getScenarioCustomReportGridModel():ScenarioCustomReportGridModel {
			return new ScenarioCustomReportGridModel();
		}
		
		//Constructor should be private but current AS3.0 does not allow it yet (?)
		public function ReportModelLocator()
		{
			if ( modelLocator != null )
			{
				throw new Error( "Only one ModelLocator instance should	be instantiated" );
			}
		}
	}
}