package viser.user;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import viser.dao.user.UserDAO;
import viser.domain.user.PasswordMismatchException;
import viser.domain.user.User;
import viser.domain.user.UserNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContext.xml")
public class UserTest {
  private static final Logger logger = LoggerFactory.getLogger(UserTest.class);
  public static User TEST_USER = new User("Test_Id", "PSW", "Name", "25", "test@viser.com", "Man");
  
  @Autowired
  private UserDAO userDAO;
  
  @Before
  public void returns() throws SQLException {
    userDAO.removeUser(TEST_USER.getUserId());
  }
  
  @Test
  public void matchPassword() {
    assertTrue(TEST_USER.matchPassword("PSW"));
  }

  @Test
  public void notMatchPassword() {
    assertFalse(TEST_USER.matchPassword("AnyPSW"));
  }

  @Test(expected = UserNotFoundException.class)
  public void loginWhenNotExsitedUser() throws Exception {
    User.login("AnyId", TEST_USER.getPassword());
  }

  @Test(expected = PasswordMismatchException.class)
  public void loginWhenPasswordMismatch() throws Exception {
    userDAO.addUser(TEST_USER);
    User.login(TEST_USER.getUserId(), "AnyPSW");
  }

  @Test
  public void login() throws Exception {
    userDAO.addUser(TEST_USER);
    assertTrue(User.login(TEST_USER.getUserId(), TEST_USER.getPassword()));
  }
}
