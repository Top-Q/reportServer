package il.co.topq.systems.report.commands.states
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.events.states.ChangeTopLevelEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	
	import mx.controls.Alert;

	public class ChangeTopLevelCommand  implements ICommand
	{
		private var model: ReportModelLocator = ReportModelLocator.getInstance();
		
		public function execute(event:CairngormEvent): void
		{
			var evt:ChangeTopLevelEvent = event as ChangeTopLevelEvent;
			model.applicationState.topLevelState =  evt.targetState;
		}
	}
}