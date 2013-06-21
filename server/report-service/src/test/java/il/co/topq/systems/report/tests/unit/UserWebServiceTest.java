package il.co.topq.systems.report.tests.unit;

import il.co.topq.systems.report.common.infra.log.ReportLogger;
import il.co.topq.systems.report.common.model.User;
import il.co.topq.systems.report.component.jaxbWrappers.UserList;
import il.co.topq.systems.report.component.jaxbWrappers.UserRoleList;
import il.co.topq.systems.report.component.utils.URLParts;
import il.co.topq.systems.report.service.infra.UserService;
import il.co.topq.systems.report.tests.infra.WebserviceBaseTest;
import il.co.topq.systems.report.utils.RandomData;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/applicationContext.xml" })
public class UserWebServiceTest extends WebserviceBaseTest {

	private Logger log = ReportLogger.getInstance().getLogger(this.getClass());

	@Autowired
	UserService userService;

	@Test
	public void createUserTest() {
		User createdUser = null;
		try {
			User user = RandomData.getRandomUser();
			log.info(user.toString());
			createdUser = getWebResource(URLParts.CREATE_NEW_USER).post(User.class, user);
			log.info(createdUser.toString());
		} finally {
			if (createdUser != null) {
				userService.delete(createdUser.getId());
			}
		}
	}

	@Test
	public void deleteUserTest() throws Exception {

		User createdUser = userService.create(RandomData.getRandomUser());
		Assert.assertNotNull(createdUser);
		User afterDeletion = null;
		try {
			getWebResource(URLParts.DELETE_USER + createdUser.getId()).get(String.class);
			afterDeletion = userService.get(createdUser.getId());
			Assert.assertNull(afterDeletion);
		} finally {
			if (afterDeletion != null) {
				userService.delete(createdUser.getId());
			}
		}
	}

	@Test
	public void updateUserTest() throws Exception {
		String newUsername = "Eran";
		User createdUser = userService.create(RandomData.getRandomUser());
		Assert.assertNotNull(createdUser);
		createdUser.setUsername(newUsername);
		User afterUpdate = null;
		try {
			afterUpdate = getWebResource(URLParts.UPDATE_USER).post(User.class, createdUser);
			Assert.assertEquals(newUsername, afterUpdate.getUsername());
		} finally {
			if (createdUser != null) {
				userService.delete(createdUser.getId());
			}
		}
	}

	@Test
	public void countUsersTest() {
		String numOfUsersStr = getWebResource(URLParts.COUNT_USERS).get(String.class);
		numOfUsersStr = numOfUsersStr.trim();
		Assert.assertNotNull(numOfUsersStr);
	}

	@Test
	public void getUserByIdTest() throws Exception {
		User user = null;
		try {
			user = userService.create(RandomData.getRandomUser());
			User userFromDB = getWebResource(URLParts.GET_USER_BY_ID + user.getId()).get(User.class);
			Assert.assertNotNull(userFromDB);
			Assert.assertEquals(user.getUsername(), userFromDB.getUsername());
			Assert.assertEquals(user.getPassword(), userFromDB.getPassword());
		} finally {
			if (user != null) {
				userService.delete(user.getId());
			}
		}
	}

	@Test
	public void getUserRolesList() {
		UserRoleList userRoleList = getWebResource(URLParts.GET_USER_ROLES).get(UserRoleList.class);
		Assert.assertNotNull(userRoleList);
	}

	@Test
	public void getAllUsers() {
		UserList userList = getWebResource(URLParts.GET_ALL_USERS).get(UserList.class);
		Assert.assertNotNull(userList);
	}

	// TODO: this is not that important as application will use getAll, assuming there are not a lot of users.
	public void getChunkOfUsersTest() {

	}
}
