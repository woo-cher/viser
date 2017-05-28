package viser.project;

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

@WebServlet("/project/memberlist")
public class ReadProjectMemberListServlet extends HttpServlet {
	public static Logger logger=LoggerFactory.getLogger(ReadProjectMemberListServlet.class);
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		HttpSession session=request.getSession();
		List<ProjectMember> memberlist = new ArrayList(); 		//형근: chat멤버 목록을 가져오기 위하여 LIST 객체생성
		ProjectDAO projectDao = new ProjectDAO();
		
		try {
		memberlist = projectDao.getProjectMemberList((String)session.getAttribute("projectname")); 	//형근: 멤버목록을 LIST 객체에 담는다.
		request.setAttribute("memberlist", memberlist);
		
		for(ProjectMember pm:memberlist){  //형근: 로그인한 유저가 관리자 권한을 가지고 있는지 체크해주는 반복문
			logger.debug("관리자 권한 확인 반복문 시작");
			logger.debug("ReadProjectMemberListServlet db에서 조회 아이디 :"+pm.getUserId());
			logger.debug("ReadProjectMemberListServlet 세션에서 조회 아이디 :"+session.getAttribute("userId"));
			if(pm.getUserId().equals(session.getAttribute("userId"))&&pm.getPower()==1){
				logger.debug("ReadProjectMemberListServlet db에서 조회한 권한 :"+pm.getPower());
				request.setAttribute("isMaster", true);
			}
		}
		RequestDispatcher rd = request.getRequestDispatcher("/user.jsp");
		rd.forward(request, response);
	
		} catch (Exception e) {
			logger.debug("ReadProjectMemberListServlet error:"+e.getMessage());
		}
	}
}
