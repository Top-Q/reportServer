package il.co.topq.systems.report.service.infra;

import il.co.topq.systems.report.common.obj.Chunk;
import il.co.topq.systems.report.common.obj.TimeRange;

import java.util.List;

/**
 * 
 * @author Eran Golan & Tomer Gafner
 */
public interface CustomReportService<T> extends BaseService<T> {

	/**
	 * @param timeRange
	 *            used as property to filter scenarioCustomReport -
	 * @return will return a the size of this query, this method will be used by UI to build the pages according the
	 *         result's size; Null in case of error;
	 */
	int getSizeOfCustomReportByFilter(TimeRange timeRange);

	List<T> getCustomReport(Chunk chunk, TimeRange timeRange);
}
