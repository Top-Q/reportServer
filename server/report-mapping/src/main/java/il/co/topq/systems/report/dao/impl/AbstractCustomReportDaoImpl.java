package il.co.topq.systems.report.dao.impl;

import il.co.topq.systems.report.common.obj.Chunk;
import il.co.topq.systems.report.common.obj.TimeRange;
import il.co.topq.systems.report.dao.infra.CustomReportDao;
import il.co.topq.systems.report.utils.QueryUtil;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository("customReportDao")
public abstract class AbstractCustomReportDaoImpl<T> extends GenericDaoImpl<T> implements CustomReportDao<T> {

	@SuppressWarnings(value = "unchecked")
	public List<T> get(Chunk chunk, TimeRange timeRange) {
		StringBuilder sb = new StringBuilder();
		sb.append("from ").append(type.getSimpleName()).append(" as custom_report");

		if (timeRange != null) {
			String timeRangeStr = QueryUtil.createTimeRangeRestrictionQuery(timeRange, "dateOfCreation");
			if (timeRangeStr != null) {
				sb.append(" where ").append(timeRangeStr);
			}
		}
		sb.append(" order by dateOfCreation desc");
		Query query = getSession().createQuery(sb.toString());
		QueryUtil.setQueryResultSize(query, chunk);
		return query.list();
	}

	public int getSizeOfCustomReportByFilter(TimeRange timeRange) {
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from ").append(type.getName()).append("  as custom_report");
		if (timeRange != null) {
			String timeRangeStr = QueryUtil.createTimeRangeRestrictionQuery(timeRange, "dateOfCreation");
			if (timeRangeStr != null) {
				sb.append(" where ").append(timeRangeStr);
			}
		}
		Query query = getSession().createQuery(sb.toString());
		Object size = query.uniqueResult();
		return Integer.parseInt(size.toString());
	}
}
