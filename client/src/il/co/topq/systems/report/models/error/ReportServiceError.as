package il.co.topq.systems.report.models.error
{
	import mx.rpc.events.FaultEvent;

	public class ReportServiceError
	{
		public function ReportServiceError()
		{
		}
		
		private var _message:String;

		public function get message():String
		{
			return _message;
		}
		
		public function setMessage(event:FaultEvent,message:String = ""):void
		{
			_message = 	message + "\nStatus Code: ";
			if(event != null){
				_message +=  event.statusCode + "\n" +
				"Fault Code: " + event.fault.faultCode + "\n" +
				"Fault String: " + event.fault.faultString + "\n" +
				"Description: " + event.fault.rootCause.text + "\n"
				,"Report Server Error \n\n";
			}
		}
		

	}
}