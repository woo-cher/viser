package viser.user;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import viser.db.Database;
import viser.user.User;

public class UserTest {
	public static User TEST_USER=new User("ID","PSW","NAME");
	@Test
	public void matchPassword() {
		assertTrue(TEST_USER.matchPassword("PSW"));
	}
	@Test
	public void notMatchPassword() {
		assertFalse(TEST_USER.matchPassword("AnyPSW"));
	}
	@Test(expected=Exception.class)
	public void loginWhenNotExsitedUser() throws Exception {
		User.login("AnyId", TEST_USER.getPassword());
	}
	@Test(expected=Exception.class)
	public void loginWhenPasswordMismatch() throws Exception {
		User.login(TEST_USER.getUserId(), "AnyPSW");
	}
	@Test
	public void login() throws Exception {
		User user=UserTest.TEST_USER;
		Database.addUser(user);
		assertTrue(User.login(TEST_USER.getUserId(),TEST_USER.getPassword()));
	}
}
