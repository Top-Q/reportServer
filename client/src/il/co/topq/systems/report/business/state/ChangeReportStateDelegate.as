package il.co.topq.systems.report.business.state
{
	import il.co.topq.systems.report.models.ReportModelLocator;
	
	import mx.rpc.IResponder;

	public class ChangeReportStateDelegate
	{
		private var responder:IResponder;
		
		public function ChangeReportStateDelegate(responder:IResponder)
		{
			this.responder = responder;
		}
		
		public function changeState(state:String):void {
			ReportModelLocator.getInstance().applicationState.reportsState =  state;
			
		}
	}
}