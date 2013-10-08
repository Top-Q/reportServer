package il.co.topq.systems.report.models.valueobjects
{
	import mx.collections.ArrayCollection;

	public class SummaryDataVO
	{
		[Bindable]
		public var totalTests:String;
		
		[Bindable]
		public var totalScenarios:String;
		
		[Bindable]
		public var lastVersionExecution:String;
		
		[Bindable]
		public var testResults:ArrayCollection;
		
		[Bindable]
		public var executionStatistics:ArrayCollection;
		
//		[Bindable]
//		public var executionStatistics:ArrayCollection = new ArrayCollection([
//			{Version:"1", Fail:123, Pass:300, Warning:20},
//			{Version:"2", Fail:54, Pass:200, Warning:40},
//			{Version:"3", Fail:33, Pass:320, Warning:10},
//			{Version:"4", Fail:12, Pass:32, Warning:11},	
//			{Version:"5", Fail:123, Pass:300, Warning:20},
//			{Version:"6", Fail:123, Pass:300, Warning:20},
//			{Version:"7", Fail:54, Pass:200, Warning:40},
//			{Version:"8", Fail:33, Pass:320, Warning:10},
//			{Version:"9", Fail:12, Pass:32, Warning:11},
//			{Version:"10", Fail:123, Pass:300, Warning:20},
//			{Version:"11", Fail:54, Pass:200, Warning:40},
//			{Version:"12", Fail:33, Pass:320, Warning:10},
//			{Version:"13", Fail:12, Pass:32, Warning:11},
//			{Version:"14", Fail:54, Pass:200, Warning:40},
//			{Version:"15", Fail:33, Pass:320, Warning:10},
//			{Version:"16", Fail:12, Pass:32, Warning:11},
//			{Version:"17", Fail:33, Pass:320, Warning:10}
//		]);
		
		[Bindable]
		public var scenarioStatisticsByTimeRange:ArrayCollection;
		
//		[Bindable]
//		public var scenarioStatisticsByTimeRange:ArrayCollection = new ArrayCollection([
//			{Month:new Date(2009,0) , Fail:70, Pass:250, Warning:50},
//			{Month:new Date(2009,4), Fail:67, Pass:270, Warning:40},
//
//			{Month:new Date(2009,3), Fail:70, Pass:250, Warning:50},
//
//			{Month:new Date(2009,5), Fail:90, Pass:320, Warning:30},
//			{Month:new Date(2009,6), Fail:70, Pass:250, Warning:50},
//			
//			{Month:new Date(2009,7), Fail:67, Pass:270, Warning:40},
//			{Month:new Date(2009,8), Fail:90, Pass:320, Warning:30},
//			{Month:new Date(2009,2), Fail:90, Pass:320, Warning:30},
//			{Month:new Date(2009,1), Fail:67, Pass:270, Warning:40}
			
			
/*			{Month:"JAN", Fail:70, Pass:250, Warning:50},
			{Month:"FEB", Fail:67, Pass:270, Warning:40},
			{Month:"MAR", Fail:90, Pass:320, Warning:30},
			{Month:"APR", Fail:70, Pass:250, Warning:50},
			{Month:"MAY", Fail:67, Pass:270, Warning:40},
			{Month:"JUN", Fail:90, Pass:320, Warning:30},
			{Month:"JUL", Fail:70, Pass:250, Warning:50},
			{Month:"AUG", Fail:67, Pass:270, Warning:40},
			{Month:"SEP", Fail:90, Pass:320, Warning:30}*/
		
		public function setTestStatistics(testStatistics:XML):void{
			testResults = new ArrayCollection();
			testResults.addItem({Status:"Fail",Amount:testStatistics.failed});
			testResults.addItem({Status:"Pass",Amount:testStatistics.passed});
			testResults.addItem({Status:"Warning",Amount:testStatistics.warned});
		}
		
		public function setExecutionStatistics(executionStatisticsXml:XML):void {
			this.executionStatistics = new ArrayCollection();
			var scenarioStatistics:XMLList = executionStatisticsXml.scenarioStatistics as XMLList;
			for (var i:int = 0; i<scenarioStatistics.length(); i++){
				executionStatistics.addItem(
					{
						ExecutionType:scenarioStatistics[i].scenarioType,
						Fail:scenarioStatistics[i].testStatistics.failed,
						Pass:scenarioStatistics[i].testStatistics.passed,
						Warning:scenarioStatistics[i].testStatistics.warned
					}
				);
			}
		}
		
		public function setExecutionStatisticsByTimeRange(scenarioStatisticsByTimeRange:XML):void{
			this.scenarioStatisticsByTimeRange = new ArrayCollection();
			var scenarios : XMLList = scenarioStatisticsByTimeRange.scenarios as XMLList;
			for (var i:int = 0; i< scenarios.length(); i++){
				this.scenarioStatisticsByTimeRange.addItem(
					{
						CreateDate:new Date(scenarios[i].startTime),
						Fail:scenarios[i].failTests,
						Pass:scenarios[i].successTests,
						Warning:scenarios[i].warningTests
					}
				);
			}
		}
	}
}