package viser.project;

import java.io.*;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/project/imagedelete")
public class DeleteImageServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(DeleteImageServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    ProjectDAO projectDao = new ProjectDAO();
    String imagePath = request.getParameter("Image_Path");
    File f = new File("C:/web-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/viser" + imagePath);
    // 형근: 현재는 절대주소라서 자신의 로컬 환경에 맞는 경로 변경이 필요하다 따라서 추후에 상대경로로 변경하거나 서버 경로로
    // 변경 필요함
    try {
      if (f.delete()) {
        logger.debug("파일 삭제 성공");
      } else {
        logger.debug("파일 삭제 실패");
      }
      projectDao.removeImage(imagePath);
    } catch (SQLException e) {
      logger.debug("DeleteImageServlet error:" + e.getMessage());
    } finally {
      response.sendRedirect("/project/imagelist");
    }
  }
}
