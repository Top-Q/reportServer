package il.co.topq.systems.report.dao.infra;

import il.co.topq.systems.report.common.obj.Chunk;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface GenericDao<T> {

	/**
	 * Method that returns the number of entries from a table that meet some criteria (where clause parameters)
	 * 
	 * @param params
	 *            SQL parameters
	 * @return the number of records meeting the criteria
	 */
	long countAll(Map<String, Object> params);

	T create(final T t);

	void delete(Object id);

	T get(Object id);

	T update(T t);

	List<T> getAll();

	List<T> get(final Chunk chunk);

	void updateAll(Collection<T> objects);

	Object createIndex(String tableName, String column, String indexName);

}