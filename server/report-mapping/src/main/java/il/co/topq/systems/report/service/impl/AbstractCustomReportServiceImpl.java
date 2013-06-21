package il.co.topq.systems.report.service.impl;

import il.co.topq.systems.report.common.obj.Chunk;
import il.co.topq.systems.report.common.obj.TimeRange;
import il.co.topq.systems.report.dao.infra.CustomReportDao;
import il.co.topq.systems.report.dao.infra.PropertyDao;
import il.co.topq.systems.report.service.infra.CustomReportService;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class AbstractCustomReportServiceImpl<T, S> implements CustomReportService<T> {

	protected CustomReportDao<T> customReportDao;
	protected PropertyDao<S> propertyDao;

	public void setCustomReportDao(CustomReportDao<T> customReportDao) {
		this.customReportDao = customReportDao;
	}

	public void setPropertyDao(PropertyDao<S> propertyDao) {
		this.propertyDao = propertyDao;
	}

	@Override
	public void delete(int id) {
		customReportDao.delete(id);
	}

	@Override
	public long countAll(Map<String, Object> params) {
		return customReportDao.countAll(params);
	}

	@Override
	public T get(long id) {
		return customReportDao.get(id);
	}

	@Override
	public List<T> getAll() {
		return customReportDao.getAll();
	}

	@Override
	public int getSizeOfCustomReportByFilter(TimeRange timeRange) {
		return customReportDao.getSizeOfCustomReportByFilter(timeRange);
	}

	@Override
	public List<T> getCustomReport(Chunk chunk, TimeRange timeRange) {
		return customReportDao.get(chunk, timeRange);
	}

	@Override
	public List<T> getChunk(final Chunk chunk) {
		return customReportDao.get(chunk);
	}

}
