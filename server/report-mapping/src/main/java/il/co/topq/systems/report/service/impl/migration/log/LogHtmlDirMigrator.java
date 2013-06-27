package il.co.topq.systems.report.service.impl.migration.log;

import java.util.List;

import org.springframework.aop.aspectj.annotation.NotAnAtAspectException;

import il.co.topq.systems.report.common.model.Scenario;
import il.co.topq.systems.report.service.impl.migration.Migrator;
import il.co.topq.systems.report.service.impl.migration.infra.MigratorTask;
import il.co.topq.systems.report.service.infra.ScenarioService;

public class LogHtmlDirMigrator extends Migrator implements MigratorTask {

	private ScenarioService scenarioService;

	public LogHtmlDirMigrator(ScenarioService scenarioService) {
		super();
		this.scenarioService = scenarioService;
	}

	@Override
	public void migrate() {

		log.info("Migrating Html log dir(Secnario data)");
		try {

			List<Scenario> allSecnario = scenarioService.getAll();
			for (Scenario scenario : allSecnario) {
				updateScenarioHtmlDir(scenario);
			}

		} catch (Exception e) {
			log.error("The Html log dir failed - " + e.getMessage());
		}

	}

	private void updateScenarioHtmlDir(Scenario scenario) {
			String htmlDir = scenario.getHtmlDir();
			boolean htmlDirIsAnNumber = false;
			
			
			try {
				Integer.parseInt(htmlDir);
			} catch (NumberFormatException e) {
				htmlDirIsAnNumber=true;
			}
			
			
			
			if(htmlDirIsAnNumber){
				String host ="127.0.0.1";
				String port ="8080";
				String apppath="report-service";
				String newPath = "http:\\" + host+":"+port+"\\"+apppath+"\\index.html";
				
				
			}
			
			
			
	}

}
