package il.co.topq.systems.report.service.impl;

import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.model.ScenarioCustomReport;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("scenarioCustomReportService")
@Transactional
public class ScenarioCustomReportServiceImpl extends
		AbstractCustomReportServiceImpl<ScenarioCustomReport, ReportProperty> {

	@Override
	public ScenarioCustomReport create(ScenarioCustomReport scenarioCustomReport) {
		scenarioCustomReport.setProperties(new HashSet<ReportProperty>(propertyDao
				.createProperties(scenarioCustomReport.getProperties())));
		return customReportDao.create(scenarioCustomReport);
		// scenarioCustomReport.setProperties((Set<ReportProperty>) propertyDao.createProperties(scenarioCustomReport
		// .getProperties()));
		// return customReportDao.create(scenarioCustomReport);
	}

	@Override
	public ScenarioCustomReport update(ScenarioCustomReport scenarioCustomReport) throws Exception {
		scenarioCustomReport.setProperties((Set<ReportProperty>) propertyDao.createProperties(scenarioCustomReport
				.getProperties()));
		return customReportDao.update(scenarioCustomReport);
	}

	@Override
	public void updateAll(Collection<ScenarioCustomReport> entities) {
		customReportDao.updateAll(entities);
	}

	@Override
	public Object createIndex(String tableName, String column, String indexName) {
		// TODO Auto-generated method stub
		return null;
	}

	// TODO: delete should call delete orphan properties..??

}
