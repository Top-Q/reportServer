package il.co.topq.systems.report.service.infra;

import il.co.topq.systems.report.common.model.User;

public interface UserService extends BaseService<User> {

	User getUser(String username, String password);

	void validateUserPermissions(User user) throws Exception;

	boolean isUserExist(User user);

}
