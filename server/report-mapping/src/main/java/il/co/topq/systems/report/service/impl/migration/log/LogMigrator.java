package il.co.topq.systems.report.service.impl.migration.log;

import java.util.List;

import il.co.topq.systems.report.common.model.Scenario;
import il.co.topq.systems.report.service.impl.migration.Migrator;
import il.co.topq.systems.report.service.impl.migration.infra.MigratorTask;
import il.co.topq.systems.report.service.infra.ScenarioService;

public class LogMigrator extends Migrator  implements MigratorTask {

	private ScenarioService scenarioService;

	public LogMigrator(ScenarioService scenarioService) {
		super();
		this.scenarioService = scenarioService;
	}

	@Override
	public void migrate() {
		try {
			super.loadPropertiesFile();
		} catch (Exception e) {
			this.log.error("failure in LogMigrator: " + e.getMessage());
		}
		
	}
	
	private void migrateHtmlDir(){
		
		List<Scenario> allSenarios = scenarioService.getAll();
		for (Scenario scenario : allSenarios) {
			String htmlDir = scenario.getHtmlDir();
			
			// the pattern with only the TS of the upload (report-server V1.0) 
			//e.g. '1370352093303'

			boolean isHtmlDirInOldPattern = false; 
			try {
				Integer.parseInt(htmlDir);
				isHtmlDirInOldPattern=true;
			} catch (NumberFormatException e) {
				// in case of not number
			}
			
			if(isHtmlDirInOldPattern){
				
				
				
			}else{
				
				
			}
			
			
		}
		
	}
	
	
	

}
