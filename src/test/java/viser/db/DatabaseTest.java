package viser.db;

import static org.junit.Assert.*;

import org.junit.Test;

import viser.db.Database;
import viser.user.User;

public class DatabaseTest {
	@Test
	public void addAndFind() {
		User user=new User("ID","PSW","NAME");
		Database.addUser(user);
		User dbUser=Database.findByUserId(user.getUserId());
		assertEquals(user,dbUser);
	}
	@Test
	public void addAndFindWhenNotExsited() throws Exception {
		User dbUser=Database.findByUserId("AnyId");
		assertNull(dbUser);
	}
}
