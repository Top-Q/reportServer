package il.co.topq.systems.report.service.impl;

import il.co.topq.systems.report.common.exception.ErrorMessage;
import il.co.topq.systems.report.common.infra.log.ReportLogger;
import il.co.topq.systems.report.common.model.User;
import il.co.topq.systems.report.common.obj.Chunk;
import il.co.topq.systems.report.common.obj.UserRole;
import il.co.topq.systems.report.dao.infra.UserDao;
import il.co.topq.systems.report.service.infra.UserService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	private Logger log = ReportLogger.getInstance().getLogger(this.getClass());

	@Autowired
	private UserDao userDao;

	@Override
	public User create(User user) throws Exception {
		if (user != null) {
			validateUserPermissions(user);
			if (!isUserExist(user)) {
				return userDao.create(user);
			} else {
				log.info("could not create user: " + user.toString() + ", it already exist");
				throw new Exception(ErrorMessage.USER_EXIST);
			}
		}

		throw new Exception("user object to create is null");
	}

	public boolean isUserExist(User user) {
		return (userDao.getUser(user.getUsername(), user.getPassword()) != null);
	}

	public void validateUserPermissions(User user) throws Exception {
		if (user != null) {
			String permissions = user.getPermissions();
			if (permissions != null && !permissions.isEmpty()) {
				permissions = permissions.trim();
				String[] permissionsArr = permissions.split(",");
				for (String permission : permissionsArr) {
					if (UserRole.getUserRole(permission) == null) {
						throw new Exception(ErrorMessage.INVALID_USER_PERMISSIONS + ": " + permission);
					}
				}
			}
		}
	}

	@Override
	public void delete(int id) {
		userDao.delete(id);
	}

	@Override
	public User update(User t) throws Exception {
		return userDao.update(t);
	}

	@Override
	public long countAll(Map<String, Object> params) {
		return userDao.countAll(params);
	}

	@Override
	public User get(long id) {
		return userDao.get(id);
	}

	@Override
	public List<User> getAll() {
		return userDao.getAll();
	}

	@Override
	public List<User> getChunk(Chunk chunk) {
		return userDao.get(chunk);
	}

	@Override
	public void updateAll(Collection<User> entities) {
		userDao.updateAll(entities);
	}

	@Override
	public Object createIndex(String tableName, String column, String indexName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser(String username, String password) {
		return userDao.getUser(username, password);
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
