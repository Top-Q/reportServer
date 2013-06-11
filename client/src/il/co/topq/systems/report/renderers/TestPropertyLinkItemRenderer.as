package il.co.topq.systems.report.renderers
{
	import flash.events.MouseEvent;
	import flash.net.URLRequest;
	import flash.net.navigateToURL;
	
	import il.co.topq.systems.report.events.test.GetTestScenarioEvent;
	import il.co.topq.systems.report.models.valueobjects.Scenario;
	import il.co.topq.systems.report.models.valueobjects.Test;
	import il.co.topq.systems.report.models.valueobjects.TestProperty;
	import il.co.topq.systems.report.services.ReportServiceLocator;
	
	import mx.collections.ArrayCollection;
	import mx.containers.HBox;
	import mx.controls.Alert;
	import mx.controls.LinkButton;
	import mx.controls.dataGridClasses.MXDataGridItemRenderer;
	import mx.core.IFactory;
	import mx.rpc.events.ResultEvent;
	
	public class TestPropertyLinkItemRenderer extends MXDataGridItemRenderer implements IFactory
	{
		private var hbox:HBox;
		private var linkButton:LinkButton;
		private var imgTooltip:String;
		private var NOT_FOUND:int = -1;
		
		protected override function createChildren():void{
			super.createChildren();
			this.hbox = new HBox;
			this.hbox.percentWidth = 100;
			this.hbox.setStyle("horizontalAlign","center");
			this.hbox.setStyle("verticalAlign","middle");
			
			linkButton = new LinkButton();
			linkButton.label = "Link";
			linkButton.setStyle("rollOverColor","#2E44C7");
			linkButton.setStyle("textRollOverColor","#ffffff");
			linkButton.addEventListener(MouseEvent.CLICK,linkButtonClicked);
			hbox.addChild(linkButton);
			addElement(hbox);
		}
		
		protected function linkButtonClicked(event:MouseEvent):void
		{
			new GetTestScenarioEvent(data.id,scenarioCallbackResult,scenarioCallbackFault).dispatch();
		}
		public function scenarioCallbackResult(event:ResultEvent): void
		{
			var testLinkFileName:String = retrieveTestLinkFileName();
			var test:Test = (data as Test);
			if (testLinkFileName == null){
				Alert.show("file not attached");
			}else{
				var scenarioXML : XML = new XML(event.message.body);
				var scenario:Scenario = new Scenario(scenarioXML);
				var testIndex:int = getTestIndexFromScenarioTests(scenario, test);
				if (testIndex != NOT_FOUND){
					var url:String = ReportServiceLocator.domain;
					var urlRequest:URLRequest = new URLRequest("/results/"+scenario.htmlDir+"/test_" + testIndex +"/"+testLinkFileName);
					navigateToURL(urlRequest);		
				}
			}
		}
		
		/**
		 *This method will go over all the scenario tests and will find the test's index.
		 */
		private function getTestIndexFromScenarioTests(scenario:Scenario, test:Test) : int{

			for(var i:int =0 ; i<scenario.tests.length; i++) 
			{
				var t:Test = scenario.tests.getItemAt(i) as Test;
				if (t.id == test.id){
					return (i+1);
				}
				
			}
			
			return NOT_FOUND; 
			
			
		}
		
		private function retrieveTestLinkFileName():String {
			var testProperties:ArrayCollection = (data as Test).testProperties;
			for each (var property:TestProperty in testProperties) 
			{
				if (property.propertyKey == "TestLink"){
					return property.propertyValue;
				}
			}
			return null;
		}
		
		public function scenarioCallbackFault(info:Object): void
		{
			Alert.show("Failed to retrieve test's scenario");
			//				var event:FaultEvent = FaultEvent(info);
			//				var model:ReportModelLocator = ReportModelLocator.getInstance();
			//				model.error.setMessage(event,"Could not get retrieve test scenario");
			//				model.applicationState.currentState = "ErrorPage";	
		}
		
		protected override function commitProperties():void
		{
			super.commitProperties();
			var fileName:String = retrieveTestLinkFileName();
			if (fileName != null){
				linkButton.label = fileName;
			}else{
				linkButton.label = "/";
			}
		}
		
		public function newInstance():*{
			return new TestPropertyLinkItemRenderer();
		}
		
		public function TestPropertyLinkItemRenderer(){
			super();
		}
		
	}
}