package viser.web.project;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import viser.dao.project.ProjectDAO;
import viser.domain.project.Image;

@WebServlet("/imageUpload")
public class UploadImageServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(UploadImageServlet.class);

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    final String path = request.getRealPath("/upload_image/");
    MultipartRequest mr = new MultipartRequest(request, path, 1024 * 1024 * 5, "utf-8", new DefaultFileRenamePolicy());

    ProjectDAO projectDAO = new ProjectDAO();

    HttpSession session = request.getSession();
    logger.debug("UploadImageServlet 에서 조회한 세션의 projectname:" + (String) session.getAttribute("projectName"));

    Image image = new Image(path + mr.getFile("s_file").getName(), (String) session.getAttribute("userId"));

    try {
      projectDAO.addImage(image, (String) session.getAttribute("projectName"));
    } catch (SQLException e) {
      logger.debug("UploadImageServlet error" + e.getMessage());
    }
    response.sendRedirect("lists/cardlist?boardNum=" + (int) session.getAttribute("boardNum"));
  }
}
