package viser.user;


import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.Before;
import org.junit.Test;

public class UserDAOTest {

	private UserDAO userDAO;
	private User user;
	@Before
	public void setUp() throws Exception {
		userDAO=new UserDAO();
		user=UserTest.TEST_USER;
	}
	@Test
	public void connection() throws Exception {
		UserDAO userDAO=new UserDAO();
		Connection con=userDAO.getConnection();
		assertNotNull(con);
	}
	@Test
	public void crud() throws Exception {
		userDAO.removeUser(user.getUserId());  //d
		userDAO.addUser(user);  //c
		User dbuser=userDAO.findByUserId(user.getUserId()); //r
		assertEquals(dbuser,user);
	}
	@Test
	public void findWhenNotExsitUser() throws Exception {
		UserDAO userDAO=new UserDAO();
		userDAO.removeUser(user.getUserId());  //d
		User dbuser=userDAO.findByUserId(user.getUserId()); //r
		assertNull(dbuser);
	}
}
