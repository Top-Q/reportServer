package il.co.topq.systems.report.service.impl;

import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.model.TestCustomReport;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("testCustomReportService")
@Transactional
public class TestCustomReportServiceImpl extends AbstractCustomReportServiceImpl<TestCustomReport, ReportProperty> {

	@Override
	public TestCustomReport create(TestCustomReport testCustomReport) {
		testCustomReport.setProperties(new HashSet<ReportProperty>(propertyDao.createProperties(testCustomReport
				.getProperties())));
		return customReportDao.create(testCustomReport);
	}

	@Override
	public TestCustomReport update(TestCustomReport testCustomReport) throws Exception {
		testCustomReport.setProperties((Set<ReportProperty>) propertyDao.createProperties(testCustomReport
				.getProperties()));
		return customReportDao.update(testCustomReport);
	}

	@Override
	public void updateAll(Collection<TestCustomReport> entities) {
		customReportDao.updateAll(entities);
	}

	@Override
	public Object createIndex(String tableName, String column, String indexName) {
		// TODO Auto-generated method stub
		return null;
	}

}
