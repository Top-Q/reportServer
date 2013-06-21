package il.co.topq.systems.report.business.config
{
	import il.co.topq.systems.report.events.config.SetUIConfigurationsEvent;
	import il.co.topq.systems.report.models.ReportModelLocator;
	import il.co.topq.systems.report.models.valueobjects.uiConfigurations.GridUIConfiguration;
	
	import mx.collections.ArrayCollection;
	import mx.controls.dataGridClasses.DataGridColumn;

	public class GridColumnRemover
	{
		public function GridColumnRemover()
		{
		}
		public static function removeColumn(gridColumns:Array, unremovableColumns:ArrayCollection, headerText:String, gridDefaultColumns:ArrayCollection, gridUIConfiguration:GridUIConfiguration):Array{
			
			var columnsWithoutPermanantColumns:Array = new Array();
			var newColumns:Array = new Array();
			for (var i:int = 0; i<gridColumns.length; i++){
				if ((gridColumns[i] as DataGridColumn).headerText != headerText){
					newColumns.push(gridColumns[i] as DataGridColumn);
				}
			}
			
			gridUIConfiguration.update(unremovableColumns,gridDefaultColumns,newColumns);
//			new SetUIConfigurationsEvent().dispatch();

			return newColumns;
		}
		
	}
}