package il.co.topq.systems.report.models.valueobjects
{
	import mx.controls.Alert;

	[Bindable]
	public class TestDescription
	{
		public var testName:String;
		
		public var testDescription:String;
		
		public function toXML():XML {
			testDescription = stripHtmlTags(testDescription);
			var xml:XML = <testDescription></testDescription>;
			var str:String = "<testName>" + testName + "</testName>";
			str += "<testDescription>" + testDescription + "</testDescription>";
			var xMLList:XMLList = XMLList(str);
			xml.appendChild(xMLList);
			return xml;
		}
		
		private function stripHtmlTags(html:String, tags:String = ""):String
		{
			var tagsToBeKept:Array = new Array();
			if (tags.length > 0)
				tagsToBeKept = tags.split(new RegExp("\\s*,\\s*"));
			
			var tagsToKeep:Array = new Array();
			for (var i:int = 0; i < tagsToBeKept.length; i++)
			{
				if (tagsToBeKept[i] != null && tagsToBeKept[i] != "")
					tagsToKeep.push(tagsToBeKept[i]);
			}
			
			var toBeRemoved:Array = new Array();
			var tagRegExp:RegExp = new RegExp("<([^>\\s]+)(\\s[^>]+)*>", "g");
			
			var foundedStrings:Array = html.match(tagRegExp);
			for (i = 0; i < foundedStrings.length; i++) 
			{
				var tagFlag:Boolean = false;
				if (tagsToKeep != null) 
				{
					for (var j:int = 0; j < tagsToKeep.length; j++)
					{
						var tmpRegExp:RegExp = new RegExp("<\/?" + tagsToKeep[j] + "( [^<>]*)*>", "i");
						var tmpStr:String = foundedStrings[i] as String;
						if (tmpStr.search(tmpRegExp) != -1) 
							tagFlag = true;
					}
				}
				if (!tagFlag)
					toBeRemoved.push(foundedStrings[i]);
			}
			for (i = 0; i < toBeRemoved.length; i++) 
			{
				var tmpRE:RegExp = new RegExp("([\+\*\$\/])","g");
				var tmpRemRE:RegExp = new RegExp((toBeRemoved[i] as String).replace(tmpRE, "\\$1"),"g");
				html = html.replace(tmpRemRE, "");
			} 
			return html;
		}
	}
}