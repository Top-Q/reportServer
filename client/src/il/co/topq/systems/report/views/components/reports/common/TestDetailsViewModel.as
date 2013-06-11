package il.co.topq.systems.report.views.components.reports.common
{
	import il.co.topq.systems.report.models.valueobjects.Test;
	import il.co.topq.systems.report.views.components.reports.tools.TestDetail;
	
	public class TestDetailsViewModel extends TestDetail
	{
		public function TestDetailsViewModel(test:Test)
		{
			super();
			this._test = test;
			this._testClone = test.clone();
			init();
		}
		override protected function init():void {
			formatTestTimes();
			getTestScenario();
		}
	}
}