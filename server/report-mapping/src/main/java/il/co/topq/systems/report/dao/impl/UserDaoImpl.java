package il.co.topq.systems.report.dao.impl;

import org.springframework.stereotype.Repository;

import il.co.topq.systems.report.common.model.User;
import il.co.topq.systems.report.dao.infra.UserDao;

@Repository("userDao")
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {

	@Override
	public User getUser(String username, String password) {
		return (User) getSession().createQuery(
				new StringBuilder().append("from ").append(this.type.getSimpleName()).append(" where username='")
						.append(username).append("' and password='").append(password).append("'").toString())
				.uniqueResult();
	}

}
