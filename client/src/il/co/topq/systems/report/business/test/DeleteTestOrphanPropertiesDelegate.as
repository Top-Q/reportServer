package il.co.topq.systems.report.business.test
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import mx.controls.Alert;
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.http.HTTPService;

	public class DeleteTestOrphanPropertiesDelegate implements IResponder
	{
		private var responder:IResponder;
		public function DeleteTestOrphanPropertiesDelegate()
		{
			this.responder = this;
		}
		
		
		public function deleteOrphanProperties():void {
			var service:HTTPService = ServiceLocator.getInstance().getHTTPService("deleteTestOrphanProperties");
			service.request = new Date();//used to break the chaching of the get request.
			var token:AsyncToken = service.send();
			token.addResponder(responder) ;
		}
		
		public function result(data:Object): void
		{
			var xML:XML = new XML(data.message.body);
			if (xML.toString() != "true"){
				Alert.show("Failed to delete test orphan properties");
			}
		}
		
		/**
		 * This method will be called in case the deletion of orphan properties will fail.
		 */ 
		public function fault(info:Object): void
		{
			Alert.show("Failed to delete test orphan properties");
		}
	}
}
