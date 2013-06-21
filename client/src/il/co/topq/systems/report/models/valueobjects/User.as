/**
 * This Object represents the model for the report system user.
 * Each user has a username,password and a session after having logged in.
 * The password is canceled immediatly after having logged in for security reason.
 */
package il.co.topq.systems.report.models.valueobjects
{
	import mx.collections.ArrayCollection;

	[Bindable]
	public class User
	{
		public var username:String = "";
		public var password:String = "";
		public var firstName:String = "";
		public var lastName:String = "";
		public var permissions:String = "";
		public var email:String = "";
		public var id:String = "";
		private var xml:XML;
		public var permissionsArr:ArrayCollection = new ArrayCollection();
		
		public function User(xml:XML = null)
		{
			if(xml != null){
				this.xml = xml;
				populateScenarioFromXML();
			}	
		}
		
		public function toXML ():XML {
			var xml:XML;
			if (this.id != null && this.id.length >0){
				xml= <user id={id}></user>;
			}else {
				xml = <user></user>;
			}
			var str:String = "";
			str += "<firstName>" + firstName + "</firstName>";
			str += "<lastName>" + lastName + "</lastName>";
			str += "<email>" + email + "</email>";
			str += "<username>" + username + "</username>";
			str += "<password>" + password + "</password>";
			str += "<permissions>" + permissionsArrayToString() + "</permissions>";
			var xMLList:XMLList = XMLList(str);
			xml.appendChild(xMLList);
			return xml;
		}
		
		private function populateScenarioFromXML():void
		{
			this.id = xml.@id;
			this.username = xml.username;
			this.password = xml.password;
			this.firstName = xml.firstName;
			this.lastName = xml.lastName;
			this.permissions = xml.permissions;
			this.email = xml.email;
			permissionsStringToArray();
		}
		
		private function permissionsStringToArray():void {
			
			var split:Array = permissions.split(',');
			for (var i:int = 0; i< split.length; i++) 
			{	
				if (split[i].toString().charAt(0) == ' '){
					split[i] = split[i].toString().substr(1, split[i].toString().length);//trim white space
				}
				if (split[i].length > 0){
					this.permissionsArr.addItem(split[i]);
				}
			}
		}
		
		private function permissionsArrayToString():String {
			var res:String = "";
			for each (var s:String in this.permissionsArr) 
			{
				res += s + ", ";
			}
			return res;
		}
		
		public function clone():User {
			return new User(xml);
		}
		
	}
}