package il.co.topq.systems.report.dao.impl;

import il.co.topq.systems.report.common.infra.log.ReportLogger;
import il.co.topq.systems.report.common.obj.Chunk;
import il.co.topq.systems.report.dao.infra.GenericDao;
import il.co.topq.systems.report.utils.QueryUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class GenericDaoImpl<T> implements GenericDao<T> {

	protected HibernateTemplate hibernateTemplate;
	protected Class<T> type;// will hold the generic type of this class

	private Logger log = ReportLogger.getInstance().getLogger(this.getClass());

	protected Session getSession() {
		Session currentSession = hibernateTemplate.getSessionFactory().getCurrentSession();
		if (!currentSession.isOpen()) {
			return hibernateTemplate.getSessionFactory().openSession();
		} else
			return currentSession;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> get(final Chunk chunk) {
		StringBuilder sb = new StringBuilder().append("from ").append(type.getSimpleName()).append(" as entity");
		Query query = getSession().createQuery(sb.toString());
		QueryUtil.setQueryResultSize(query, chunk);
		return query.list();
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	public GenericDaoImpl() {
		;
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		type = (Class<T>) pt.getActualTypeArguments()[0];
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll() {
		return (List<T>) hibernateTemplate.find("from " + type.getSimpleName());
	}

	@Override
	public long countAll(final Map<String, Object> params) {
		final StringBuffer queryString = new StringBuffer("SELECT count(o) from ");

		queryString.append(type.getSimpleName()).append(" o ");
		queryString.append(getQueryClauses(params, null));

		return DataAccessUtils.longResult(this.hibernateTemplate.find(queryString.toString()));
	}

	@Override
	public T create(final T t) {
		// hibernateTemplate.save(t);
		hibernateTemplate.saveOrUpdate(t);
		return t;
	}

	@Override
	public void delete(final Object id) {
		hibernateTemplate.delete(get(id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(final Object id) {
		return (T) hibernateTemplate.get(type, Integer.valueOf(id.toString()));
	}

	@Override
	public T update(final T t) {
		hibernateTemplate.merge(t);
		return t;
	}

	private String getQueryClauses(final Map<String, Object> params, final Map<String, Object> orderParams) {
		final StringBuffer queryString = new StringBuffer();
		if ((params != null) && !params.isEmpty()) {
			queryString.append(" where ");
			for (final Iterator<Map.Entry<String, Object>> it = params.entrySet().iterator(); it.hasNext();) {
				final Map.Entry<String, Object> entry = it.next();
				if (entry.getValue() instanceof Boolean) {
					queryString.append(entry.getKey()).append(" is ").append(entry.getValue()).append(" ");
				} else {
					if (entry.getValue() instanceof Number) {
						queryString.append(entry.getKey()).append(" = ").append(entry.getValue());
					} else {
						// string equality
						queryString.append(entry.getKey()).append(" = '").append(entry.getValue()).append("'");
					}
				}
				if (it.hasNext()) {
					queryString.append(" and ");
				}
			}
		}
		if ((orderParams != null) && !orderParams.isEmpty()) {
			queryString.append(" order by ");
			for (final Iterator<Map.Entry<String, Object>> it = orderParams.entrySet().iterator(); it.hasNext();) {
				final Map.Entry<String, Object> entry = it.next();
				queryString.append(entry.getKey()).append(" ");
				if (entry.getValue() != null) {
					queryString.append(entry.getValue());
				}
				if (it.hasNext()) {
					queryString.append(", ");
				}
			}
		}
		return queryString.toString();
	}

	@Override
	public void updateAll(Collection<T> objects) {
		hibernateTemplate.saveOrUpdateAll(objects);
	}

	public Object createIndex(String tableName, String column, String indexName) {
		Object uniqueResult = getSession().createSQLQuery(
				new StringBuilder().append("create index ").append(indexName).append(" on ").append(tableName)
						.append('(').append(column).append(')').toString()).executeUpdate();

		return uniqueResult;
	}
}