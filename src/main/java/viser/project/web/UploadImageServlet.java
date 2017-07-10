package viser.project.web;

import java.io.IOException;
import java.io.PrintWriter;
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

import viser.project.ProjectDAO;

@WebServlet("/imageUpload")
public class UploadImageServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(UploadImageServlet.class);

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    String path = request.getRealPath("/upload_image");

    MultipartRequest mr = new MultipartRequest(

        request, path, 1024 * 1024 * 5, "utf-8", new DefaultFileRenamePolicy());

    HttpSession session = request.getSession();
    logger.debug("UploadImageServlet 에서 조회한 세션의 projectname:" + (String) session.getAttribute("projectName"));
    ProjectDAO projectDao = new ProjectDAO();

    // 파라미터로 전달한 파일이름과 세션에 저장되어있는 userId, ProjectName 전달
    try {
      projectDao.addImage("/upload_image/" + mr.getFile("s_file").getName(), (String) session.getAttribute("projectName"), (String) session.getAttribute("userId"));
    } catch (SQLException e) {
      logger.debug("UploadImageServlet error" + e.getMessage());
    }
    response.sendRedirect("lists/cardlist?boardNum=" + (int) session.getAttribute("boardNum"));
  }

}
