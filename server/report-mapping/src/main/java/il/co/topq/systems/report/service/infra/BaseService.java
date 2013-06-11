package il.co.topq.systems.report.service.infra;

import il.co.topq.systems.report.common.obj.Chunk;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface BaseService<T> {

	T create(T t) throws Exception;

	void delete(int id);

	T update(T t) throws Exception;

	long countAll(final Map<String, Object> params);

	T get(long id);

	List<T> getAll();

	List<T> getChunk(final Chunk chunk);

	void updateAll(Collection<T> entities);

	Object createIndex(String tableName, String column, String indexName);

}
