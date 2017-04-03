package viser.user;


import static org.junit.Assert.assertNotNull;

import java.sql.Connection;

import org.junit.Test;

public class UserDAOTest {

	@Test
	public void connection() throws Exception {
		UserDAO userDAO=new UserDAO();
		Connection con=userDAO.getConnection();
		assertNotNull(con);
	}
	@Test
	public void insert() throws Exception {
		UserDAO userDAO=new UserDAO();
		userDAO.insert(UserTest.TEST_USER);
	}
}
