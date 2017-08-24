package viser.web.project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import viser.dao.project.ProjectDAO;
import viser.service.support.SessionUtils;

@WebServlet("/project/imagelist")
public class ReadImageListServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(ReadImageListServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<String> imagelist = new ArrayList<String>();
    ProjectDAO projectDAO = new ProjectDAO();
    HttpSession session = request.getSession();
    SessionUtils sessionUtils = new SessionUtils();

    imagelist = projectDAO.getImageList(sessionUtils.getStringValue(session, "projectName"));
    request.setAttribute("imageList", imagelist);
    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/chat.jsp");
    rd.forward(request, response);
  }
}
