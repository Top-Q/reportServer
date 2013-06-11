/**
 * This Object represents the model for the report system user.
 * Each user has a username,password and a session after having logged in.
 * The password is canceled immediatly after having logged in for security reason.
 */
package il.co.topq.systems.report.models.valueobjects
{
	public class User
	{
		public var username:String;
		public var password:String;
		public var firstName:String;
		public var lastName:String;
		public var permissions:String;
		public var id:String;
		private var xml:XML;

		public function User(xml:XML = null)
		{
			if(xml != null){
				this.xml = xml;
				populateScenarioFromXML();
			}	
		}
		
//		public function User(username:String ="", password:String="",session:String=""){
//			this.username = username;
//			this.password = password;
//		}
		
		private function populateScenarioFromXML():void
		{
			this.id = xml.@id;
			this.username = xml.username;
			this.password = xml.password;
			this.firstName = xml.firstName;
			this.lastName = xml.lastName;
			this.permissions = xml.permissions;
		}
		

	}
}