package il.co.topq.systems.report.models.valueobjects
{
	public final class TypeEnum {
		public static const SCENARIO_REPORT:TypeEnum = new TypeEnum("SCENARIO_REPORT");
		public static const TEST_REPORT:TypeEnum = new TypeEnum("TEST_REPORT");
		public static const SCENARIO_DETAILS:TypeEnum = new TypeEnum("SCENARIO_DETAILS");
		public static const SCENARIO_CUSTOM_REPORT:TypeEnum = new TypeEnum("SCENARIO_CUSTOM_REPORT");
		public static const TEST_CUSTOM_REPORT:TypeEnum = new TypeEnum("TEST_CUSTOM_REPORT");
		
		private var type:String;
		
		public function TypeEnum(type:String):void {
			this.type = type;
		}
		
		public function valueOf():String {
			return type;
		}
	}
}