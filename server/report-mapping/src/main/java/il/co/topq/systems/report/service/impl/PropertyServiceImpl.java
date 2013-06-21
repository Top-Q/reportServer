package il.co.topq.systems.report.service.impl;

import il.co.topq.systems.report.common.model.ReportProperty;
import il.co.topq.systems.report.common.obj.Chunk;
import il.co.topq.systems.report.common.obj.PropertyValuesWrapper;
import il.co.topq.systems.report.dao.infra.PropertyDao;
import il.co.topq.systems.report.service.infra.PropertyService;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("propertyService")
@Transactional
public class PropertyServiceImpl<T> implements PropertyService<T> {

	private PropertyDao<T> propertyDao;

	public void setPropertyDao(PropertyDao<T> propertyDao) {
		this.propertyDao = propertyDao;
	}

	@Override
	public T create(T t) {
		return propertyDao.create(t);
	}

	@Override
	public void delete(int id) {
		propertyDao.delete(id);
	}

	@Override
	public T update(T t) {
		return propertyDao.update(t);
	}

	@Override
	public long countAll(Map<String, Object> params) {
		return propertyDao.countAll(params);
	}

	@Override
	public T get(long id) {
		return propertyDao.get(id);
	}

	@Override
	public List<T> getAll() {
		return propertyDao.getAll();
	}

	@Override
	public Set<String> getPropertyValuesByKey(String key) {
		return propertyDao.getPropertyValuesByKey(key);
	}

	@Override
	public List<String> getUniquePropertyKeyList() {
		return propertyDao.getUniquePropertyKeyList();
	}

	@Override
	public T getProperty(String key, String value) {
		return propertyDao.getProperty(key, value);
	}

	@Override
	public boolean isPersisted(String key, String value) {
		return propertyDao.isPersisted(key, value);
	}

	@Override
	public String getPropertyKey(Integer propertyId) {
		return propertyDao.getPropertyKey(propertyId);
	}

	@Override
	public void deleteOrphanProperties() {
		propertyDao.deleteOrphanProperties();
	}

	@Override
	public List<PropertyValuesWrapper> getAllPropertiesValues(Collection<ReportProperty> properties) {
		return propertyDao.getAllPropertiesValues(properties);
	}

	@Override
	public List<T> getChunk(Chunk chunk) {
		return propertyDao.get(chunk);
	}

	@Override
	public void updateAll(Collection<T> properties) {
		propertyDao.updateAll(properties);
	}

	@Override
	public Object createIndex(String tableName, String column, String indexName) {
		// TODO Auto-generated method stub
		return null;
	}

}
