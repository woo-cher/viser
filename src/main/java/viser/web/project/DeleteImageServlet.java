package viser.web.project;

import java.io.*;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import viser.dao.project.ProjectDAO;

// 현재는 절대주소라서 추후에 상대경로로 변경하거나 서버 경로로 변경 필요함
@WebServlet("/project/imagedelete")
public class DeleteImageServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(DeleteImageServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    ProjectDAO projectDAO = new ProjectDAO();
    final String imagePath = request.getParameter("Image_Path");
    final File f = new File("C:/web-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/viser" + imagePath);

    try {
      if (f.delete()) {
        logger.debug("파일 삭제 성공");
      } else {
        logger.debug("파일 삭제 실패");
      }
      projectDAO.removeImage(imagePath);
    } catch (SQLException e) {
      logger.debug("DeleteImageServlet error:" + e.getMessage());
    } finally {
      response.sendRedirect("/project/imagelist");
    }
  }
}
