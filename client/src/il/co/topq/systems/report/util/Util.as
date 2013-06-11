package il.co.topq.systems.report.util
{
	import mx.collections.ArrayCollection;
	import mx.controls.dataGridClasses.DataGridColumn;

	public class Util
	{
		public static function arrayToArrayCollection(arr:Array):ArrayCollection{
			var arrCol:ArrayCollection = new ArrayCollection();
			for (var i:int = 0; i<arr.length; i++){
				arrCol.addItem((arr[i] as DataGridColumn).headerText);
			}
			return arrCol;
		}
	}
}