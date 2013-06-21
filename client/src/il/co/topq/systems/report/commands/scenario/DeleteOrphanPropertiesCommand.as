package il.co.topq.systems.report.commands.scenario
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import il.co.topq.systems.report.business.scenario.DeleteScenarioOrphanPropertiesDelegate;
	import il.co.topq.systems.report.business.test.DeleteTestOrphanPropertiesDelegate;
	import il.co.topq.systems.report.events.scenario.DeleteOrphanPropertiesEvent;
	
	import mx.controls.Alert;
	import mx.rpc.IResponder;
	import mx.rpc.events.ResultEvent;

	public class DeleteOrphanPropertiesCommand implements ICommand
	{
		public function execute(event:CairngormEvent): void
		{
			new DeleteScenarioOrphanPropertiesDelegate().deleteOrphanProperties();	
			new DeleteTestOrphanPropertiesDelegate().deleteOrphanProperties();
		}
	}
}