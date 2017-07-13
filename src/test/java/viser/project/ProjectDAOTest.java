package viser.project;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import viser.user.User;
import viser.user.UserDAO;
import viser.user.UserTest;

public class ProjectDAOTest {
  private static final Logger logger = LoggerFactory.getLogger(ProjectDAOTest.class);
  public static Project TEST_PROJECT = new Project("TEST_PROJECT");
  private ProjectDAO projectDAO;
  private UserDAO userDAO;

  @Before
  public void setup() throws SQLException {
    projectDAO = new ProjectDAO();
    userDAO = new UserDAO();
    projectDAO.removeProject(ProjectDAOTest.TEST_PROJECT.getProjectName());
    projectDAO.addProject(ProjectDAOTest.TEST_PROJECT);
  }

  @After
  public void returns() throws SQLException {
    projectDAO.removeProject(ProjectDAOTest.TEST_PROJECT.getProjectName());
  }

  @Test
  public void Connection() throws SQLException {
    Connection con = projectDAO.getConnection();
    assertNotNull(con);
  }

  @Test
  public void projectCrud() throws SQLException {
    Project dbProject = projectDAO.findByProjectName(TEST_PROJECT.getProjectName());
    assertEquals(TEST_PROJECT.getProjectName(), dbProject.getProjectName());

    Project UpdateProject = new Project("UpdateProject");
    projectDAO.updateProject(UpdateProject.getProjectName(), dbProject.getProjectName());
    dbProject = projectDAO.findByProjectName(UpdateProject.getProjectName());
    assertEquals(dbProject.getProjectName(), UpdateProject.getProjectName());

    projectDAO.removeProject(UpdateProject.getProjectName());
  }

  @Test
  public void UserCrd_ProjectU() throws SQLException {
    User user = UserTest.TEST_USER;

    userDAO.removeUser(user.getUserId());
    userDAO.addUser(user);

    // invite Test
    projectDAO.InviteUser(user.getUserId(), TEST_PROJECT.getProjectName(), 0);
    User invitedUser = projectDAO.findProjectMember(TEST_PROJECT.getProjectName());
    assertEquals(user.getUserId(), invitedUser.getUserId());

    // getProjectMemberList Test
    assertNotNull(projectDAO.getProjectMemberList(TEST_PROJECT.getProjectName()));
    logger.debug("projectMember : {}", projectDAO.getProjectMemberList(TEST_PROJECT.getProjectName()));

    // getProjectList Test
    assertNotNull(projectDAO.getProjectList(user.getUserId()));
    logger.debug("projectList : {}", projectDAO.getProjectMemberList(TEST_PROJECT.getProjectName()));

    // kick Test
    projectDAO.KickProjectUser(invitedUser.getUserId(), TEST_PROJECT.getProjectName());
    invitedUser = projectDAO.findProjectMember(TEST_PROJECT.getProjectName());
    assertNull(invitedUser);

    userDAO.removeUser(user.getUserId());
  }

  @Test
  public void getUserList() throws Exception {
    userDAO.addUser(new User("loginUser", "", "", "", "", ""));
    userDAO.addUser(new User("TestId1", "", "", "", "", ""));
    userDAO.addUser(new User("TestId2", "", "", "", "", ""));

    logger.debug("Users : {}", projectDAO.getUserList("te", "loginUser"));
    assertNotNull(projectDAO.getUserList("te", "loginUser"));

    userDAO.removeUser("loginUser");
    userDAO.removeUser("TestId1");
    userDAO.removeUser("TestId2");
  }

  @Test
  public void getMemberList() throws Exception {

  }
}
