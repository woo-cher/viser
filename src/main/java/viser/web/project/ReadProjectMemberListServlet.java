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
import viser.domain.project.ProjectMember;

@WebServlet("/project/memberlist")
public class ReadProjectMemberListServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(ReadProjectMemberListServlet.class);

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    List<ProjectMember> memberlist = new ArrayList();
    ProjectDAO projectDAO = new ProjectDAO();

    try {
      memberlist = projectDAO.getProjectMemberList((String) session.getAttribute("projectName"));
      request.setAttribute("memberlist", memberlist);

      for (ProjectMember pm : memberlist) {

        logger.debug("관리자 권한 확인 반복문 시작");
        logger.debug("ReadProjectMemberListServlet db에서 조회 아이디 :" + pm.getUserId());
        logger.debug("ReadProjectMemberListServlet 세션에서 조회 아이디 :" + session.getAttribute("userId"));
        if (pm.getUserId().equals(session.getAttribute("userId")) && pm.getPower() == 1) {
          logger.debug("ReadProjectMemberListServlet db에서 조회한 권한 :" + pm.getPower());
          request.setAttribute("isMaster", true);
        }
      }
      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/member.jsp");
      rd.forward(request, response);

    } catch (Exception e) {
      logger.debug("ReadProjectMemberListServlet error:" + e.getMessage());
    }
  }
}
