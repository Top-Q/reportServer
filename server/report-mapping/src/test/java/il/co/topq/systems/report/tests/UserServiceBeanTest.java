package il.co.topq.systems.report.tests;

import il.co.topq.systems.report.common.exception.ErrorMessage;
import il.co.topq.systems.report.common.infra.log.ReportLogger;
import il.co.topq.systems.report.common.model.User;
import il.co.topq.systems.report.common.obj.Chunk;
import il.co.topq.systems.report.service.infra.UserService;
import il.co.topq.systems.report.utils.RandomData;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceBeanTest extends SpringBaseTest {

	private Logger log = ReportLogger.getInstance().getLogger(this.getClass());

	@Autowired
	UserService userService;

	@Test
	public void createUserTest() throws Exception {
		long numOfExistingUsers = userService.countAll(null);

		User user = userService.create(RandomData.getRandomUser());
		Assert.assertNotNull(user);
		userService.delete(user.getId());
		long numOfUsers = userService.countAll(null);
		Assert.assertEquals(numOfExistingUsers, numOfUsers);
	}

	@Test
	public void deleteUserTest() throws Exception {
		long numOfExistingUsers = userService.countAll(null);

		User user = userService.create(RandomData.getRandomUser());
		Assert.assertNotNull(user);
		userService.delete(user.getId());
		long numOfUsers = userService.countAll(null);
		Assert.assertEquals(numOfExistingUsers, numOfUsers);
	}

	@Test
	public void updateUserTest() throws Exception {
		long numOfExistingUsers = userService.countAll(null);
		String updatedPassword = RandomData.generateString(10);
		User user = userService.create(RandomData.getRandomUser());
		Assert.assertNotNull(user);
		user.setPassword(updatedPassword);
		User updatedUser = userService.update(user);
		Assert.assertEquals(updatedPassword, updatedUser.getPassword());
		User updatedUserFromDB = userService.get(updatedUser.getId());
		Assert.assertEquals(updatedPassword, updatedUserFromDB.getPassword());

		userService.delete(user.getId());
		Assert.assertEquals(numOfExistingUsers, userService.countAll(null));
	}

	@Test
	public void countAllUsers() throws Exception {
		long numOfExistingUsers = userService.countAll(null);

		User user = userService.create(RandomData.getRandomUser());
		User user2 = userService.create(RandomData.getRandomUser());
		User user3 = userService.create(RandomData.getRandomUser());
		Assert.assertEquals(3 + numOfExistingUsers, userService.countAll(null));
		userService.delete(user.getId());
		userService.delete(user2.getId());
		userService.delete(user3.getId());
		Assert.assertEquals(numOfExistingUsers, userService.countAll(null));
	}

	@Test
	public void getUserById() throws Exception {
		long numOfExistingUsers = userService.countAll(null);
		User user = userService.create(RandomData.getRandomUser());
		Assert.assertEquals(user.getUsername(), userService.get(user.getId()).getUsername());
		Assert.assertEquals(user.getId(), userService.get(user.getId()).getId());
		Assert.assertEquals(user.getPassword(), userService.get(user.getId()).getPassword());
		Assert.assertEquals(user.getPermissions(), userService.get(user.getId()).getPermissions());
		userService.delete(user.getId());
		Assert.assertEquals(numOfExistingUsers, userService.countAll(null));
	}

	@Test
	public void getChunk() throws Exception {
		long numOfExistingUsers = userService.countAll(null);

		int numOfUsers = 30;
		List<User> createdUsers = new ArrayList<User>();
		for (int i = 0; i < numOfUsers; i++) {
			createdUsers.add(userService.create(RandomData.getRandomUser()));
		}
		Assert.assertEquals(numOfUsers, createdUsers.size());

		List<User> chunkOfUsers = userService.getChunk(new Chunk(0, 10));
		Assert.assertEquals(10, chunkOfUsers.size());

		for (User user : createdUsers) {
			userService.delete(user.getId());
		}
		Assert.assertEquals(numOfExistingUsers, userService.countAll(null));
	}

	@Test
	public void createUserWithUIConfigurations() throws Exception {
		long numOfExistingUsers = userService.countAll(null);
		User randomUser = RandomData.getRandomUser();
		String uiConfigurationXMLStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n<!DOCTYPE properties SYSTEM \"http://java.sun.com/dtd/properties.dtd\">\r\n<properties>\r\n<entry key=\"Hello\">hi</entry>\r\n</properties>";
		randomUser.setUiConfigurations(uiConfigurationXMLStr);
		User user = userService.create(randomUser);
		Assert.assertNotNull(user);
		User fromDB = userService.get(user.getId());
		Assert.assertNotNull(fromDB.getUiConfigurations());
		Assert.assertEquals(uiConfigurationXMLStr, fromDB.getUiConfigurations());
		userService.delete(user.getId());
		long numOfUsers = userService.countAll(null);
		Assert.assertEquals(numOfExistingUsers, numOfUsers);
	}

	@Test
	public void getUserByUsernameAndPassword() throws Exception {
		long numOfExistingUsers = userService.countAll(null);

		User user = userService.create(RandomData.getRandomUser());
		Assert.assertNotNull(user);
		User fromdb = userService.getUser(user.getUsername(), user.getPassword());
		Assert.assertNotNull(fromdb);
		userService.delete(user.getId());
		long numOfUsers = userService.countAll(null);
		Assert.assertEquals(numOfExistingUsers, numOfUsers);
	}

	@Test
	public void createDuplicateUsers() throws Exception {
		User randomUser = RandomData.getRandomUser();
		String username = randomUser.getUsername();
		String password = randomUser.getPassword();
		User u1 = new User();
		User u2 = new User();
		u1.setUsername(username);
		u2.setUsername(username);
		u1.setPassword(password);
		u2.setPassword(password);

		userService.create(u1);
		Assert.assertNotNull(u1.getId());
		try {
			userService.create(u2);
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().contains(ErrorMessage.USER_EXIST));
			if (u1 != null) {
				userService.delete(u1.getId());
			}
		}
	}

	@Test
	public void createUserWithInvalidPermissions() {
		User randomUser = RandomData.getRandomUser();
		User u1 = new User();
		u1.setUsername(randomUser.getUsername());
		u1.setPassword(randomUser.getPassword());
		u1.setPermissions("invalidPermission");
		try {
			userService.create(u1);
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().contains(ErrorMessage.INVALID_USER_PERMISSIONS));
		} finally {
			if (u1.getId() != null) {
				userService.delete(u1.getId());
			}
		}
	}

	@Test
	public void getAllUsers() {
		List<User> allUsers = userService.getAll();
		Assert.assertNotNull(allUsers);
	}

}
