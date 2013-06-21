package il.co.topq.systems.report.dao.infra;

import il.co.topq.systems.report.common.model.User;

public interface UserDao extends GenericDao<User> {

	User getUser(String username, String password);
}
