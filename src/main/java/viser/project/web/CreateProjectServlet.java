package viser.project.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import viser.project.Project;
import viser.project.ProjectDAO;
import viser.support.SessionUtils;
import viser.user.User;
import viser.user.web.LogInServlet;

@WebServlet("/project/createProject")
public class CreateProjectServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(CreateProjectServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    Project project = new Project();
    User user = new User();

    ProjectDAO pjtDao = new ProjectDAO();
    HttpSession session = request.getSession();

    request.setCharacterEncoding("utf-8");
    String userId = new SessionUtils().getStringValue(session, LogInServlet.SESSION_USER_ID);
    String projectName = request.getParameter("projectName");

    project.setProjectName(projectName);
    user.setUserId(userId);

    try {
      pjtDao.addProject(project);
      pjtDao.addprojectMember(project, user, 1);

      response.sendRedirect("/project/projectlist");

    } catch (Exception e) {
      logger.debug("Project create Fail : " + e);
    }

  }
}
