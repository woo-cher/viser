package viser.project;

import java.io.IOException;
import java.sql.SQLException;
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

@WebServlet("/project/imagelist")
public class ReadImageListServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(ReadImageListServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<String> imagelist = new ArrayList<String>();
    ProjectDAO projectDao = new ProjectDAO();
    try {
      logger.debug("imageList doget처리");
      HttpSession session = request.getSession();
      imagelist = projectDao.getImageList((String) session.getAttribute("projectName"));
      request.setAttribute("imageList", imagelist);
      RequestDispatcher rd = request.getRequestDispatcher("/chat.jsp");
      rd.forward(request, response);
    } catch (SQLException e) {
      logger.debug("imagelist doget error" + e.getMessage());
    }
  }
}
