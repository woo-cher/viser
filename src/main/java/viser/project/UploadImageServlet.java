package viser.project;

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

@WebServlet("/imageUpload")
public class UploadImageServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(UploadImageServlet.class);

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    String path = request.getRealPath("/upload_image");

    MultipartRequest mr = new MultipartRequest(

        request, path, 1024 * 1024 * 5, "utf-8", new DefaultFileRenamePolicy());

    // 형근: 이미지 경로를 저장하기 위한 dao객체 및 세션 생성
    HttpSession session = request.getSession();
    logger.debug("UploadImageServlet 에서 조회한 세션의 projectname:" + (String) session.getAttribute("projectName"));
    ProjectDAO projectDao = new ProjectDAO();
    try {
      projectDao.addImage("/upload_image/" + mr.getFile("s_file").getName(), (String) session.getAttribute("projectName"), (String) session.getAttribute("userId")); // 형근:
                                                                                                                                                                     // 파라메터로
                                                                                                                                                                     // 전달한
                                                                                                                                                                     // 파일이름과
                                                                                                                                                                     // 세션에
                                                                                                                                                                     // 저장되있는
                                                                                                                                                                     // 사용자
                                                                                                                                                                     // Id와
                                                                                                                                                                     // 프로젝트
                                                                                                                                                                     // 이름
                                                                                                                                                                     // 전달
    } catch (SQLException e) {
      logger.debug("UploadImageServlet error" + e.getMessage());
    }
    response.sendRedirect("lists/cardlist?boardNum=" + (int) session.getAttribute("boardNum"));
  }

}
