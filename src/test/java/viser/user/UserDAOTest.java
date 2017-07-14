package viser.user;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.Before;
import org.junit.Test;

public class UserDAOTest {

  private UserDAO userDAO;
  private User user;

  @Before
  public void setUp() {
    userDAO = new UserDAO();
    user = UserTest.TEST_USER;
  }

  @Test
  public void connection() throws Exception {
    UserDAO userDAO = new UserDAO();
    Connection con = userDAO.getConnection();
    assertNotNull(con);
  }

  @Test
  public void crud() throws Exception {
    userDAO.removeUser(user.getUserId());
    userDAO.addUser(user);

    User dbUser = userDAO.getByUserId(user.getUserId());
    assertEquals(dbUser, user);

    User updateUser = new User(user.getUserId(), "uPSW", "uName", "u25", "viser@visermail.net", "Her");
    userDAO.updateUser(updateUser);

    dbUser = userDAO.getByUserId(updateUser.getUserId());
    assertEquals(updateUser, dbUser);
  }

  @Test
  public void getWhenNotExsitUser() throws Exception {
    userDAO.removeUser(user.getUserId());
    User dbUser = userDAO.getByUserId(user.getUserId());
    assertNull(dbUser);
  }
}
