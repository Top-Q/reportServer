package il.co.topq.systems.report.commands.states
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.events.states.ChangeReportsStateEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;

	public class ChangeReportsStateCommand implements ICommand
	{
		private var model: ReportModelLocator = ReportModelLocator.getInstance();
		
		public function execute(event:CairngormEvent): void
		{
			var evt:ChangeReportsStateEvent = event as ChangeReportsStateEvent;
			model.applicationState.reportsState =  evt.targetState;
		}
	}
	
}